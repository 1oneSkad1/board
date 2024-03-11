package com.kh.web.web.api;

import lombok.Getter;

@Getter
public enum ResCode {
  OK("00"),NOK("01"),
  EXIST("21"),NONE("22"),
  ETC("99");

  private final String code;

  ResCode(String code) {
    this.code = code;
  }
}
