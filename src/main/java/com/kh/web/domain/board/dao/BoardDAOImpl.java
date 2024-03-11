package com.kh.web.domain.board.dao;

import com.kh.web.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class BoardDAOImpl implements BoardDAO{

  private final NamedParameterJdbcTemplate template;

  BoardDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  //게시글 추가
  @Override
  public Long save(Board board) {

    StringBuffer sql = new StringBuffer();
    sql.append("insert into board (board_id, board_title, writer, board_content) ");
    sql.append("values (board_board_id_seq.nextval, :boardTitle, :writer, :boardContent) ");

    // SQL파라미터 자동매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(board);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"board_id","board_title","writer","board_content"});
    Long board_id = ((BigDecimal)keyHolder.getKeys().get("board_id")).longValue(); //게시판 아이디
    return board_id;
  }

  //게시글 조회

  @Override
  public Optional<Board> findById(Long boardId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select board_title, writer, board_content, cdate, udate");
    sql.append("  from board ");
    sql.append(" where board_id = :boardId ");

    Map<String,Long> map = Map.of("boardId",boardId);
    Board board = template.queryForObject(sql.toString(), map, BeanPropertyRowMapper.newInstance(Board.class));
    return Optional.of(board);
  }

  //게시글 수정

  @Override
  public int updateById(Long boardId, Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("update board ");
    sql.append("   set board_title = :boardTitle, ");
    sql.append("       board_content = :boardContent, ");
    sql.append("       udate = default ");
    sql.append(" where board_id = :boardId ");

    //sql 파라미터 변수에 값 매핑
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("boardTitle", board.getBoardTitle())
        .addValue("boardContent", board.getBoardContent())
        .addValue("boardId", boardId);

    //update 수행 후 변경된 행수 반환
    int updateRowCnt = template.update(sql.toString(), param);

    return updateRowCnt;
  }


  //게시글 삭제

  @Override
  public int deleteById(Long boardId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from board ");
    sql.append(" where board_id = :boardId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("boardId", boardId);

    int deletedRowCnt = template.update(sql.toString(),param);

    return deletedRowCnt;
  }


  //게시글 목록

  @Override
  public List<Board> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("  select board_id, board_title, writer, udate ");
    sql.append("    from board ");
    sql.append("order by board_id desc ");

    List<Board> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Board.class));

    return list;
  }

}
