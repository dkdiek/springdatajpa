package study.datajpa.repository;

import study.datajpa.entity.Member;

import java.util.List;

/**
 * study.datajpa.repository MemberRepositoryCustom
 *
 * @author : K
 */
public interface MemberRepositoryCustom {
  List<Member> findMemberCustom();
}
