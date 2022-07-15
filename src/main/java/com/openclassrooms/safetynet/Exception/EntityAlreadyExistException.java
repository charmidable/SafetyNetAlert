package com.openclassrooms.safetynet.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class EntityAlreadyExistException extends RuntimeException
{
    public EntityAlreadyExistException(String message)
    {
        super(message);
    }
}