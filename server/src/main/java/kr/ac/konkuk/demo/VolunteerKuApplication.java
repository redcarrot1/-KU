package kr.ac.konkuk.demo;

import jakarta.annotation.PostConstruct;
import kr.ac.konkuk.demo.domain.room.application.RoomService;
import kr.ac.konkuk.demo.domain.room.entity.Room;
import kr.ac.konkuk.demo.domain.user.application.UserRegisterService;
import kr.ac.konkuk.demo.domain.user.entity.Major;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.domain.volunteer.application.VolunteerService;
import kr.ac.konkuk.demo.domain.volunteer.entity.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class VolunteerKuApplication {

    public static void main(String[] args) {
        SpringApplication.run(VolunteerKuApplication.class, args);
    }

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private RoomService roomService;

    @PostConstruct
    public void init() {
        userRegisterService.registerUser(User.builder()
                .email("test@konkuk.ac.kr")
                .password("testtest123")
                .nickname("tester1")
                .major(Major.valueOf("컴퓨터공학부"))
                .imageUrl("default.png")
                .introduction("안녕하세요! 저는 홍승택입니다. 좋은 사람들과 새로운 경험을 나누는 것을 좋아하고, 서로에 대해 더 알아가면 좋겠습니다. 저와 함께 하시겠어요?")
                .build());

        userRegisterService.registerUser(User.builder()
                .email("dsh33@konkuk.ac.kr")
                .password("testtest123")
                .nickname("heyhey3")
                .major(Major.valueOf("컴퓨터공학부"))
                .imageUrl("default.png")
                .introduction("안녕하세요 저는 코딩 좋아하는 컴공인입니다.")
                .build());

        userRegisterService.registerUser(User.builder()
                .email("gogo11@konkuk.ac.kr")
                .password("tteesstt123")
                .nickname("myname55")
                .major(Major.valueOf("국어국문학과"))
                .imageUrl("default.png")
                .introduction("안녕하세요 저는 문학소녀입니다.")
                .build());
        userRegisterService.registerUser(User.builder()
                .email("math123@konkuk.ac.kr")
                .password("theone123")
                .nickname("kimiki2")
                .major(Major.valueOf("수학과"))
                .imageUrl("default.png")
                .introduction("안녕하세요 수학은 너무 재미있는 과목입니다.")
                .build());
        userRegisterService.registerUser(User.builder()
                .email("mediaGood@konkuk.ac.kr")
                .password("yeop55d")
                .nickname("hyun99")
                .major(Major.valueOf("미디어커뮤니케이션학과"))
                .imageUrl("default.png")
                .introduction("안녕하세요 저는 요즘에 클라이밍을 취미로 갖고 있습니다.")
                .build());
        userRegisterService.registerUser(User.builder()
                .email("physicalperfect@konkuk.ac.kr")
                .password("qwer789")
                .nickname("gyu78")
                .major(Major.valueOf("물리학과"))
                .imageUrl("default.png")
                .introduction("안녕하세요 저는 우주에 대해 관심이 많습니다.")
                .build());
        userRegisterService.registerUser(User.builder()
                .email("eatfood@konkuk.ac.kr")
                .password("testt90")
                .nickname("jeong33")
                .major(Major.valueOf("식품유통공학과"))
                .imageUrl("default.png")
                .introduction("안녕하세요 저는 빵 가게를 차릴 예정입니다.")
                .build());
        userRegisterService.registerUser(User.builder()
                .email("artMaster@konkuk.ac.kr")
                .password("jarr390")
                .nickname("jjwi22")
                .major(Major.valueOf("산업디자인학과"))
                .imageUrl("default.png")
                .introduction("안녕하세요 저는 요즘에 전시회를 가는 것을 좋아합니다.")
                .build());
        userRegisterService.registerUser(User.builder()
                .email("designislife@konkuk.ac.kr")
                .password("write05")
                .nickname("hwu89")
                .major(Major.valueOf("의상디자인학과"))
                .imageUrl("default.png")
                .introduction("안녕하세요 옷으로 자기 개성을 표현하는 것을 좋아합니다.")
                .build());
        userRegisterService.registerUser(User.builder()
                .email("gazia@konkuk.ac.kr")
                .password("makeid33")
                .nickname("ruq75")
                .major(Major.valueOf("경영학과"))
                .imageUrl("default.png")
                .introduction("안녕하세요 저는 요즘에 주식에 대해 관심이 많습니다.")
                .build());
        userRegisterService.registerUser(User.builder()
                .email("commaster@konkuk.ac.kr")
                .password("foiep123")
                .nickname("sgeo89")
                .major(Major.valueOf("컴퓨터공학부"))
                .imageUrl("default.png")
                .introduction("안녕하세요 저는 발전하는 사람이 되기 위해서 노력합니다.")
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
/*
        roomService.registerRoom(1L, Room.builder()
                .title("유기견 봉사활동 같이 가실분 구해요!")
                .content("6월 5일 광진구에서 유기견 봉사활동 진행하는데 같이 가실분 계신가요? 있으시면 아래 오픈카톡방 링크로 들어와주세요!!")
                .closedDateTime(LocalDateTime.of(2023, 7, 1, 15, 20))
                .currentHeadCount(1)
                .limitHeadCount(4)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.registerRoom(2L, Room.builder()
                .title("어르신을 위한 환경 정화 봉사 참여자 모집")
                .content("휠체어 청소, 계당 청소 등 청소 위주로 진행하는 봉사활동입니다! 관심있으신 분들 참여 부탁드려용")
                .closedDateTime(LocalDateTime.of(2023, 7, 1, 16, 30))
                .currentHeadCount(1)
                .limitHeadCount(6)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.registerRoom(2L, Room.builder()
                .title("장애인 탁구대회 행사 뛰실 분?")
                .content("선수분들 지원하는 봉사활동입니다! 탁구대회 공줍기나 선수 지원활동 같은거 진행할 예정이에요!")
                .closedDateTime(LocalDateTime.of(2023, 7, 1, 19, 30))
                .currentHeadCount(1)
                .limitHeadCount(5)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.registerRoom(4L, Room.builder()
                .title("도서관운영및 서가정리 같이해요~~")
                .content("말그대로 도서관운영과 서가정리 하는 봉사활동입니다~~ 오픈채팅방 링크로 들어와주시면 됩니다~~")
                .closedDateTime(LocalDateTime.of(2023, 7, 2, 11, 10))
                .currentHeadCount(1)
                .limitHeadCount(9)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.registerRoom(4L, Room.builder()
                .title("치매안심센터 방문자 안내 및 업무 보조 봉사활동 같이 진행해요")
                .content("방문인분들 안내만 해드리면 되는 간단한 봉사활동이에요 같이해요")
                .closedDateTime(LocalDateTime.of(2023, 7, 2, 13, 40))
                .currentHeadCount(1)
                .limitHeadCount(6)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.registerRoom(5L, Room.builder()
                .title("어르신 미용 자원봉사 참여해주실분 계신가요?")
                .content("어르신분들 머리 관리해주는 봉사활동입니다. 미용사 자격있으신분만 카톡방 입장해주세요.")
                .closedDateTime(LocalDateTime.of(2023, 7, 2, 15, 50))
                .currentHeadCount(1)
                .limitHeadCount(3)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.registerRoom(6L, Room.builder()
                .title("우편봉사 인원모집")
                .content("간단한 우편작업 진행합니다. 같이하실분 모집합니다.")
                .closedDateTime(LocalDateTime.of(2023, 7, 2, 17, 30))
                .currentHeadCount(1)
                .limitHeadCount(5)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.registerRoom(6L, Room.builder()
                .title("복지관 식당 홀청소 봉사활동 같이 가주실분 계신가요ㅠㅠ")
                .content("혼자 가려니 외로울거같아서 구해봅니다...같이 가주실분 계시면 오픈채팅방 입장해주세요")
                .closedDateTime(LocalDateTime.of(2023, 7, 2, 19, 40))
                .currentHeadCount(1)
                .limitHeadCount(3)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.registerRoom(7L, Room.builder()
                .title("광진구 우리동네 키움센터 봉사 같이해요")
                .content("초등학생 놀이지도가 주 봉사활동입니다! 하고싶으신분들은 카톡방 들어와서 자세한거 물어봐주세요!")
                .closedDateTime(LocalDateTime.of(2023, 7, 3, 11, 0))
                .currentHeadCount(1)
                .limitHeadCount(7)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.registerRoom(9L, Room.builder()
                .title("도시락배달 봉사자 모집합니다")
                .content("봉사활동 총 2시간 인정됩니다. 같이 걸으면서 봉사하실분 계시면 같이 하면 좋을 것 같습니다.")
                .closedDateTime(LocalDateTime.of(2023, 7, 3, 15, 30))
                .currentHeadCount(1)
                .limitHeadCount(8)
                .internetURL("https://www.naver.com")
                .kakaoURL("https://open.kakao.com/o/sKitPvnf")
                .build()
        );

        roomService.joinRoom(1L, 2L);
        roomService.joinRoom(2L, 1L);
        roomService.joinRoom(3L, 2L);
        roomService.joinRoom(2L, 4L);
        roomService.joinRoom(3L, 6L);
        roomService.joinRoom(1L, 7L);
 */
    }
}
