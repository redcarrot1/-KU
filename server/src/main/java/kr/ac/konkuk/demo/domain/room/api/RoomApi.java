package kr.ac.konkuk.demo.domain.room.api;

import jakarta.validation.Valid;
import kr.ac.konkuk.demo.domain.room.application.RoomService;
import kr.ac.konkuk.demo.domain.room.dto.RoomRegisterDto;
import kr.ac.konkuk.demo.global.resolver.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
