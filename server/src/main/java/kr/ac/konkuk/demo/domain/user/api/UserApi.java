package kr.ac.konkuk.demo.domain.user.api;

import jakarta.validation.Valid;
import kr.ac.konkuk.demo.domain.user.application.UserProfileService;
import kr.ac.konkuk.demo.domain.user.application.UserRegisterService;
import kr.ac.konkuk.demo.domain.user.dto.*;
import kr.ac.konkuk.demo.global.resolver.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserRegisterService userRegisterService;
    private final UserProfileService userProfileService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void userRegister(@RequestBody @Valid UserRegisterDto.Request registerRequest) {
        userRegisterService.registerUser(registerRequest.toEntity());
    }

    @GetMapping("/duplication")
    public HashMap<String, Object> nicknameDuplication(@Param("nickname") String nickname) {
        boolean result = userProfileService.checkDuplicateNickname(nickname);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("isDuplicate", result);
        return map;
    }

    @PostMapping("/login")
    public TokenDto userLogin(@RequestBody @Valid UserLoginDto.Request request) {
        TokenDto tokenDto = userRegisterService.loginUser(request.getEmail(), request.getPassword());
        return tokenDto;
    }

    @PostMapping("/password")
    public void changePasswordWithoutLogin(@RequestBody @Valid PasswordChangeWithoutLoginDto.Request request) {
        userProfileService.changePassword(request.getEmail(), request.getPassword());
    }

    @PostMapping("/change/password")
    public void changePassword(@UserId Long userId, @RequestBody @Valid PasswordChangeDto.Request request) {
        userProfileService.changePassword(userId, request.getPassword());
    }

    @PostMapping("/change/nickname")
    public void changeNickname(@UserId Long userId, @RequestBody @Valid NicknameChangeDto.Request request) {
        userProfileService.changeNickname(userId, request.getNickname());
    }

    @PostMapping("/change/introduction")
    public void changeIntroduction(@UserId Long userId, @RequestBody @Valid IntroductionChangeDto.Request request) {
        userProfileService.changeIntroduction(userId, request.getIntroduction());
    }

    @PostMapping("/change/image")
    public void changeImage(@UserId Long userId, @RequestBody @Valid ImageChangeDto.Request request) {
        userProfileService.changeImageUrl(userId, request.getImageUrl());
    }

    @GetMapping("/exist")
    public HashMap<String, Object> existCheckUserByEmail(@Param("email") String email) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (userProfileService.existUserByEmail(email)) map.put("isExist", true);
        else map.put("isExist", false);
        return map;
    }
}
