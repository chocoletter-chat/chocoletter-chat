package chocoletter.chat.api.chat.repository;

import chocoletter.chat.api.chat.domain.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    @Query(value = "{ 'roomId' : ?0 }", sort = "{ 'createdAt' : -1 }")
    List<ChatMessage> findChatMessages(String roomId, Pageable pageable);
}
