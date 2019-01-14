package com.cemalonder.tutorials.simplelibraryproject.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;

@DataJpaTest
@RunWith(SpringRunner.class)
public class BookRepositoryIT {

  @Autowired private TestEntityManager testEntityManager;

  @Autowired private BookRepository bookRepository;

  @Test
  public void findByNameAndAuthorNameAndAuthorName_nullNameAndAuthorName_shouldReturnNull() {
    Optional<Book> found = bookRepository.findByNameAndAuthorName(null, null);

    assertFalse(found.isPresent());
  }

  @Test
  public void findByNameAndAuthorNameAndAuthorName_notFoundName_shouldReturnEmptyOptional() {
    Book book = Book.builder().name("bookName").authorName("authorName").build();
    testEntityManager.persist(book);
    testEntityManager.flush();

    Optional<Book> found =
        bookRepository.findByNameAndAuthorName("invalidBookName", book.getAuthorName());

    assertNull(found.orElse(null));
  }

  @Test
  public void findByNameAndAuthorNameAndAuthorName_notFoundAuthorName_shouldReturnEmptyOptional() {
    Book book = Book.builder().name("bookName").authorName("authorName").build();
    testEntityManager.persist(book);
    testEntityManager.flush();

    Optional<Book> found =
        bookRepository.findByNameAndAuthorName(book.getName(), "invalidAuthorName");

    assertNull(found.orElse(null));
  }

  @Test
  public void findByNameAndAuthorNameAndAuthorName_foundName_shouldReturnOptionalWithFoundBook() {
    Book book = Book.builder().name("bookName").authorName("authorName").build();
    testEntityManager.persist(book);
    testEntityManager.flush();

    Optional<Book> found =
        bookRepository.findByNameAndAuthorName(book.getName(), book.getAuthorName());

    assertEquals(book, found.orElse(null));
  }
}
