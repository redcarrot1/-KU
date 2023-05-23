package kr.ac.konkuk.demo.domain.volunteer.dto;

import kr.ac.konkuk.demo.domain.volunteer.entity.Volunteer;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerFindDto {

    List<Response> volunteerList = new ArrayList<>();

    @Data
    public static class Response {

        private String content;
        private Integer minuteTime;
        private String date;

        public Response(String content, Integer minuteTime, String date) {
            this.content = content;
            this.minuteTime = minuteTime;
            this.date = date;
        }

        public static VolunteerFindDto.Response of(Volunteer volunteer) {
            return new VolunteerFindDto.Response(
                    volunteer.getContent(),
                    volunteer.getMinuteTime(),
                    volunteer.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
    }
}
