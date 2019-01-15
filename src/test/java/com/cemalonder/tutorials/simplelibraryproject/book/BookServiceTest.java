package com.cemalonder.tutorials.simplelibraryproject.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import com.cemalonder.tutorials.simplelibraryproject.book.exception.BookNotFoundException;
import com.cemalonder.tutorials.simplelibraryproject.book.exception.DuplicateBookException;
import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;
import com.cemalonder.tutorials.simplelibraryproject.book.model.BookDto;
import com.cemalonder.tutorials.simplelibraryproject.book.validation.BookValidators;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

  @Mock private BookMapper bookMapper;

  @Mock private BookRepository bookRepository;

  @Mock private BookValidationService bookValidationService;

  @InjectMocks private BookService bookService;

  @Test(expected = BookNotFoundException.class)
  public void deleteBy_notFoundId_shouldThrowException() {
    doThrow(EmptyResultDataAccessException.class).when(bookRepository).deleteById(1L);

    bookService.deleteBook(1L);
  }

  @Test
  public void deleteBy_foundId_shouldDeleteBook() {
    bookService.deleteBook(1L);

    verify(bookRepository, times(1)).deleteById(eq(1L));
  }

  @Test
  public void findBookBy_nullBookId_shouldReturnEmptyOptional() {
    given(bookRepository.findById(any())).willReturn(Optional.of(new Book()));
    given(bookMapper.map(any(Book.class))).willReturn(null);

    Optional<BookDto> found = bookService.findBookById(null);

    assertFalse(found.isPresent());
  }

  @Test
  public void findBookBy_validBookId_shouldReturnOptionalWithFoundBook() {
    BookDto bookDto = new BookDto();
    given(bookRepository.findById(1L)).willReturn(Optional.of(new Book()));
    given(bookMapper.map(any(Book.class))).willReturn(bookDto);

    Optional<BookDto> found = bookService.findBookById(1L);

    assertEquals(bookDto, found.orElse(null));
  }

  @Test
  public void findBooks_noBooksFound_shouldReturnEmptyList() {
    given(bookRepository.findAll()).willReturn(Collections.emptyList());
    given(bookMapper.map(anyList())).willReturn(Collections.emptyList());

    List<BookDto> bookDtos = bookService.findBooks();
    assertEquals(0, bookDtos.size());
  }

  @Test
  public void findBooks_booksFound_shouldReturnBooksList() {
    BookDto bookDto1 = BookDto.builder().authorName("authorName1").name("name1").bookId(1L).build();
    BookDto bookDto2 = BookDto.builder().authorName("authorName2").name("name2").bookId(2L).build();
    List<BookDto> expected = Arrays.asList(bookDto1, bookDto2);

    given(bookRepository.findAll()).willReturn(Collections.emptyList());
    given(bookMapper.map(anyList())).willReturn(expected);

    List<BookDto> bookDtos = bookService.findBooks();
    assertEquals(2, bookDtos.size());
    assertEquals(expected.get(0), bookDtos.get(0));
    assertEquals(expected.get(1), bookDtos.get(1));
  }

  @Test(expected = NullPointerException.class)
  public void insertBook_nullBookDto_shouldThrowException() {
    bookService.insertBook(null);
  }

  @Test(expected = DuplicateBookException.class)
  public void insertBook_existingNameAndAuthorName_shouldThrowException() {
    given(bookRepository.findByNameAndAuthorName(anyString(), anyString()))
        .willReturn(Optional.of(new Book()));
    doThrow(DuplicateBookException.class)
        .when(bookValidationService)
        .validate(any(BookValidators.class), any(Book.class));
    BookDto bookDto = BookDto.builder().authorName("authorName").name("name").build();
    bookService.insertBook(bookDto);
  }

  @Test
  public void insertBook_validBook_shouldReturnInsertedBook() {
    BookDto bookDto = BookDto.builder().authorName("authorName").name("name").build();
    BookDto savedBookDto =
        BookDto.builder().authorName("authorName").name("name").bookId(1L).build();

    given(bookRepository.findByNameAndAuthorName(anyString(), anyString()))
        .willReturn(Optional.empty());
    given(bookMapper.map(bookDto)).willReturn(new Book());
    given(bookRepository.save(any(Book.class))).willReturn(new Book());
    given(bookMapper.map(any(Book.class))).willReturn(savedBookDto);

    BookDto expected = bookService.insertBook(bookDto);
    assertEquals(expected, savedBookDto);
  }

  @Test(expected = NullPointerException.class)
  public void updateBook_nullBookDto_shouldThrowException() {
    bookService.updateBook(1L, null);
  }

  @Test(expected = BookNotFoundException.class)
  public void updateBook_notFoundBook_shouldThrowException() {
    given(bookRepository.findById(anyLong())).willReturn(Optional.empty());
    doThrow(BookNotFoundException.class)
        .when(bookValidationService)
        .validate(any(BookValidators.class), eq(null));
    BookDto bookDto = BookDto.builder().authorName("authorName").name("name").build();
    bookService.updateBook(1L, bookDto);
  }

  @Test
  public void updateBook_validBook_shouldReturnUpdatedBook() {
    Book foundBook = Book.builder().authorName("authorName").name("name").build();
    BookDto updatedBookDto =
        BookDto.builder().authorName("authorName").name("name").bookId(1L).build();

    given(bookRepository.findById(anyLong())).willReturn(Optional.of(foundBook));
    given(bookRepository.save(any(Book.class))).willReturn(new Book());
    given(bookMapper.map(any(Book.class))).willReturn(updatedBookDto);

    BookDto expected = bookService.updateBook(1L, new BookDto());
    assertEquals(updatedBookDto, expected);
  }
}
