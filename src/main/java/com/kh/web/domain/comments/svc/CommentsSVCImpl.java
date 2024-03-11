package com.kh.web.domain.comments.svc;

import com.kh.web.domain.comments.dao.CommentsDAO;
import com.kh.web.domain.entity.Comments;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentsSVCImpl implements CommentsSVC{

  private final CommentsDAO commentsDAO;

  @Override
  public Long saveComments(Comments comments) {
    return commentsDAO.saveComments(comments);
  }

  @Override
  public List<Comments> findCommentsAll(Long boardId) {
    return commentsDAO.findCommentsAll(boardId);
  }

  @Override
  public int totalCnt() {
    return commentsDAO.totalCnt();
  }

  @Override
  public int deleteByCommentsId(Long commentsId) {
    return commentsDAO.deleteByCommentsId(commentsId);
  }

  @Override
  public int updateByCommentsId(Comments comments) {
    return commentsDAO.updateByCommentsId(comments);
  }
}
