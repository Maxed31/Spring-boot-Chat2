package com.armin.Chat.chat;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {
    private String Id;
    private String senderId;
    private String recipientId;
    private String content;
}
