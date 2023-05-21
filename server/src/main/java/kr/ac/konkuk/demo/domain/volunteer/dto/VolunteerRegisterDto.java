package kr.ac.konkuk.demo.domain.volunteer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kr.ac.konkuk.demo.domain.volunteer.entity.Volunteer;
import kr.ac.konkuk.demo.global.validation.annotation.LocalDateValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VolunteerRegisterDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotBlank(message = "내용은 필수값입니다.")
        private String content;

        @Min(value = 1, message = "시간은 1분 이상이어야 합니다.")
        private Integer minuteTime;

        @LocalDateValid
        private String date;

        public Volunteer toEntity() {
            return Volunteer.builder()
                    .content(content)
                    .minuteTime(minuteTime)
                    .date(LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                    .build();
        }
    }
}
