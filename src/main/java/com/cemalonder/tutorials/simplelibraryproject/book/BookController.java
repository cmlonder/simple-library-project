package com.cemalonder.tutorials.simplelibraryproject.book;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cemalonder.tutorials.simplelibraryproject.book.exception.BookNotFoundException;
import com.cemalonder.tutorials.simplelibraryproject.book.model.BookDto;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
class BookController {

  private BookService bookService;

  public BookController(BookService bookService) {
    Objects.requireNonNull(bookService, "bookService can not be null");
    this.bookService = bookService;
  }

  @DeleteMapping("/{bookId}")
  public void deleteBy(@PathVariable Long bookId) {
    bookService.deleteBook(bookId);
  }

  @GetMapping("/{bookId}")
  public BookDto findBookById(@PathVariable Long bookId) {
    Optional<BookDto> optionalBook = bookService.findBookById(bookId);
    return optionalBook.orElseThrow(BookNotFoundException::new);
  }

  @GetMapping
  public List<BookDto> findBooks() {
    return bookService.findBooks();
  }

  @PostMapping
  public BookDto insertBook(@Valid @RequestBody BookDto bookDto) {
    return bookService.insertBook(bookDto);
  }

  @PutMapping("/{bookId}")
  public BookDto updateBook(@Valid @RequestBody BookDto bookDto, @PathVariable Long bookId) {
    return bookService.updateBook(bookId, bookDto);
  }
}
