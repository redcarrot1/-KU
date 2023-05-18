package kr.ac.konkuk.demo.domain.user.application;

import kr.ac.konkuk.demo.domain.user.dao.UserRepository;
import kr.ac.konkuk.demo.domain.user.entity.User;
import kr.ac.konkuk.demo.domain.user.exception.DuplicateUserNameException;
import kr.ac.konkuk.demo.global.manager.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new DuplicateUserNameException();

        user.updateUserPassword(passwordEncoder.encrypt(user.getPassword()));
        userRepository.save(user);
    }
}
