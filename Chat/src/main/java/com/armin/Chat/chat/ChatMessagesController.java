package com.armin.Chat.chat;

import com.armin.Chat.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessagesController {
    private final SimpMessagingTemplate template;
    private final ChatMessageService service;

    @MessageMapping("/chat")
    public void processMessage(
            @Payload ChatMessages chatMessages
    ){
        ChatMessages savedMsg = service.save(chatMessages);
        template.convertAndSendToUser(savedMsg.getRecipientId(),"/queue/messages",
                ChatNotification.builder()
                .Id(savedMsg.getId())
                        .senderId(savedMsg.getSenderId())
                        .recipientId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent())
                .build());
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessages>> findChatMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ){
        return ResponseEntity.ok(service.findChatMessages(senderId, recipientId));
    }
}
