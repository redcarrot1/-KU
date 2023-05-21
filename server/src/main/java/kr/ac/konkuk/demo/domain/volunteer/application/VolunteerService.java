package kr.ac.konkuk.demo.domain.volunteer.application;

import kr.ac.konkuk.demo.domain.user.dao.UserFindDao;
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
    private final UserFindDao userFindDao;

    public void registerVolunteer(Long userId, Volunteer volunteer) {
        User user = userFindDao.findById(userId);
        volunteer.updateUser(user);
        volunteerRepository.save(volunteer);
    }
}
