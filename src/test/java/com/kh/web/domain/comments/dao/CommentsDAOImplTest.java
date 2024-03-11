package com.kh.web.domain.comments.dao;

import com.kh.web.domain.entity.Comments;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class CommentsDAOImplTest {

  @Autowired
  CommentsDAO commentsDAO;

  @Test
  @DisplayName("댓글 등록")
  void saveComments() {
    Comments comments = new Comments();
    comments.setCommentsContent("가나다라");
    comments.setWriter("사용자1");
    Long commentsId = commentsDAO.saveComments(comments);
    log.info("commentsId={}",commentsId);
  }

  @Test
  @DisplayName("댓글 목록")
  void findCommentsAll() {
    List<Comments> list = commentsDAO.findCommentsAll(1L);
    log.info("list={}",list);
  }

  @Test
  void totalCnt() {
    commentsDAO.totalCnt();
  }

  @Test
  void deleteByCommentsId() {
    commentsDAO.deleteByCommentsId(1L);
  }

  @Test
  void updateByCommentsId() {
    Comments comments = new Comments();
    comments.setCommentsContent("수정된 댓글");
    comments.setCommentsId(2L);
    commentsDAO.updateByCommentsId(comments);
  }
}