package com.kh.web.web.form.board;

import lombok.Data;

@Data
public class UpdateForm {
  private Long boardId;
  private String boardTitle;
  private String writer;
  private String boardContent;
}
