package kr.ac.konkuk.demo.domain.room.dto;

import kr.ac.konkuk.demo.domain.room.entity.Room;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class RoomFindDetailDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        private String kakaoUrl;

        private String internetUrl;

        private String title;

        private Integer limitHeadCount;
        private Integer currentHeadCount;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime closedDateTime;

        private String content;

        public static RoomFindDetailDto.Request of(Room room) {
            return Request.builder()
                    .kakaoUrl(room.getKakaoURL())
                    .internetUrl(room.getInternetURL())
                    .title(room.getTitle())
                    .limitHeadCount(room.getLimitHeadCount())
                    .closedDateTime(room.getClosedDateTime())
                    .content(room.getContent())
                    .currentHeadCount(room.getCurrentHeadCount())
                    .build();
        }
    }
}
