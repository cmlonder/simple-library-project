package com.cemalonder.tutorials.simplelibraryproject.book.validation;

import org.junit.Before;
import org.junit.Test;

import com.cemalonder.tutorials.simplelibraryproject.book.exception.BookNotFoundException;

public class UpdateValidatorTest {
  private UpdateValidator updateValidator;

  @Before
  public void setUp() {
    updateValidator = new UpdateValidator();
  }

  @Test(expected = BookNotFoundException.class)
  public void validate_nullFoundBook_shouldThrowException() {
    updateValidator.validate(null);
  }
}
