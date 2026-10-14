package com.propertyrental.controller;
import com.propertyrental.model.Account;
import com.propertyrental.model.ChatMessage;
import com.propertyrental.repository.AccountRepository;
import com.propertyrental.repository.ChatMessageRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api/messages")
public class MessageController {
    private final ChatMessageRepository messages; private final AccountRepository accounts; private final AuthController auth;
    public MessageController(ChatMessageRepository messages,AccountRepository accounts,AuthController auth){this.messages=messages;this.accounts=accounts;this.auth=auth;}
    public record SendRequest(@Email @NotBlank String recipientEmail,@NotBlank @Size(max=255) String propertyTitle,@NotBlank @Size(max=2000) String text){}
    public record MessageView(Long id,String from,String text,LocalDateTime createdAt){}
    public record ConversationView(String id,String email,String name,String property,List<MessageView> messages){}
    @GetMapping("/conversations") public List<ConversationView> conversations(@RequestHeader("Authorization") String authorization){
        Account me=auth.authenticated(authorization);
        List<ChatMessage> all=messages.findBySenderEmailIgnoreCaseOrRecipientEmailIgnoreCaseOrderByCreatedAtAsc(me.getEmail(),me.getEmail());
        Map<String,List<ChatMessage>> grouped=all.stream().collect(Collectors.groupingBy(m->pairKey(me,m),LinkedHashMap::new,Collectors.toList()));
        return grouped.values().stream().map(thread->{ChatMessage last=thread.get(thread.size()-1);String other=otherEmail(me,last);Account contact=accounts.findByEmailIgnoreCase(other).orElseThrow();
            return new ConversationView(other,other,contact.getName(),last.getPropertyTitle(),thread.stream().map(m->new MessageView(m.getId(),m.getSenderEmail().equalsIgnoreCase(me.getEmail())?"me":"them",m.getBody(),m.getCreatedAt())).toList());}).toList();
    }
    @PostMapping public ChatMessage send(@RequestHeader("Authorization") String authorization,@Valid @RequestBody SendRequest request){
        Account me=auth.authenticated(authorization);String role=me.getRole();
        if(!role.equals("tenant")&&!role.equals("landlord"))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only tenants and landlords can message each other");
        Account recipient=accounts.findByEmailIgnoreCase(request.recipientEmail().trim()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Recipient account not found"));
        if(recipient.getRole().equals(role)||!Set.of("tenant","landlord").contains(recipient.getRole()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Messages must be between a tenant and a landlord");
        ChatMessage m=new ChatMessage();m.setSenderEmail(me.getEmail());m.setRecipientEmail(recipient.getEmail());m.setPropertyTitle(request.propertyTitle().trim());m.setBody(request.text().trim());return messages.save(m);
    }
    private String pairKey(Account me,ChatMessage m){return otherEmail(me,m).toLowerCase();}
    private String otherEmail(Account me,ChatMessage m){return m.getSenderEmail().equalsIgnoreCase(me.getEmail())?m.getRecipientEmail():m.getSenderEmail();}
}

