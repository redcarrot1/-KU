package kr.ac.konkuk.demo.domain.room.dto;

import kr.ac.konkuk.demo.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class RoomRegisterDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String kakaoUrl;

        private String internetUrl;

        private String title;

        private Integer limitHeadCount;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime closedDateTime;

        private String content;

        public Room toEntity() {
            return Room.builder()
                    .kakaoURL(kakaoUrl)
                    .internetURL(internetUrl)
                    .title(title)
                    .limitHeadCount(limitHeadCount)
                    .closedDateTime(closedDateTime)
                    .content(content)
                    .currentHeadCount(1)
                    .build();
        }
    }

}
