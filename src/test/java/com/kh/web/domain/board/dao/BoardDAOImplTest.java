package com.kh.web.domain.board.dao;

import com.kh.web.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class BoardDAOImplTest {

  @Autowired
  BoardDAO boardDAO;

  @Test
  @DisplayName("게시글 등록")
  void save() {

    Board board = new Board();
    board.setBoardTitle("제목");
    board.setWriter("홍길서");
    board.setBoardContent("내용2");

    Long boardId = boardDAO.save(board);
    log.info("boardId={}", boardId);
  }


  @Test
  @DisplayName("게시글 목록")
  void findAll() {
    List<Board> list = boardDAO.findAll();
    for (Board board : list) {
      log.info("board={}", board);
    }
    log.info("size={}", list.size());
  }

  @Test
  @DisplayName("게시글 조회")
  void findById() {
    Long boardId = 3L;
    Optional<Board> findedBoard = boardDAO.findById(boardId);
    Board board = findedBoard.orElse(null);
    log.info("board={}", board);

  }

  @Test
  @DisplayName("게시글 삭제")
  void deleteById() {
    Long bid = 6L;
    int deleteRowCnt = boardDAO.deleteById(bid);

    Assertions.assertThat(deleteRowCnt).isEqualTo(1);
  }

  @Test
  @DisplayName("게시글 수정")
  void updateById() {
    Long boardId = 4L;
    Board board = new Board();
    board.setBoardTitle("해치웠나?");
    board.setBoardContent("응 맞아");
    int updatedRowCnt = boardDAO.updateById(boardId, board);
    if (updatedRowCnt == 0) {
      Assertions.fail("변경 내역이 없습니다.");
    }
    Optional<Board> optionalBoard = boardDAO.findById(boardId);
    if (optionalBoard.isPresent()) {
      Board findedBoard = optionalBoard.get();
      Assertions.assertThat(findedBoard.getBoardTitle()).isEqualTo("해치웠나?");
      Assertions.assertThat(findedBoard.getBoardContent()).isEqualTo("응 맞아");
    } else {
      Assertions.fail("변경할 상품이 없습니다");
    }
  }
}