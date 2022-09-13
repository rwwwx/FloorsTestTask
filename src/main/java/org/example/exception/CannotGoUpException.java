package org.example.exception;

public class CannotGoUpException extends RuntimeException {

  @Override
  public String getMessage() {
    return "it cannot go up";
  }

  @Override
  public String toString() {
    return "CannotGoUpException";
  }

}
