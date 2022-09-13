package org.example.exception;

public class CannotGoDownException extends RuntimeException {

  @Override
  public String getMessage() {
    return "it cannot go down";
  }

  @Override
  public String toString() {
    return "CannotGoDownException";
  }

}
