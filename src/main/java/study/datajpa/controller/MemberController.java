package study.datajpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

/**
 * study.datajpa.controller MemberController
 *
 * @author : K
 */
@RestController
@RequiredArgsConstructor
public class MemberController {
  private final MemberRepository memberRepository;

  @GetMapping("/members/{id}")
  public String findMember(@PathVariable("id") Long id) {
    Member member = memberRepository.findById(id).get();
    return member.getUsername();
  }
  
  @GetMapping("/members2/{id}")
  public String findMember(@PathVariable("id") Member member) {
    return member.getUsername();
  }
  
  @GetMapping("/members")
  public Page<MemberDto> list(Pageable pageable) {
    return memberRepository.findAll(pageable).map(MemberDto::new);
  }

//  @PostConstruct //존성 주입이 이루어진 후 초기화를 수행하는 메서드
  public void init() {
    for(int i = 0; i < 100; i++) {
      memberRepository.save(new Member("user" + i, i));
    }
  }
}
