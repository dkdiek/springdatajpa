package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;

/**
 * study.datajpa.repository MemberRepositoryt
 *
 * @author : K
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

  List<Member> findByUsername(String username);
}
