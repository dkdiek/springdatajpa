package study.datajpa.repository;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

/**
 * study.datajpa.repository MemberRepositoryTest
 *
 * @author : K
 */
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {
  @Autowired MemberRepository memberRepository;
  @Autowired TeamRepository teamRepository;
   @PersistenceContext
   private final EntityManager em;
  
  public MemberRepositoryTest(@Autowired EntityManager em) {
    this.em = em;
  }
  
  @Test
  public void testMember() {
    Member member = new Member("memberA");
    Member savedMember = memberRepository.save(member);
    Member findMember = memberRepository.findById(savedMember.getId()).get();
    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    Assertions.assertThat(findMember).isEqualTo(member); // JPA 엔티티 동일성 보장
  }

  @Test
  public void basicCRUD() {
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");
    memberRepository.save(member1);
    memberRepository.save(member2);
    // 단건 조회 검증
    Member findMember1 = memberRepository.findById(member1.getId()).get();
    Member findMember2 = memberRepository.findById(member2.getId()).get();
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);
    // 리스트 조회 검증
    List<Member> all = memberRepository.findAll();
    assertThat(all.size()).isEqualTo(2);
    // 카운트 검증
    long count = memberRepository.count();
    assertThat(count).isEqualTo(2);
    // 삭제 검증
    memberRepository.delete(member1);
    memberRepository.delete(member2);
    long deletedCount = memberRepository.count();
    assertThat(deletedCount).isEqualTo(0);
  }

  @Test
  public void findByUsernameAndAgeGreaterThan() {
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("aaa", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("aaa", 11);
    assertThat(result.get(0).getUsername()).isEqualTo("aaa");
    assertThat(result.get(0).getAge()).isEqualTo(20);
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  public void findHelloBy() {
    List<Member> helloBy = memberRepository.findTop3HelloBy();
  }

  @Test
  public void testQuery() {
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("aaa", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> result = memberRepository.findUser("aaa", 10);
    assertThat(result.get(0)).isEqualTo(m1);
  }

  @Test
  public void findUsernameList() {
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("aaa", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    List<String> result = memberRepository.findUsernameList();
    for (String s : result) {
      System.out.println(s);
    }
  }
  
  @Test
  public void findMemberDto() {
    Team team = new Team("teamA");
    
    teamRepository.save(team);
    
    Member m1 = new Member("aaa", 10);
    m1.setTeam(team);
    memberRepository.save(m1);
    
    List<MemberDto> memberDto = memberRepository.findMemberDto();
    for (MemberDto dto : memberDto) {
      System.out.println(dto);
    }
  }
  
  @Test
  public void findByNames() {
    
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("bbb", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<Member> result = memberRepository.findByNames(Arrays.asList("aaa", "bbb"));
    for (Member member : result) {
      System.out.println(member);
    }
  }
  
  @Test
  public void returnType() {
    
    Member m1 = new Member("aaa", 10);
    Member m2 = new Member("bbb", 20);
    memberRepository.save(m1);
    memberRepository.save(m2);

    List<Member> aaa = memberRepository.findListByUsername("AAA");
    Member bbb = memberRepository.findMemberByUsername("bbb");
    Optional<Member> ccc = memberRepository.findOptionalByUsername("bbb");
  }
  
  @Test
  public void paging(){
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 10));
    memberRepository.save(new Member("member3", 10));
    memberRepository.save(new Member("member4", 10));
    memberRepository.save(new Member("member5", 10));

    int age = 10;
    // spring data jpa는 page 0부터 시작 주의
    PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
    
    Page<Member> members = memberRepository.findByAge(age, pageRequest);

    members.getContent();
    members.getTotalElements();
  }
  
  @Test
  public void bulkUpdate() throws Exception {
//given
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 19));
    memberRepository.save(new Member("member3", 20));
    memberRepository.save(new Member("member4", 21));
    memberRepository.save(new Member("member5", 40));
//when
    int resultCount = memberRepository.bulkAgePlus(20);
//then
    assertThat(resultCount).isEqualTo(3);
  }
  
  @Test
  public void findMemberLazy() throws Exception {
//given
//member1 -> teamA
//member2 -> teamB
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    teamRepository.save(teamA);
    teamRepository.save(teamB);
    memberRepository.save(new Member("member1", 10, teamA));
    memberRepository.save(new Member("member2", 20, teamB));
    em.flush();
    em.clear();
//when
    //멤버 가져오는 쿼리만
    List<Member> members = memberRepository.findAll();
//then
    for (Member member : members) {
      //팀의 내용을 위에서 조회안하고 가짜 껍데기를 가져와서 팀의 정보를 가져오기 위해 팀 조회 쿼리가 실행된다
      member.getTeam().getName();
    }
  }
  
  @Test
  public void queryHint() throws Exception {
//given
    memberRepository.save(new Member("member1", 10));
    em.flush();
    em.clear();
//when
    Member member = memberRepository.findReadOnlyByUsername("member1");
    member.setUsername("member2");
    em.flush(); //Update Query 실행X
  }
  
  @Test
  public void callCustom(){
    List<Member> memberCustom = memberRepository.findMemberCustom();
  }


}
