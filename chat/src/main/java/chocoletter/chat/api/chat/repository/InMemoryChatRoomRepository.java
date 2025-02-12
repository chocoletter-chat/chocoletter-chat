package chocoletter.chat.api.chat.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Getter
public class InMemoryChatRoomRepository {

    private final Map<String, List<String>> chatRooms = new ConcurrentHashMap<>(); // key : roomId, value : 멤버 id 리스트

    // 특정 roomId에 접속한 멤버 리스트 가져오기
    public List<String> findOnlineMembers(String roomId) {
        return chatRooms.getOrDefault(roomId, new ArrayList<>());
    }

    // 채팅방 저장 (입장 내역 추가)
    public void save(String roomId, String memberId) {
        chatRooms.computeIfAbsent(roomId, k -> new ArrayList<>()).add(memberId);
        System.out.println("========== after connect chat room ==========");
        System.out.println(chatRooms);
        System.out.println("========== after connect members ==========");
    }

    // 채팅방 삭제 (퇴장 시)
    public void remove(String roomId, String memberId) {
        chatRooms.computeIfPresent(roomId, (key, rooms) -> {
            rooms.removeIf(id -> Objects.equals(id, memberId));
            return rooms.isEmpty() ? null : rooms;
        });
        System.out.println("========== after remove chat room ==========");
        System.out.println(chatRooms);
        System.out.println("========== after remove members ==========");
    }
}

