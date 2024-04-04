package Capstone.Capstone.repository;

import Capstone.Capstone.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    @Query("SELECT cr.id FROM ChatRoom cr " +
            "JOIN cr.users u " +
            "WHERE u.id IN :userIds " +
            "GROUP BY cr.id " +
            "HAVING COUNT(DISTINCT u.id) = :userCount")
    List<Long> findChatRoomIdByUserIds(List<String> userIds, int userCount);
}
