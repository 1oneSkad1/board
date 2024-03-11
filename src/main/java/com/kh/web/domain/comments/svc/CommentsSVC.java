package com.kh.web.domain.comments.svc;

import com.kh.web.domain.entity.Comments;

import java.util.List;

public interface CommentsSVC {

  //댓글 작성
  Long saveComments(Comments comments);

  //댓글 삭제
  int deleteByCommentsId(Long commentsId);

  //댓글 수정
  int updateByCommentsId(Comments comments);

  //댓글 목록
  List<Comments> findCommentsAll(Long boardId);

  //총 댓글 건수
  int totalCnt();
}
