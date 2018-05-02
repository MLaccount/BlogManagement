package com.kickcity.task.blogmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NoContentFoundException extends RuntimeException {

    public NoContentFoundException() {
        super();
    }

    public NoContentFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoContentFoundException(String message) {
        super(message);
    }

    public NoContentFoundException(Throwable cause) {
        super(cause);
    }
}