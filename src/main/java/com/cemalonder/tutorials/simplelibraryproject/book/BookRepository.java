package com.cemalonder.tutorials.simplelibraryproject.book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  Optional<Book> findByNameAndAuthorName(String name, String authorName);
}
