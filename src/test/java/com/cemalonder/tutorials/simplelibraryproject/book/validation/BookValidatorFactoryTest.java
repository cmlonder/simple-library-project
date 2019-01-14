package com.cemalonder.tutorials.simplelibraryproject.book.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class BookValidatorFactoryTest {

  private BookValidatorFactory bookValidatorFactory;

  @Before
  public void setUp() {
    bookValidatorFactory = new BookValidatorFactory();
  }

  @Test
  public void getValidator_nullBookValidator_shouldReturnNull() {
    BookValidation bookValidation = bookValidatorFactory.getValidator(null);
    assertNull(bookValidation);
  }

  @Test
  public void getValidator_saveBookValidator_shouldReturnSaveValidator() {
    BookValidation bookValidation = bookValidatorFactory.getValidator(BookValidators.SAVE);
    assertEquals(SaveValidator.class, bookValidation.getClass());
  }

  @Test
  public void getValidator_updateBookValidator_shouldReturnUpdateValidator() {
    BookValidation bookValidation = bookValidatorFactory.getValidator(BookValidators.UPDATE);
    assertEquals(UpdateValidator.class, bookValidation.getClass());
  }

}
