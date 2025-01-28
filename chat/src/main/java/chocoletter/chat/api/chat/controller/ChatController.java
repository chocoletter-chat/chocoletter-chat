package chocoletter.chat.api.chat.controller;

import chocoletter.chat.api.chat.domain.ChatMessage;
import chocoletter.chat.api.chat.dto.request.ChatMessageRequestDto;
import chocoletter.chat.api.chat.dto.response.ChatMessageResponseDto;
import chocoletter.chat.api.chat.repository.ChatMessageRepository;
import chocoletter.chat.api.chat.service.ChatMessageService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/send")
    public void sendMessage(ChatMessageRequestDto chatMessageRequestDto) {
        // 어느 방(어느 채널)로 가야되는 메시지인지 설정하는 부분
        String topic = "/topic/" + chatMessageRequestDto.getRoomId();
        // RequestDto를 받아서 ResponseDto로 만들어서 전달하는 부분(실시간이 중요하다고 생각해서 일단 상대한테 보낸 뒤에 저장하는 로직으로 짬)
        messagingTemplate.convertAndSend(topic, ChatMessageResponseDto.builder()
                .senderId(chatMessageRequestDto.getSenderId())
                .content(chatMessageRequestDto.getContent())
                .read(false)
                .createdAt(String.valueOf(LocalDateTime.now()))
                .build());
        // 채팅이 날아온 걸 DB에 저장하는 부분
        chatMessageService.saveChatMessage(ChatMessage.builder()
                .senderId(chatMessageRequestDto.getSenderId())
                .roomId(chatMessageRequestDto.getRoomId())
                .content(chatMessageRequestDto.getContent())
                .build());
    }
}
