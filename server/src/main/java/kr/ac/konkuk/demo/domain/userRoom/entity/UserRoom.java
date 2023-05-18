package kr.ac.konkuk.demo.domain.userRoom.entity;

import jakarta.persistence.*;
import kr.ac.konkuk.demo.domain.room.entity.Room;
import kr.ac.konkuk.demo.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_room")
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public UserRoom(User user, Room room) {
        this.user = user;
        this.room = room;
    }
}
