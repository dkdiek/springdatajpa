package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * study.datajpa.entity Team
 *
 * @author : K
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","name"})
public class Team {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;
  private String name;

  // one에 mappedby 추가, 즉 FK가 없는쪽에 권장
  @OneToMany(mappedBy = "team")
  private List<Member> members = new ArrayList<>();
  
  public Team(String name) {
    this.name = name;
  }
}
