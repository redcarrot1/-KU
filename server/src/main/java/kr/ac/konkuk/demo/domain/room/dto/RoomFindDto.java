package kr.ac.konkuk.demo.domain.room.dto;

import kr.ac.konkuk.demo.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RoomFindDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private Long id;

        private String title;

        private Integer limitHeadCount;
        private Integer currentHeadCount;

        public static Request of(Room room) {
            return new Request(room.getId(), room.getTitle(), room.getLimitHeadCount(), room.getCurrentHeadCount());
        }
    }
}
