package kr.ac.konkuk.demo.domain.user.application;

import kr.ac.konkuk.demo.domain.auth.email.exception.NotKUEmailException;
import kr.ac.konkuk.demo.domain.user.dao.UserFindDao;
import kr.ac.konkuk.demo.domain.user.dao.UserRepository;
import kr.ac.konkuk.demo.domain.user.dto.TokenDto;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.domain.user.exception.DuplicateUserEmailException;
import kr.ac.konkuk.demo.domain.user.exception.DuplicateUserNicknameException;
import kr.ac.konkuk.demo.domain.user.exception.FailUserLoginException;
import kr.ac.konkuk.demo.global.manager.PasswordEncoder;
import kr.ac.konkuk.demo.global.manager.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRegisterService {
    private final UserRepository userRepository;
    private final UserFindDao userFindDao;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    public void registerUser(User user) {
        if (!user.getEmail().split("@")[1].equals("konkuk.ac.kr")) {
            throw new NotKUEmailException();
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateUserEmailException();
        }
        if (userRepository.existsByNickname(user.getNickname())) {
            throw new DuplicateUserNicknameException();
        }
        user.updateUserPassword(passwordEncoder.encrypt(user.getPassword()));
        userRepository.save(user);
    }

    public TokenDto loginUser(String email, String password) {
        User user = userFindDao.findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new FailUserLoginException();
        }
        return tokenManager.createTokenDto(user.getId());
    }
}
