package kr.ac.konkuk.demo.domain.volunteer.api;

import jakarta.validation.Valid;
import kr.ac.konkuk.demo.domain.volunteer.application.VolunteerService;
import kr.ac.konkuk.demo.domain.volunteer.dto.VolunteerFindDto;
import kr.ac.konkuk.demo.domain.volunteer.dto.VolunteerRegisterDto;
import kr.ac.konkuk.demo.global.resolver.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
public class VolunteerApi {

    private final VolunteerService volunteerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void volunteerRegister(@UserId Long userId,
                                  @RequestBody @Valid VolunteerRegisterDto.Request registerRequest) {
        volunteerService.registerVolunteer(userId, registerRequest.toEntity());
    }

    @GetMapping
    public List<VolunteerFindDto.Response> findVolunteer(@UserId Long userId) {
        return volunteerService.findVolunteer(userId);
    }

}
