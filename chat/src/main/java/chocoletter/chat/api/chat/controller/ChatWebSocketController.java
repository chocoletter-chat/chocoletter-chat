package chocoletter.chat.api.chat.controller;

import chocoletter.chat.api.chat.domain.ChatMessage;
import chocoletter.chat.api.chat.dto.request.ChatMessageRequestDto;
import chocoletter.chat.api.chat.dto.response.ChatMessageResponseDto;
import chocoletter.chat.api.chat.repository.ChatMessageRepository;
import chocoletter.chat.api.chat.service.ChatMessageProducer;
import chocoletter.chat.api.chat.service.ChatMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageProducer chatMessageProducer;

    @MessageMapping("/send")
    public void sendMessage(ChatMessageRequestDto chatMessageRequestDto) {
        // Kafka로 메시지 전송
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(chatMessageRequestDto);
            chatMessageProducer.sendMessage(chatMessageRequestDto.getRoomId(), message); // Kafka topic으로 전송
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize message", e);
        }
    }
}
