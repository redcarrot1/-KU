package kr.ac.konkuk.demo.domain.room.application;

import kr.ac.konkuk.demo.domain.room.dao.RoomRepository;
import kr.ac.konkuk.demo.domain.room.entity.Room;
import kr.ac.konkuk.demo.domain.user.dao.UserFindDao;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.domain.userRoom.dao.UserRoomRepository;
import kr.ac.konkuk.demo.domain.userRoom.entity.UserRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;
    private final UserFindDao userFindDao;

    public void registerRoom(Long userId, Room entity) {
        User user = userFindDao.findById(userId);
        entity.updateHostUser(user);
        roomRepository.save(entity);

        UserRoom userRoom = UserRoom.builder()
                .room(entity)
                .user(user)
                .build();
        userRoomRepository.save(userRoom);
    }
}
