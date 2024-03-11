package com.kh.web.domain.member.dao;

import com.kh.web.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberDAOImplTest {

  @Autowired
  MemberDAO memberDAO;

  @Test
  @DisplayName("회원가입")
  void register() {
    Member member = new Member();
    member.setEmail("user3@kh.com");
    member.setPasswd("user3");
    member.setNickname("사용자3");
    Long memberId = memberDAO.register(member);
    log.info("memberId={}", memberId);
  }

  @Test
  @DisplayName("이메일 중복")
  void existEmail() {
    boolean exit = memberDAO.existEmail("user1@kh.com");
    Assertions.assertThat(exit).isEqualTo(false);
  }

  @Test
  @DisplayName("회원조회")
  void findByEmailAndPasswd() {
    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd("user1@kh.com", "user1");
    // 결과 검증
    Assertions.assertThat(optionalMember).isPresent(); // Optional이 존재해야 함

    Member findedMember = optionalMember.get();
    Assertions.assertThat(findedMember.getEmail()).isEqualTo("user1@kh.com"); // 이메일 일치 여부 확인
    Assertions.assertThat(findedMember.getPasswd()).isEqualTo("user1"); // 비밀번호 일치 여부 확인
  }
}