package team.jlpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.jlpt.entity.Learn;

@Repository
public interface LearnRepository extends JpaRepository<Learn, Long> {
}
