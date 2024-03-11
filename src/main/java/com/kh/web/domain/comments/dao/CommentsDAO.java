package com.kh.web.domain.comments.dao;

import com.kh.web.domain.entity.Comments;

import java.util.List;

public interface CommentsDAO {
  //댓글 작성
  Long saveComments(Comments comments);

  //댓글 삭제
  int deleteByCommentsId(Long commentsId);

  //댓글 수정
  int updateByCommentsId(Comments comments);

  //댓글 목록
  List<Comments> findCommentsAll(Long boardId);
  //목록(페이징)
  List<Comments> findAll(Long reqPage, Long recordCnt);

  //총 댓글 건수
  int totalCnt();

}
