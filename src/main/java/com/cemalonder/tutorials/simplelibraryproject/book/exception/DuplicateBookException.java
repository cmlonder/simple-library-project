package com.cemalonder.tutorials.simplelibraryproject.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Book already exist")
public class DuplicateBookException extends RuntimeException {}
