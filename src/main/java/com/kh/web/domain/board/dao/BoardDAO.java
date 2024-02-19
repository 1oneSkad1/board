package com.kh.web.domain.board.dao;

import com.kh.web.domain.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardDAO {
  //게시글 등록
  Long save(Board board);

  //게시글 조회
  Optional<Board> findById(Long boardId);

  //게시글 삭제
  int deleteById(Long boardId);

  //게시글 수정
  int updateById(Long boardId, Board board);

  //게시글 목록
  List<Board> findAll();
}
