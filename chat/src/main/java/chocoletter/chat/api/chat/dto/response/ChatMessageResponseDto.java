package chocoletter.chat.api.chat.dto.response;


import lombok.Builder;

@Builder
public record ChatMessageResponseDto(Long senderId, String content, String createdAt, Boolean read) {
}
