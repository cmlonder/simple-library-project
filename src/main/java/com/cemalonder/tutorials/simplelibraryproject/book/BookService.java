package com.cemalonder.tutorials.simplelibraryproject.book;

import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cemalonder.tutorials.simplelibraryproject.book.exception.BookNotFoundException;
import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;
import com.cemalonder.tutorials.simplelibraryproject.book.model.BookDto;
import com.cemalonder.tutorials.simplelibraryproject.book.validation.BookValidators;

@Service
@Transactional
public class BookService {

  private BookMapper bookMapper;
  private BookRepository bookRepository;
  private BookValidationService bookValidationService;

  public BookService(
      BookMapper bookMapper,
      BookRepository bookRepository,
      BookValidationService bookValidationService) {
    Objects.requireNonNull(bookMapper, "bookMapper can not be null");
    Objects.requireNonNull(bookRepository, "bookRepository can not be null");
    Objects.requireNonNull(bookValidationService, "bookValidationService can not be null");

    this.bookMapper = bookMapper;
    this.bookRepository = bookRepository;
    this.bookValidationService = bookValidationService;
  }

  public void deleteBook(Long bookId) {
    try {
      bookRepository.deleteById(bookId);
    } catch (EmptyResultDataAccessException e) {
      throw new BookNotFoundException();
    }
  }

  public Optional<BookDto> findBookById(Long bookId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);
    return optionalBook.map(bookMapper::map);
  }

  public BookDto insertBook(BookDto bookDto) {
    Objects.requireNonNull(bookDto, "bookDto can not be null");

    Optional<Book> foundBook =
        bookRepository.findByNameAndAuthorName(bookDto.getName(), bookDto.getAuthorName());
    bookValidationService.validate(BookValidators.SAVE, foundBook.orElse(null));
    Book book = bookMapper.map(bookDto);
    Book savedBook = bookRepository.save(book);

    return bookMapper.map(savedBook);
  }

  public BookDto updateBook(Long bookId, BookDto bookDto) {
    Objects.requireNonNull(bookDto, "bookDto can not be null");

    Optional<Book> updateBookOptional = bookRepository.findById(bookId);
    Book updateBookRequest = updateBookOptional.orElse(null);
    bookValidationService.validate(BookValidators.UPDATE, updateBookRequest);
    bookMapper.assignToBook(bookDto, updateBookRequest);
    Book updatedBook = bookRepository.save(updateBookRequest);

    return bookMapper.map(updatedBook);
  }
}
