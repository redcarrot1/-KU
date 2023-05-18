package kr.ac.konkuk.demo.domain.userRoom.dao;

import kr.ac.konkuk.demo.domain.userRoom.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {

}
