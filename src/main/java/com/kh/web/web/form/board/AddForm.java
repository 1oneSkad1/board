package com.kh.web.web.form.board;

import lombok.Data;

@Data
public class AddForm {
  private String boardTitle;
  private String writer;
  private String boardContent;
}
