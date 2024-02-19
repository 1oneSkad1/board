package com.kh.web.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {
  private Long boardId;
  private String boardTitle;
  private String boardContent;
  private String writer;
  private LocalDateTime cdate;
  private LocalDateTime udate;
}
