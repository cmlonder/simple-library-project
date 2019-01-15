package com.cemalonder.tutorials.simplelibraryproject.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cemalonder.tutorials.simplelibraryproject.book.exception.BookNotFoundException;
import com.cemalonder.tutorials.simplelibraryproject.book.model.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private BookService bookService;

  private JacksonTester<BookDto> jsonBook;
  private JacksonTester<List<BookDto>> jsonBooks;

  @Before
  public void setUp() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  public void deleteBy_notFoundId_shouldReturnNotFound() throws Exception {
    doThrow(BookNotFoundException.class).when(bookService).deleteBook(1L);

    MockHttpServletResponse response =
        mockMvc.perform(delete("/api/books/1")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void deleteBy_foundId_shouldDeleteBook() throws Exception {
    MockHttpServletResponse response =
        mockMvc.perform(delete("/api/books/1")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void findBookBy_nullId_shouldReturnNotFound() throws Exception {
    MockHttpServletResponse response =
        mockMvc.perform(get("/api/books/1")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void findBookBy_notFoundId_shouldReturnNotFound() throws Exception {
    given(bookService.findBookById(eq(1L))).willReturn(Optional.empty());

    MockHttpServletResponse response =
        mockMvc.perform(get("/api/books/1")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void findBookBy_foundId_shouldReturnFoundBook() throws Exception {
    BookDto bookDto = new BookDto();
    String expectedBook = jsonBook.write(bookDto).getJson();

    given(bookService.findBookById(eq(1L))).willReturn(Optional.of(bookDto));

    MockHttpServletResponse response =
        mockMvc.perform(get("/api/books/1")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(expectedBook);
  }

  @Test
  public void findBooks_noBooksFound_shouldReturnEmptyList() throws Exception {
    List<BookDto> bookDtos = Collections.emptyList();
    String expectedBooks = jsonBooks.write(bookDtos).getJson();

    given(bookService.findBooks()).willReturn(bookDtos);

    MockHttpServletResponse response = mockMvc.perform(get("/api/books")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(expectedBooks);
  }

  @Test
  public void findBooks_booksFound_shouldReturnBooksList() throws Exception {
    BookDto bookDto1 = BookDto.builder().name("name1").build();
    BookDto bookDto2 = BookDto.builder().name("name2").build();
    List<BookDto> bookDtos = Arrays.asList(bookDto1, bookDto2);
    String expectedBooks = jsonBooks.write(bookDtos).getJson();

    given(bookService.findBooks()).willReturn(bookDtos);

    MockHttpServletResponse response = mockMvc.perform(get("/api/books")).andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(expectedBooks);
  }

  @Test
  public void insertBook_nullBookDto_shouldReturnBadRequest() throws Exception {
    String requestedBook = "";

    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/api/books").content(requestedBook).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void insertBook_nullBookName_shouldReturnBadRequest() throws Exception {
    BookDto bookDto = BookDto.builder().authorName("authorName").build();
    String requestedBook = jsonBook.write(bookDto).getJson();

    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/api/books").content(requestedBook).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void insertBook_nullAuthorName_shouldReturnBadRequest() throws Exception {
    BookDto bookDto = BookDto.builder().name("name").build();
    String requestedBook = jsonBook.write(bookDto).getJson();

    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/api/books").content(requestedBook).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void insertBook_validBookDto_shouldReturnInsertedBook() throws Exception {
    BookDto bookDto = BookDto.builder().authorName("authorName").name("name").build();
    String requestedBook = jsonBook.write(bookDto).getJson();

    given(bookService.insertBook(any(BookDto.class))).willReturn(bookDto);

    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/api/books").content(requestedBook).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(requestedBook);
  }

  @Test
  public void updateBook_nullBookDto_shouldReturnBadRequest() throws Exception {
    String requestedBook = "";

    MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/api/books/1").content(requestedBook).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void updateBook_nullBookName_shouldReturnBadRequest() throws Exception {
    BookDto bookDto = BookDto.builder().authorName("authorName").build();
    String requestedBook = jsonBook.write(bookDto).getJson();

    MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/api/books/1").content(requestedBook).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void updateBook_nullAuthorName_shouldReturnBadRequest() throws Exception {
    BookDto bookDto = BookDto.builder().name("name").build();
    String requestedBook = jsonBook.write(bookDto).getJson();

    MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/api/books/1").content(requestedBook).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void updateBook_validBookDto_shouldReturnUpdatedBook() throws Exception {
    BookDto bookDto = BookDto.builder().authorName("authorName").name("name").build();
    String requestedBook = jsonBook.write(bookDto).getJson();

    given(bookService.updateBook(anyLong(), any(BookDto.class))).willReturn(bookDto);

    MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/api/books/1").content(requestedBook).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(requestedBook);
  }
}
