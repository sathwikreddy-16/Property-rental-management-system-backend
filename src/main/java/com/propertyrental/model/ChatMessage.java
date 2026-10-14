package com.propertyrental.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="chat_messages")
public class ChatMessage {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=190) private String senderEmail;
    @Column(nullable=false,length=190) private String recipientEmail;
    @Column(nullable=false,length=255) private String propertyTitle;
    @Column(nullable=false,length=2000) private String body;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    public ChatMessage(){}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getSenderEmail(){return senderEmail;} public void setSenderEmail(String v){senderEmail=v;}
    public String getRecipientEmail(){return recipientEmail;} public void setRecipientEmail(String v){recipientEmail=v;}
    public String getPropertyTitle(){return propertyTitle;} public void setPropertyTitle(String v){propertyTitle=v;}
    public String getBody(){return body;} public void setBody(String v){body=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

