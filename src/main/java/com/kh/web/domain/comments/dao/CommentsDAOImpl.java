package com.kh.web.domain.comments.dao;

import com.kh.web.domain.entity.Comments;
import lombok.RequiredArgsConstructor;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentsDAOImpl implements CommentsDAO {

  private final NamedParameterJdbcTemplate template;

  //댓글 작성
  @Override
  public Long saveComments(Comments comments) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into comments (comments_id, board_id, writer, member_id, comments_content) ");
    sql.append("values (comments_comments_id_seq.nextval, :boardId, :writer, :memberId, :commentsContent) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(comments);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyHolder, new String[]{"comments_id", "board_id"});
    Long comments_id = ((BigDecimal) keyHolder.getKeys().get("comments_id")).longValue();
    return comments_id;
  }

  //댓글 삭제
  @Override
  public int deleteByCommentsId(Long commentsId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from comments ");
    sql.append(" where comments_id = :commentsId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("commentsId", commentsId);

    int deletedCnt = template.update(sql.toString(), param);

    return deletedCnt;
  }

  //댓글 수정
  @Override
  public int updateByCommentsId(Comments comments) {
    StringBuffer sql = new StringBuffer();
    sql.append("update comments ");
    sql.append("   set comments_content = :commentsContent, ");
    sql.append("       udate = default ");
    sql.append(" where comments_id = :commentsId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("commentsContent", comments.getCommentsContent())
        .addValue("commentsId", comments.getCommentsId());

    int updateRowCnt = template.update(sql.toString(), param);

    return updateRowCnt;
  }

  //댓글 목록
  @Override
  public List<Comments> findCommentsAll(Long boardId) {
    StringBuffer sql = new StringBuffer();
    sql.append("  select board_id, comments_id, comments_content, writer, member_id, cdate, udate ");
    sql.append("    from comments ");
    sql.append("   where board_id = :boardId ");
    sql.append("order by comments_id desc ");

    Map<String, Object> param = new HashMap<>();
    param.put("boardId", boardId);

    List<Comments> list = template.query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Comments.class));
    return list;
  }

  @Override
  public List<Comments> findAll(Long reqPage, Long recCnt) {
    StringBuffer sql = new StringBuffer();
    sql.append("  select board_id, comments_id,comments_content,writer,member_id,cdate,udate ");
    sql.append("    from comments ");
    sql.append("order by comments_id asc ");
    sql.append("offset (:reqPage - 1) * :recCnt rows fetch first :recCnt rows only ");

    Map<String, Long> param = Map.of("reqPage", reqPage, "recCnt", recCnt);
    List<Comments> list = template.query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Comments.class));

    return list;
  }

  //총 댓글 수
  @Override
  public int totalCnt() {
    String sql = "SELECT COUNT(comments_id) FROM comments ";

    SqlParameterSource param = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt;
  }
}
