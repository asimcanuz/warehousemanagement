package org.asodev.monolithic.warehousemanagement.exception;

import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.asodev.monolithic.warehousemanagement.dto.response.ExceptionResponse;
import org.asodev.monolithic.warehousemanagement.dto.response.GenericResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    @ExceptionHandler(WMSException.class)
    public GenericResponse<ExceptionResponse> handleException(WMSException exception) {
        log.error(exception.getLocalizedMessage());
        return GenericResponse.failed(exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, UnexpectedTypeException.class})
    public GenericResponse<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception.getLocalizedMessage());

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // errors converted to string
        return GenericResponse.failed(errors.toString());

    }
}
