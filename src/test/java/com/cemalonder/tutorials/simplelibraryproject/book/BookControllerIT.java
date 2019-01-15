package com.cemalonder.tutorials.simplelibraryproject.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cemalonder.tutorials.simplelibraryproject.book.model.BookDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-it.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIT {

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private BookService bookService;

  @Test
  public void deleteBy_notFoundId_shouldReturnNotFound() {
    ResponseEntity<BookDto> response =
        restTemplate.exchange("/api/books/1", HttpMethod.DELETE, null, BookDto.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void deleteBy_foundId_shouldDeleteBook() {
    BookDto bookDto = BookDto.builder().authorName("authorName").name("name").build();
    BookDto savedBook = bookService.insertBook(bookDto);
    restTemplate.delete("/api/books/" + savedBook.getBookId());

    Optional<BookDto> deletedBook = bookService.findBookById(savedBook.getBookId());
    assertFalse(deletedBook.isPresent());
  }

  @Test
  public void findBookBy_notFoundId_shouldReturnNotFound() {
    ResponseEntity<BookDto> response = restTemplate.getForEntity("/api/books/1", BookDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void findBookBy_foundId_shouldReturnFoundBook() {
    BookDto bookDto = BookDto.builder().authorName("authorName").name("name").build();
    BookDto savedBook = bookService.insertBook(bookDto);

    ResponseEntity<BookDto> response =
        restTemplate.getForEntity("/api/books/" + savedBook.getBookId(), BookDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(savedBook);
  }

  @Test
  public void findBooks_noBooksFound_shouldReturnEmptyList() {

    ResponseEntity<List> response = restTemplate.getForEntity("/api/books", List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(Collections.emptyList());
  }

  @Test
  public void findBooks_booksFound_shouldReturnBooksList() {
    BookDto bookDto1 = BookDto.builder().authorName("authorName1").name("name1").build();
    BookDto bookDto2 = BookDto.builder().authorName("authorName2").name("name2").build();

    BookDto savedBookDto1 = bookService.insertBook(bookDto1);
    BookDto savedBookDto2 = bookService.insertBook(bookDto2);
    List<BookDto> bookDtos = Arrays.asList(savedBookDto1, savedBookDto2);

    ResponseEntity<List<BookDto>> response =
        restTemplate.exchange(
            "/api/books", HttpMethod.GET, null, new ParameterizedTypeReference<List<BookDto>>() {});

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).containsAll(bookDtos);
  }

  @Test
  public void insertBook_nullBookName_shouldReturnBadRequest() {
    BookDto bookDto = BookDto.builder().authorName("authorName").build();
    ResponseEntity<BookDto> response =
        restTemplate.postForEntity("/api/books", bookDto, BookDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void insertBook_nullAuthorName_shouldReturnBadRequest() {
    BookDto bookDto = BookDto.builder().name("name").build();

    ResponseEntity<BookDto> response =
        restTemplate.postForEntity("/api/books", bookDto, BookDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void insertBook_nameAndAuthorName_shouldInsertBook() {
    BookDto bookDto = BookDto.builder().name("name").authorName("authorName").build();

    ResponseEntity<BookDto> response =
        restTemplate.postForEntity("/api/books", bookDto, BookDto.class);
    bookDto.setBookId(Objects.requireNonNull(response.getBody()).getBookId());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(bookDto);
  }

  @Test
  public void updateBook_nullBookName_shouldReturnBadRequest() {
    BookDto bookDto = BookDto.builder().authorName("authorName").build();
    ResponseEntity<BookDto> response =
        restTemplate.exchange(
            "/api/books/1", HttpMethod.PUT, new HttpEntity<>(bookDto), BookDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void updateBook_nullAuthorName_shouldReturnBadRequest() {
    BookDto bookDto = BookDto.builder().name("name").build();

    ResponseEntity<BookDto> response =
        restTemplate.exchange(
            "/api/books/1", HttpMethod.PUT, new HttpEntity<>(bookDto), BookDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void updateBook_nameAndAuthorName_shouldUpdateBook() {
    BookDto bookDto = BookDto.builder().name("name").authorName("authorName").build();
    BookDto savedBook = bookService.insertBook(bookDto);

    BookDto updateBookDto =
        BookDto.builder().name("updatedName").authorName("updatedAuthorName").build();

    ResponseEntity<BookDto> response =
        restTemplate.exchange(
            "/api/books/" + savedBook.getBookId(),
            HttpMethod.PUT,
            new HttpEntity<>(updateBookDto),
            BookDto.class);
    updateBookDto.setBookId(savedBook.getBookId());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(updateBookDto);
  }
}
