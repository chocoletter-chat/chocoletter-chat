package chocoletter.chat.api.chat.service;

import chocoletter.chat.api.chat.domain.ChatMessage;
import chocoletter.chat.api.chat.domain.MessageType;
import chocoletter.chat.api.chat.dto.response.ChatMessageResponseDto;
import chocoletter.chat.api.chat.dto.response.ChatMessagesResponseDto;
import chocoletter.chat.api.chat.dto.response.LastChatMessageResponseDto;
import chocoletter.chat.api.chat.repository.ChatMessageRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

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


    public LastChatMessageResponseDto findLastChatMessage(String roomId, Long memberId) {
        return chatMessageRepository.findUnReadCountAndLastMessage(roomId, memberId);
    }
}
