package chocoletter.chat.api.chat.dto;

import lombok.Getter;

@Getter
public class ChatMessageRequestDto {
    private String roomId;
    private Long senderId;
    private String content;
}
