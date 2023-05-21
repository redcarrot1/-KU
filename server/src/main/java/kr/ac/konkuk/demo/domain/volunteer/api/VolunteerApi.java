package kr.ac.konkuk.demo.domain.volunteer.api;

import jakarta.validation.Valid;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.domain.volunteer.application.VolunteerService;
import kr.ac.konkuk.demo.domain.volunteer.dto.VolunteerRegisterDto;
import kr.ac.konkuk.demo.global.resolver.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
public class VolunteerApi {

    private final VolunteerService volunteerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void volunteerRegister(@UserEntity User user,
                                  @RequestBody @Valid VolunteerRegisterDto.Request registerRequest) {
        volunteerService.registerVolunteer(user, registerRequest.toEntity());
    }

}
