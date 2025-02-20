package com.armin.Chat.chat;

import com.armin.Chat.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;


    public ChatMessages save(ChatMessages chatMessages){
        var chatId = chatRoomService.getChatRoomId(chatMessages.getSenderId(), chatMessages.getRecipientId(),true)
                .orElseThrow();
        chatMessages.setChatId(chatId);
        repository.save(chatMessages);
        return chatMessages;
    }

    public List<ChatMessages> findChatMessages(
            String senderId, String recipientId
    ){
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }
}
