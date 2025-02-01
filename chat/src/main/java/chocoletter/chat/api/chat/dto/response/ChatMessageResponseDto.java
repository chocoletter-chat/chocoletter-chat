package chocoletter.chat.api.chat.dto.response;


import chocoletter.chat.api.chat.domain.ChatMessage;
import lombok.Builder;

@Builder
public record ChatMessageResponseDto(Long senderId, String senderName, String content, String createdAt, Boolean isRead) {
    public static ChatMessageResponseDto of(ChatMessage chatMessage) {
        return ChatMessageResponseDto.builder()
                .senderId(chatMessage.getSenderId())
                .senderName(chatMessage.getSenderName())
                .content(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt().toString())
                .isRead(chatMessage.getIsRead())
                .build();
    }
}
