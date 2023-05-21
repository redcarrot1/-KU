package kr.ac.konkuk.demo.domain.user.application;

import kr.ac.konkuk.demo.domain.user.dao.UserFindDao;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.global.manager.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileService {
    private final UserFindDao userFindDao;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(Long userId, String password) {
        User user = userFindDao.findById(userId);
        user.updateUserPassword(passwordEncoder.encrypt(password));
    }

    public void changeNickname(Long userId, String nickname) {
        User user = userFindDao.findById(userId);
        user.updateUserNickname(nickname);
    }

    public void changeIntroduction(Long userId, String introduction) {
        User user = userFindDao.findById(userId);
        user.updateUserIntroduction(introduction);
    }

    public void changeImageUrl(Long userId, String imageUrl) {
        User user = userFindDao.findById(userId);
        user.updateUserImageUrl(imageUrl);
    }
}
