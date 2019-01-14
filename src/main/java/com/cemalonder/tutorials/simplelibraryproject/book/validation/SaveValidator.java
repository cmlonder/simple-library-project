package com.cemalonder.tutorials.simplelibraryproject.book.validation;

import java.util.Objects;

import com.cemalonder.tutorials.simplelibraryproject.book.exception.DuplicateBookException;
import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;

public class SaveValidator implements BookValidation {

  @Override
  public void validate(Book foundBook) {
    if (!Objects.isNull(foundBook)) {
      throw new DuplicateBookException();
    }
  }
}
