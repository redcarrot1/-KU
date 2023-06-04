package kr.ac.konkuk.demo.domain.room.api;

import jakarta.validation.Valid;
import kr.ac.konkuk.demo.domain.room.application.RoomService;
import kr.ac.konkuk.demo.domain.room.dto.RoomFindDetailDto;
import kr.ac.konkuk.demo.domain.room.dto.RoomFindDto;
import kr.ac.konkuk.demo.domain.room.dto.RoomRegisterDto;
import kr.ac.konkuk.demo.domain.room.dto.RoomTitleListDto;
import kr.ac.konkuk.demo.global.resolver.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomApi {

    private final RoomService roomService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void roomRegister(@UserId Long userId,
                             @RequestBody @Valid RoomRegisterDto.Request request) {
        roomService.registerRoom(userId, request.toEntity());
    }

    @GetMapping
    public List<RoomFindDto.Request> findRoom() {
        return roomService.findRoom();
    }

    @GetMapping("/detail/{id}")
    public RoomFindDetailDto.Request findRoomDetail(@PathVariable Long id) {
        return roomService.findRoomById(id);
    }

    @PostMapping("/applications")
    public HashMap<String, Object> joinRoom(@UserId Long userId, @RequestBody HashMap<String, Object> request) {
        String title = roomService.joinRoom(userId, (Long) request.get("id"));
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", title);
        return map;
    }

    @GetMapping("/applicationsview")
    public RoomTitleListDto findJoinRoom(@UserId Long userId) {
        List<String> title = roomService.findJoinRoom(userId);
        return new RoomTitleListDto(title);
    }
}
