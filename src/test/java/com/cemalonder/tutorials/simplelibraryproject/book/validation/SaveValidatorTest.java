package com.cemalonder.tutorials.simplelibraryproject.book.validation;

import org.junit.Before;
import org.junit.Test;

import com.cemalonder.tutorials.simplelibraryproject.book.exception.DuplicateBookException;
import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;

public class SaveValidatorTest {

  private SaveValidator saveValidator;

  @Before
  public void setUp() {
    saveValidator = new SaveValidator();
  }

  @Test(expected = DuplicateBookException.class)
  public void validate_notNullFoundBook_shouldThrowException() {
    saveValidator.validate(new Book());
  }
}
