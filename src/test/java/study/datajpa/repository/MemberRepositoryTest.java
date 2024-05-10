package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * study.datajpa.repository MemberRepositoryTest
 *
 * @author : K
 */
@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
  @Autowired
  MemberRepository memberRepository;
  
  @Test
  public void testMember(){
    Member member = new Member("memberA");
    Member savedMember = memberRepository.save(member);
    
    Member findMember = memberRepository.findById(savedMember.getId()).get();

    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    assertThat(findMember).isEqualTo(member);
  }
}