package study.datajpa.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.datajpa.entity.Member;

/**
 * dto MemberDto
 *
 * @author : K
 */
@Data
public class MemberDto {
  private Long id;
  private String username;
  public MemberDto(Member m) {
    this.id = m.getId();
    this.username = m.getUsername();
  }
}
