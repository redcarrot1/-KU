package kr.ac.konkuk.demo.domain.volunteer.application;

import kr.ac.konkuk.demo.domain.user.dao.UserRepository;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.domain.volunteer.dao.VolunteerRepository;
import kr.ac.konkuk.demo.domain.volunteer.entity.Volunteer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;

    public void registerVolunteer(User user, Volunteer volunteer) {
        User user1 = userRepository.findById(user.getId()).get();
        volunteer.updateUser(user1);
        volunteerRepository.save(volunteer);
    }
}
