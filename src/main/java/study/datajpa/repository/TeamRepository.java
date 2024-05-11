package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Team;

/**
 * study.datajpa.repository TeamRepository
 *
 * @author : K
 */
public interface TeamRepository extends JpaRepository<Team,Long> {}

