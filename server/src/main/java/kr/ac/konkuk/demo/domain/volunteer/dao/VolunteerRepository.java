package kr.ac.konkuk.demo.domain.volunteer.dao;

import kr.ac.konkuk.demo.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
