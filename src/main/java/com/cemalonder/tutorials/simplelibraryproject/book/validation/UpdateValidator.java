package com.cemalonder.tutorials.simplelibraryproject.book.validation;

import java.util.Objects;

import com.cemalonder.tutorials.simplelibraryproject.book.exception.BookNotFoundException;
import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;

public class UpdateValidator implements BookValidation {
  @Override
  public void validate(Book foundBook) {
    if (Objects.isNull(foundBook)) {
      throw new BookNotFoundException();
    }
  }
}
