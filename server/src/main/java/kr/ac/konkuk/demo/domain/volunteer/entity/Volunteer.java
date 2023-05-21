package kr.ac.konkuk.demo.domain.volunteer.entity;

import jakarta.persistence.*;
import kr.ac.konkuk.demo.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer minuteTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;

    @Builder
    public Volunteer(String content, Integer minuteTime, LocalDate date) {
        this.content = content;
        this.minuteTime = minuteTime;
        this.date = date;
    }

    public void updateUser(User user) {
        this.user = user;
        user.getVolunteers().add(this);
    }
}
