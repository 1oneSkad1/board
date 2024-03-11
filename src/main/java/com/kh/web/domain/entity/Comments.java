package com.kh.web.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comments {
  private Long boardId;
  private Long commentsId;
  private String commentsContent;
  private String writer;
  private Long memberId;
  private LocalDateTime cdate;
  private LocalDateTime udate;
}
