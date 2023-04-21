package com.twoleader.backend.global.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class ResultResponse<T> extends RepresentationModel<ResultResponse<T>> {

  private final String message;
  private Object data;

  @JsonCreator
  public ResultResponse(ResultCode resultCode) {
    this.message = resultCode.getMessage();
  }

  @JsonCreator
  public ResultResponse(ResultCode resultCode, Object data) {
    this.message = resultCode.getMessage();
    this.data = data;
  }
}
