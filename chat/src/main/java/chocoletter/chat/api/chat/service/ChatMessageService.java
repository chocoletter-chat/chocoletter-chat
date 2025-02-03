package chocoletter.chat.api.chat.service;

import chocoletter.chat.api.chat.domain.ChatMessage;
import chocoletter.chat.api.chat.domain.MessageType;
import chocoletter.chat.api.chat.dto.response.ChatMessageResponseDto;
import chocoletter.chat.api.chat.dto.response.ChatMessagesResponseDto;
import chocoletter.chat.api.chat.dto.response.LastChatMessageResponseDto;
import chocoletter.chat.api.chat.repository.ChatMessageRepository;
import chocoletter.chat.common.exception.ErrorMessage;
import chocoletter.chat.common.exception.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageProducer chatMessageProducer;

    public void saveChatMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public ChatMessagesResponseDto findChatMessages(String roomId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ChatMessageResponseDto> chatMessages = chatMessageRepository.findChatMessages(roomId, pageable)
                .stream()
                .map(chatMessage -> ChatMessageResponseDto.of(MessageType.CHAT, chatMessage))
                .toList();
        return ChatMessagesResponseDto.of(chatMessages);
    }

    public LastChatMessageResponseDto findLastChatMessage(String roomId, String memberId) {
        return chatMessageRepository.findUnReadCountAndLastMessage(roomId, memberId);
    }

    @Transactional
    public void readAllUnreadMessages(String roomId) {
        chatMessageRepository.readAllUnreadMessages(roomId);
    }

    public void noticeReadStatus(String roomId) {
        ChatMessageResponseDto chatMessageResponseDto = ChatMessageResponseDto.builder()
                .roomId(roomId)
                .messageType(MessageType.READ_STATUS)
                .content("채팅 메세지의 읽음 상태가 변경되었습니다.")
                .build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(chatMessageResponseDto);
            chatMessageProducer.sendMessage(roomId, message); // Kafka topic으로 전송
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorMessage.ERR_SERIALIZE_MESSAGE);
        }
    }
}
