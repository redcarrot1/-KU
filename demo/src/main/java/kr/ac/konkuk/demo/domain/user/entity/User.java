package kr.ac.konkuk.demo.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String imageUrl;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Major major;

    @Lob
    private String introduction;

    private Integer volunteerTime;

    private Integer goodCount;

    private Integer badCount;

    @Builder
    public User(String password, String nickname, String imageUrl, String email, Major major, String introduction) {
        this.password = password;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.email = email;
        this.major = major;
        this.introduction = introduction;
        this.volunteerTime = 0;
        this.goodCount = 0;
        this.badCount = 0;
    }

    public void updateUserPassword(String password) {
        this.password = password;
    }
}
