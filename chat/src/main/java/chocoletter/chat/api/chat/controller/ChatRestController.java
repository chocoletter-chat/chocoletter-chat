package chocoletter.chat.api.chat.controller;

import chocoletter.chat.api.chat.dto.response.ChatMessagesResponseDto;
import chocoletter.chat.api.chat.dto.response.LastChatMessageResponseDto;
import chocoletter.chat.api.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatRestController {
    private final ChatMessageService chatMessageService;

    @GetMapping("/{roomId}/all")
    public ResponseEntity<?> findChatMessages(@PathVariable("roomId") String roomId,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "20") Integer size) {
        ChatMessagesResponseDto chatMessages = chatMessageService.findChatMessages(roomId, page, size);
        return ResponseEntity.ok(chatMessages);
    }

    @GetMapping("/{roomId}/{memberId}/last")
    public ResponseEntity<?> findLastChatMessage(@PathVariable("roomId") String roomId,
                                                 @PathVariable("memberId") Long memberId) {
        LastChatMessageResponseDto lastChatMessage = chatMessageService.findLastChatMessage(roomId, memberId);
        return ResponseEntity.ok(lastChatMessage);
    }
}
