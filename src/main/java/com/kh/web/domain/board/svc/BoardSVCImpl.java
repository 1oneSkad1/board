package com.kh.web.domain.board.svc;

import com.kh.web.domain.board.dao.BoardDAO;
import com.kh.web.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoardSVCImpl implements BoardSVC{

  private BoardDAO boardDAO;

  BoardSVCImpl(BoardDAO boardDAO) {
    this.boardDAO = boardDAO;
  }

  @Override
  public Long save(Board board) {
    return boardDAO.save(board);
  }

  @Override
  public Optional<Board> findById(Long boardId) {
    return boardDAO.findById(boardId);
  }

  @Override
  public int deleteById(Long boardId) {
    return boardDAO.deleteById(boardId);
  }

  @Override
  public int updateById(Long boardId, Board board) {
    return boardDAO.updateById(boardId, board);
  }

  @Override
  public List<Board> findAll() {
    return boardDAO.findAll();
  }

}
