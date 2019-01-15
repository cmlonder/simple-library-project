package com.cemalonder.tutorials.simplelibraryproject.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

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

  @Test
  public void map_nullBooks_shouldReturnEmptyBookDtos() {
    List<Book> books = null;
    List<BookDto> bookDtos = bookMapper.map(books);
    assertEquals(0, bookDtos.size());
  }

  @Test
  public void map_validBooks_shouldReturnBookDtos() {
    Book book1 = Book.builder().authorName("authorName1").name("name1").id(1L).build();
    Book book2 = Book.builder().authorName("authorName2").name("name2").id(2L).build();
    List<Book> books = Arrays.asList(book1, book2);

    BookDto bookDto1 = BookDto.builder().authorName("authorName1").name("name1").bookId(1L).build();
    BookDto bookDto2 = BookDto.builder().authorName("authorName2").name("name2").bookId(2L).build();
    List<BookDto> expected = Arrays.asList(bookDto1, bookDto2);

    List<BookDto> bookDtos = bookMapper.map(books);
    assertEquals(books.get(0).getAuthorName(), bookDtos.get(0).getAuthorName());
    assertEquals(books.get(0).getName(), bookDtos.get(0).getName());
    assertEquals(books.get(0).getId(), bookDtos.get(0).getBookId());
    assertEquals(books.get(1).getAuthorName(), bookDtos.get(1).getAuthorName());
    assertEquals(books.get(1).getName(), bookDtos.get(1).getName());
    assertEquals(books.get(1).getId(), bookDtos.get(1).getBookId());
  }
}
