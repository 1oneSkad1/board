package com.kh.web.domain.member.svc;

import com.kh.web.domain.entity.Member;

import java.util.Optional;

public interface MemberSVC {
  //회원가입
  Long register(Member member);
  //회원 아이디 조회
  boolean existEmail(String email);
  //회원 조회
  Optional<Member> findByEmailAndPasswd(String email, String passwd);
}
