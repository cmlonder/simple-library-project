package com.cemalonder.tutorials.simplelibraryproject.book;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;
import com.cemalonder.tutorials.simplelibraryproject.book.validation.BookValidation;
import com.cemalonder.tutorials.simplelibraryproject.book.validation.BookValidatorFactory;
import com.cemalonder.tutorials.simplelibraryproject.book.validation.BookValidators;

@Service
public class BookValidationService {

  private BookValidatorFactory bookValidatorFactory;

  public BookValidationService(BookValidatorFactory bookValidatorFactory) {
    Objects.requireNonNull(bookValidatorFactory, "bookValidatorFactory can not be null");
    this.bookValidatorFactory = bookValidatorFactory;
  }

  void validate(BookValidators bookValidators, Book foundBook) {
    BookValidation bookValidation = bookValidatorFactory.getValidator(bookValidators);
    bookValidation.validate(foundBook);
  }
}
