package com.cemalonder.tutorials.simplelibraryproject.book.validation;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class BookValidatorFactory {

  public BookValidation getValidator(BookValidators bookValidator) {
    if (Objects.isNull(bookValidator)) {
      return null;
    }
    if (bookValidator.equals(BookValidators.SAVE)) {
      return new SaveValidator();
    }
    if (bookValidator.equals(BookValidators.UPDATE)) {
      return new UpdateValidator();
    }
    return null;
  }
}
