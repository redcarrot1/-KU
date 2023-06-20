package kr.ac.konkuk.demo.domain.room.application;

import kr.ac.konkuk.demo.domain.room.dao.RoomRepository;
import kr.ac.konkuk.demo.domain.room.dto.RoomFindDetailDto;
import kr.ac.konkuk.demo.domain.room.dto.RoomFindDto;
import kr.ac.konkuk.demo.domain.room.dto.RoomTitleListDto;
import kr.ac.konkuk.demo.domain.room.entity.Room;
import kr.ac.konkuk.demo.domain.room.exception.AlreadyParticipationException;
import kr.ac.konkuk.demo.domain.room.exception.FullRoomException;
import kr.ac.konkuk.demo.domain.room.exception.RoomNotFoundException;
import kr.ac.konkuk.demo.domain.user.dao.UserFindDao;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.domain.userRoom.dao.UserRoomRepository;
import kr.ac.konkuk.demo.domain.userRoom.entity.UserRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;
    private final UserFindDao userFindDao;

    public void registerRoom(Long userId, Room room) {
        User user = userFindDao.findById(userId);
        room.updateHostUser(user);
        roomRepository.save(room);

        UserRoom userRoom = UserRoom.builder()
                .room(room)
                .user(user)
                .build();

        userRoomRepository.save(userRoom);
    }

    @Transactional(readOnly = true)
    public List<RoomFindDto.Request> findRoom() {
        return roomRepository.findAll()
                .stream()
                .filter(room -> room.getClosedDateTime().isAfter(LocalDateTime.now()))
                .map(RoomFindDto.Request::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomFindDetailDto.Request findRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(RoomNotFoundException::new);
        return RoomFindDetailDto.Request.of(room);
    }

    public String joinRoom(Long userId, Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(RoomNotFoundException::new);
        if (Objects.equals(room.getLimitHeadCount(), room.getCurrentHeadCount())) {
            throw new FullRoomException();
        }

        User user = userFindDao.findById(userId);
        if (userRoomRepository.existsByUserAndRoom(user, room)) {
            throw new AlreadyParticipationException();
        }

        UserRoom userRoom = UserRoom.builder()
                .room(room)
                .user(user)
                .build();
        userRoomRepository.save(userRoom);
        room.increaseCurrentHeadCount();
        return room.getTitle();
    }

    @Transactional(readOnly = true)
    public List<RoomTitleListDto> findJoinRoom(Long userId) {
        return userRoomRepository.findByUserId(userId)
                .stream()
                .filter(userRoom -> userRoom.getRoom().getClosedDateTime().isAfter(LocalDateTime.now()))
                .map(userRoom -> new RoomTitleListDto(userRoom.getRoom().getId(), userRoom.getRoom().getTitle()))
                .toList();
    }
}
