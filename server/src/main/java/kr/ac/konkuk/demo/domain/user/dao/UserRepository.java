package kr.ac.konkuk.demo.domain.user.dao;

import kr.ac.konkuk.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
