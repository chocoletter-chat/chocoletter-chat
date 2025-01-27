package chocoletter.chat.chat.controller;

import chocoletter.chat.chat.domain.ChatMessage;
import chocoletter.chat.chat.dto.ChatMessageRequestDto;
import chocoletter.chat.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    @PostMapping("/")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessageRequestDto chatMessage) {
        chatMessageService.saveChatMessage(ChatMessage.builder()
                .content(chatMessage.getContent())
                .senderId(chatMessage.getSenderId())
                .build());
        return ResponseEntity.ok().build();
    }
}
