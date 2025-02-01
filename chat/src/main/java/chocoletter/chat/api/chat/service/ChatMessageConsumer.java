package chocoletter.chat.api.chat.service;

import chocoletter.chat.api.chat.domain.ChatMessage;
import chocoletter.chat.api.chat.dto.request.ChatMessageRequestDto;
import chocoletter.chat.api.chat.dto.response.ChatMessageResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @KafkaListener(topics = "${spring.kafka.topic.chat}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message) {
        // 메시지를 WebSocket으로 전송
        ChatMessageRequestDto chatMessageRequestDto = parseMessage(message);
        String topic = "/topic/" + chatMessageRequestDto.getRoomId();
        messagingTemplate.convertAndSend(topic, ChatMessageResponseDto.builder()
                .senderId(chatMessageRequestDto.getSenderId())
                .senderName(chatMessageRequestDto.getSenderName())
                .content(chatMessageRequestDto.getContent())
                .isRead(false)
                .createdAt(String.valueOf(LocalDateTime.now()))
                .build());

        // MongoDB에 메시지 저장
        chatMessageService.saveChatMessage(ChatMessage.builder()
                .senderId(chatMessageRequestDto.getSenderId())
                .senderName(chatMessageRequestDto.getSenderName())
                .roomId(chatMessageRequestDto.getRoomId())
                .content(chatMessageRequestDto.getContent())
                .build());
    }

    private ChatMessageRequestDto parseMessage(String message) {
        // JSON 문자열을 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(message, ChatMessageRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse message", e);
        }
    }
}
