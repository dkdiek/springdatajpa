package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

/**
 * study.datajpa.repository MemberRepositoryt
 *
 * @author : K
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

}
