package com.kickcity.task.blogmanagement.common;

import com.kickcity.task.blogmanagement.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Validator {

    public void checkForNull(Object property, String propertyName) throws ValidationException {
        if (Objects.isNull(property)) {
            throw new ValidationException(propertyName + ": must not be null");
        }
    }
}
