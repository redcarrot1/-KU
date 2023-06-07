package kr.ac.konkuk.demo.domain.userRoom.dao;

import kr.ac.konkuk.demo.domain.room.entity.Room;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.domain.userRoom.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {
    List<UserRoom> findByUserId(Long userId);

    Boolean existsByUserAndRoom(User user, Room room);
}
