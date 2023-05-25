package kr.ac.konkuk.demo;

import jakarta.annotation.PostConstruct;
import kr.ac.konkuk.demo.domain.user.application.UserRegisterService;
import kr.ac.konkuk.demo.domain.user.entity.Major;
import kr.ac.konkuk.demo.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VolunteerKuApplication {

    public static void main(String[] args) {
        SpringApplication.run(VolunteerKuApplication.class, args);
    }

    @Autowired
    private UserRegisterService userRegisterService;

    @PostConstruct
    public void init() {
        userRegisterService.registerUser(User.builder()
                .email("test@konkuk.ac.kr")
                .password("testtest123")
                .nickname("tester1")
                .major(Major.valueOf("컴퓨터공학부"))
                .imageUrl("default.png")
                .introduction("자기소개")
                .build());
    }
}
