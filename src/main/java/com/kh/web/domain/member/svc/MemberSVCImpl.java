package com.kh.web.domain.member.svc;

import com.kh.web.domain.entity.Member;
import com.kh.web.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {

  private final MemberDAO memberDAO;

  @Override
  public Long register(Member member) {
    return memberDAO.register(member);
  }

  @Override
  public boolean existEmail(String email) {
    return memberDAO.existEmail(email);
  }

  @Override
  public Optional<Member> findByEmailAndPasswd(String email, String passwd) {
    return memberDAO.findByEmailAndPasswd(email, passwd);
  }
}
