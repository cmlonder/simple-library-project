package com.cemalonder.tutorials.simplelibraryproject.book;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;
import com.cemalonder.tutorials.simplelibraryproject.book.validation.BookValidatorFactory;
import com.cemalonder.tutorials.simplelibraryproject.book.validation.BookValidators;
import com.cemalonder.tutorials.simplelibraryproject.book.validation.SaveValidator;

@RunWith(MockitoJUnitRunner.class)
public class BookValidationServiceTest {

  @Mock private BookValidatorFactory bookValidatorFactory;

  @InjectMocks private BookValidationService bookValidationService;

  @Test
  public void validate_nullBookValidation_shouldNotCallValidate() {
    SaveValidator mockSaveValidator = mock(SaveValidator.class);
    given(bookValidatorFactory.getValidator(any(BookValidators.class))).willReturn(null);
    bookValidationService.validate(BookValidators.SAVE, new Book());
    verify(mockSaveValidator, never()).validate(any(Book.class));
  }

  @Test
  public void validate_bookValidation_shouldCallValidate() {
    SaveValidator mockSaveValidator = mock(SaveValidator.class);
    given(bookValidatorFactory.getValidator(any(BookValidators.class)))
        .willReturn(mockSaveValidator);
    bookValidationService.validate(BookValidators.SAVE, new Book());
    verify(mockSaveValidator, times(1)).validate(any(Book.class));
  }
}
