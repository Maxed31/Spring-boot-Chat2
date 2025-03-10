package com.armin.Chat.chat;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessages, String> {
    List<ChatMessages> findByChatId(String s);
}
