package chocoletter.chat.chat.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor
public class ChatMessage {
    @Id
    private String id;
    private Long senderId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean read;

    @Builder
    public ChatMessage(Long senderId, String content) {
        this.senderId = senderId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }
}
