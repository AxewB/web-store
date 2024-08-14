package ru.aksenov.onlineshop.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
  private String message;

  @JsonIgnore
  private Boolean error = false;

  public MessageResponse(String message) {
    this.message = message;
  }

  public MessageResponse(String message, Boolean error) {
    this.message = message;
    this.error = error;
  }
}
