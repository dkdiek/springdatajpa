package study.datajpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * study.datajpa.entity MemberTest
 *
 * @author : K
 */
@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

  @Autowired MemberRepository memberRepository;
  @PersistenceContext EntityManager em;

  @Test
  public void testEntity() {
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    em.persist(teamA);
    em.persist(teamB);

    Member member1 = new Member("memeber1", 10, teamA);
    Member member2 = new Member("memeber1", 10, teamA);
    Member member3 = new Member("memeber1", 10, teamB);
    Member member4 = new Member("memeber1", 10, teamB);

    em.persist(member1);
    em.persist(member2);
    em.persist(member3);
    em.persist(member4);

    /*초기화*/
    em.flush();
    em.clear(); // JPA 영속성 캐시 날리기

    /*확인*/
    List<Member> selectMFromMemberM =
        em.createQuery("select m from Member m", Member.class).getResultList();
    for (Member member : selectMFromMemberM) {
      System.out.println("member - " + member);
      System.out.println("member.team - " + member.getTeam());
      System.out.println("------------------------------");
    }
  }

  @Test
  public void jpaEventBaseEntity() throws Exception {
    // given
    Member member = new Member("member1");
    memberRepository.save(member); // @PrePersist
    Thread.sleep(100);
    member.setUsername("member2");
    em.flush(); // @PreUpdate
    em.clear();
    // when
    Member findMember = memberRepository.findById(member.getId()).get();
    // then
    System.out.println("findMember.createdDate = " + findMember.getCreatedDate());
    System.out.println("findMember.updatedDate = " + findMember.getLastModifiedDate());
    System.out.println("findMember.3 = " + findMember.getCreatedBy());
    System.out.println("findMember.4 = " + findMember.getLastModifiedBy());
  }
}
