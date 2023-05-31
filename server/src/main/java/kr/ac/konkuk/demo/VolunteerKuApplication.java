package kr.ac.konkuk.demo;

import jakarta.annotation.PostConstruct;
import kr.ac.konkuk.demo.domain.user.application.UserRegisterService;
import kr.ac.konkuk.demo.domain.user.entity.Major;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.domain.volunteer.application.VolunteerService;
import kr.ac.konkuk.demo.domain.volunteer.entity.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class VolunteerKuApplication {

    public static void main(String[] args) {
        SpringApplication.run(VolunteerKuApplication.class, args);
    }

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private VolunteerService volunteerService;

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

        volunteerService.registerVolunteer(1L,
                Volunteer.builder()
                        .date(LocalDate.now())
                        .content("유기견 봉사활동")
                        .minuteTime(150)
                        .build());

        volunteerService.registerVolunteer(1L,
                Volunteer.builder()
                        .date(LocalDate.of(2021, 5, 1))
                        .content("서울숲 청소")
                        .minuteTime(30)
                        .build());
    }
}
