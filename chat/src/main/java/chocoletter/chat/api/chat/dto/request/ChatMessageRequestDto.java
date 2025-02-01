package chocoletter.chat.api.chat.dto.request;

import lombok.Getter;

@Getter
public class ChatMessageRequestDto {
    private String roomId;
    private Long senderId;
    private String senderName;
    private String content;
}
