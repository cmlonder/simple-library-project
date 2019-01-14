package com.cemalonder.tutorials.simplelibraryproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.INTERNAL_SERVER_ERROR,
    reason = "Unexpected server error occured")
public class InternalServerErrorException extends RuntimeException {}
