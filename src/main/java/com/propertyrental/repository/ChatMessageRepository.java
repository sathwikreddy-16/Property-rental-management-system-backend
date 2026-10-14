package com.propertyrental.repository;
import com.propertyrental.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findBySenderEmailIgnoreCaseOrRecipientEmailIgnoreCaseOrderByCreatedAtAsc(String sender,String recipient);
}

