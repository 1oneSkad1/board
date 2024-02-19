package com.kh.web.web;

import com.kh.web.domain.board.svc.BoardSVC;
import com.kh.web.domain.entity.Board;
import com.kh.web.web.form.board.AddForm;
import com.kh.web.web.form.board.UpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/boards")          // http://localhost:9080/boards
public class BoardController {

  private BoardSVC boardSVC;
  BoardController(BoardSVC boardSVC){
    this.boardSVC = boardSVC;
  }

  //게시판 작성 map
  @GetMapping("/add")               // GET http://localhost:9080/boards/add
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "board/add";             // 게시글 작성 화면
  }

  //게시글 등록처리
  @PostMapping("/add")        // Post, http://localhost:9080/boards/add
  public String add(
      AddForm addForm,
      Model model,
      RedirectAttributes redirectAttributes
  ){
    //데이터 유효성 체크
    //제목
    String pattern = "^[a-zA-Zㄱ-ㅣ가-힣0-9 ,._-]{2,30}$";
    if (!Pattern.matches(pattern, addForm.getBoardTitle())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_board", "영문/숫자/한글/_-, 2~30자리만 입력가능");
      return "board/add";
    }
    //작성자
    pattern = "^[가-힣]{2,5}$";
    if (!Pattern.matches(pattern, addForm.getWriter())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_writer", "한글2~5자리만 입력가능");
      return "board/add";
    }
    //내용
    pattern = "^[a-zA-Zㄱ-ㅣ가-힣0-9 \\s,./~!@#$%^&*()=+_-]{1,500}$";
    if (!Pattern.matches(pattern, addForm.getBoardContent())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_content", "공백 입력 불가 / 최대 500자까지 입력가능");
      return "board/add";
    }

    log.info("addForm={}", addForm);
    //상품등록
    Board board = new Board();
    board.setWriter(addForm.getWriter());
    board.setBoardTitle(addForm.getBoardTitle());
    board.setBoardContent(addForm.getBoardContent());

    Long boardId = boardSVC.save(board);
    log.info("상품번호={}", boardId);

    redirectAttributes.addAttribute("bid",boardId);
    return "redirect:/boards/{bid}/detail"; // 상품조회화면 302 GET http://서버:9080/board/게시글번호/detail
  }

  //게시글 조회
  @GetMapping("/{bid}/detail")       //GET http://localhost:9080/boards/게시글번호/detail
  public String findById(@PathVariable("bid") Long boardId, Model model){

    Optional<Board> findedBoard = boardSVC.findById(boardId);
    Board board = findedBoard.orElseThrow();
    model.addAttribute("board", board);

    return "board/detailForm";
  }
  
  //게시글 수정
  @GetMapping("/{bid}/edit")          //GET http://localhost:9080/boards/게시글번호/edit
  public String updateFrom(
      @PathVariable("bid") Long boardId,
      Model model) {

    Optional<Board> optionalBoard = boardSVC.findById(boardId);
    Board findedBoard = optionalBoard.orElseThrow();

    model.addAttribute("board", findedBoard);
    return "board/updateForm";
  }

  //수정처리
  @PostMapping("/{bid}/edit")
  public String update(
      //경로변수 bid로부터 게시글 번호를 읽어온다
      @PathVariable("bid") Long boardId,
      //요청메세지 바디로부터 대응되는 게시글 정보를 읽어온다.
      UpdateForm updateForm,
      //리다이렉트시 경로변수에 값을 성정하기위해 사용
      RedirectAttributes redirectAttributes,
      Model model) {

    //데이터 유효성 체크
    //제목
    String pattern = "^[a-zA-Zㄱ-ㅣ가-힣0-9 ,._-]{2,30}$";
    if (!Pattern.matches(pattern, updateForm.getBoardTitle())) {
      model.addAttribute("board", updateForm);
      model.addAttribute("s_err_board", "영문/숫자/한글/_-가능, 2~30자리");
      return "board/updateForm";
    }
    //내용
    pattern = "^[a-zA-Zㄱ-ㅣ가-힣0-9 \\s,./~!@#$%^&*()=+_-]{1,500}$";
    if (!Pattern.matches(pattern, updateForm.getBoardContent())) {
      model.addAttribute("board", updateForm);
      model.addAttribute("s_err_content", "공백 입력 불가 / 최대 500자까지 입력가능");
      return "board/updateForm";
    }


    Board board = new Board();
    board.setBoardTitle(updateForm.getBoardTitle());
    board.setBoardContent(updateForm.getBoardContent());
    int updateRowCnt = boardSVC.updateById(boardId, board);


    redirectAttributes.addAttribute("bid", boardId);
    return "redirect:/boards/{bid}/detail";
  }
  
  //게시글 삭제
  @GetMapping("/{bid}/del")                             // GET http://localhost:9080/boards/게시글번호/del
  public String delete(@PathVariable("bid") Long boardId) {

    boardSVC.deleteById(boardId);

    return "redirect:/boards";      //GET http://localhost:9080/boards
  }

  //게시글 목록
  @GetMapping   // GET http://localhost:9080/boards
  public String findAll(Model model){

    List<Board> list = boardSVC.findAll();
    model.addAttribute("list", list);

    return "board/all";
  }

}
