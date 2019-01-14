package com.cemalonder.tutorials.simplelibraryproject.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;
import com.cemalonder.tutorials.simplelibraryproject.book.model.BookDto;

public class BookMapperTest {
  private BookMapper bookMapper;

  @Before
  public void setUp() {
    bookMapper = Mappers.getMapper(BookMapper.class);
  }

  @Test
  public void map_nullBook_shouldReturnNull() {
    Book book = null;
    BookDto bookDto = bookMapper.map(book);

    assertNull(bookDto);
  }

  @Test
  public void map_validBook_shouldMap() {
    Book book = Book.builder().id(1L).name("bookName").authorName("authorName").build();

    BookDto bookDto = bookMapper.map(book);

    assertEquals(book.getId(), bookDto.getBookId());
    assertEquals(book.getName(), bookDto.getName());
    assertEquals(book.getAuthorName(), bookDto.getAuthorName());
  }

  @Test
  public void map_nullBookDto_shouldReturnNull() {
    BookDto bookDto = null;
    Book book = bookMapper.map(bookDto);

    assertNull(book);
  }

  @Test
  public void map_validBookDto_shouldMap() {
    BookDto bookDto =
        BookDto.builder().bookId(1L).name("bookName").authorName("authorName").build();

    Book book = bookMapper.map(bookDto);

    assertNotEquals(bookDto.getBookId(), book.getId());
    assertEquals(bookDto.getName(), book.getName());
    assertEquals(bookDto.getAuthorName(), book.getAuthorName());
  }

  @Test
  public void map_nullBookDto_shouldNotUpdateBook() {
    BookDto bookDto = null;
    Book book = Book.builder().authorName("authorName").build();
    bookMapper.assignToBook(bookDto, book);
    assertEquals("authorName", book.getAuthorName());
  }

  @Test
  public void map_validBookDto_shouldUpdateBook() {
    BookDto bookDto = BookDto.builder().authorName("newAuthorName").build();
    Book book = Book.builder().authorName("authorName").build();
    bookMapper.assignToBook(bookDto, book);
    assertEquals("newAuthorName", book.getAuthorName());
  }
}
