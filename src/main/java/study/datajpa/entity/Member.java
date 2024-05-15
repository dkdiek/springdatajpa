package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * study.datajpa.entity Member
 *
 * @author : K
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"}) // team 적으면 연관관계 적어서 출력되서 문제 발생 무한루프 연관관계 필드는 tostring X
@NamedQuery(
        name="MemberJPQL.findByName",
        query="select m from Member m where m.username = :username"
)
public class Member extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String username;
  private int age;

  @ManyToOne(fetch = FetchType.LAZY) // Many To One fetch Lazy로 세팅
  @JoinColumn(name = "team_id")
  private Team team;

  public Member(String username) {
    this.username = username;
  }

  public Member(String username, int age, Team team) {
    this.username = username;
    this.age = age;
    if (team != null) {
      changeTeam(team);
    }
  }

  public Member(String username, int age) {
    this.username = username;
    this.age = age;
  }

  public void changeTeam(Team team) {
    this.team = team;
    team.getMembers().add(this);
  }
}
