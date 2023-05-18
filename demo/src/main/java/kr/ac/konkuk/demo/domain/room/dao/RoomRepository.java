package kr.ac.konkuk.demo.domain.room.dao;

import kr.ac.konkuk.demo.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
