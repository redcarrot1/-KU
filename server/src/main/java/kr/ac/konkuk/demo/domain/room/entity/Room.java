package kr.ac.konkuk.demo.domain.room.entity;

import jakarta.persistence.*;
import kr.ac.konkuk.demo.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kakaoURL;

    @Column(nullable = false)
    private String internetURL;

    private String title;

    private Integer limitHeadCount;

    private Integer currentHeadCount;

    private LocalDateTime closedDateTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "host_user_id")
    private User hostUser;

    @Lob
    private String content;

    @Builder
    public Room(Long id, String kakaoURL, String internetURL, String title, Integer limitHeadCount,
                Integer currentHeadCount, LocalDateTime closedDateTime, String content) {
        this.id = id;
        this.kakaoURL = kakaoURL;
        this.internetURL = internetURL;
        this.title = title;
        this.limitHeadCount = limitHeadCount;
        this.currentHeadCount = currentHeadCount;
        this.closedDateTime = closedDateTime;
        this.content = content;
    }

    public void updateHostUser(User user) {
        this.hostUser = user;
    }

    public void increaseCurrentHeadCount() {
        this.currentHeadCount++;
    }
}
