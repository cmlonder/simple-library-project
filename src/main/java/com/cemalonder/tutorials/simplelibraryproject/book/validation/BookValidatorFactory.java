package com.cemalonder.tutorials.simplelibraryproject.book.validation;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class BookValidatorFactory {

  public BookValidation getValidator(BookValidators bookValidatior) {
    if (Objects.isNull(bookValidatior)) {
      return null;
    }
    if (bookValidatior.equals(BookValidators.SAVE)) {
      return new SaveValidator();
    }
    if (bookValidatior.equals(BookValidators.UPDATE)) {
      return new UpdateValidator();
    }
    return null;
  }
}
