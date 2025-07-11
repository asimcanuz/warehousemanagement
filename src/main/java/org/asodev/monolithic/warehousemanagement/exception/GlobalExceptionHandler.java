package org.asodev.monolithic.warehousemanagement.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.asodev.monolithic.warehousemanagement.dto.response.ExceptionResponse;
import org.asodev.monolithic.warehousemanagement.dto.response.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(WMSException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse<ExceptionResponse> handleWMSException(
            WMSException exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        log.error("[ERROR-ID: {}] WMS Exception at {} - Message: {}",
                errorId, request.getRequestURI(), exception.getMessage(), exception);

        return GenericResponse.failed(String.format("[%s] %s", errorId, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse<ExceptionResponse> handleValidationException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        Map<String, String> fieldErrors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        String errorDetails = fieldErrors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        log.warn("[ERROR-ID: {}] Validation failed at {} - Errors: {}",
                errorId, request.getRequestURI(), fieldErrors);

        return GenericResponse.failed(String.format("[%s] Validation failed: %s", errorId, errorDetails));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse<ExceptionResponse> handleConstraintViolationException(
            ConstraintViolationException exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        log.warn("[ERROR-ID: {}] Constraint violation at {} - Message: {}",
                errorId, request.getRequestURI(), exception.getMessage());

        return GenericResponse.failed(String.format("[%s] Constraint violation: %s",
                errorId, exception.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public GenericResponse<ExceptionResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        log.error("[ERROR-ID: {}] Data integrity violation at {} - Message: {}",
                errorId, request.getRequestURI(), exception.getMessage(), exception);

        return GenericResponse
                .failed(String.format("[%s] Data integrity violation - possibly duplicate entry", errorId));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public GenericResponse<ExceptionResponse> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        log.warn("[ERROR-ID: {}] Method not allowed at {} - Method: {} - Supported: {}",
                errorId, request.getRequestURI(), request.getMethod(),
                String.join(", ", exception.getSupportedMethods()));

        return GenericResponse.failed(String.format("[%s] HTTP method %s not supported. Supported methods: %s",
                errorId, exception.getMethod(), String.join(", ", exception.getSupportedMethods())));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse<ExceptionResponse> handleMissingParameterException(
            MissingServletRequestParameterException exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        log.warn("[ERROR-ID: {}] Missing parameter at {} - Parameter: {} Type: {}",
                errorId, request.getRequestURI(), exception.getParameterName(), exception.getParameterType());

        return GenericResponse.failed(String.format("[%s] Missing required parameter: %s (%s)",
                errorId, exception.getParameterName(), exception.getParameterType()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse<ExceptionResponse> handleTypeMismatchException(
            MethodArgumentTypeMismatchException exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        Class<?> requiredTypeClass = exception.getRequiredType();
        String requiredType = "Unknown";
        if (requiredTypeClass != null) {
            requiredType = requiredTypeClass.getSimpleName();
        }

        log.warn("[ERROR-ID: {}] Type mismatch at {} - Parameter: {} - Value: {} - Expected: {}",
                errorId, request.getRequestURI(), exception.getName(), exception.getValue(), requiredType);

        return GenericResponse.failed(String.format("[%s] Invalid parameter type for '%s'. Expected: %s, Got: %s",
                errorId, exception.getName(), requiredType, exception.getValue()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse<ExceptionResponse> handleMessageNotReadableException(
            HttpMessageNotReadableException exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        log.warn("[ERROR-ID: {}] Message not readable at {} - Message: {}",
                errorId, request.getRequestURI(), exception.getMessage());

        return GenericResponse.failed(String.format("[%s] Invalid request body format", errorId));
    }

    @ExceptionHandler(FileOperationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericResponse<ExceptionResponse> handleFileOperationException(
            FileOperationException exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        log.error("[ERROR-ID: {}] File operation failed at {} - Message: {}",
                errorId, request.getRequestURI(), exception.getMessage(), exception);

        return GenericResponse.failed(String.format("[%s] File operation failed: %s", errorId, exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericResponse<ExceptionResponse> handleGenericException(
            Exception exception, HttpServletRequest request) {

        String errorId = UUID.randomUUID().toString();

        log.error("[ERROR-ID: {}] Unexpected error at {} - Type: {} - Message: {}",
                errorId, request.getRequestURI(), exception.getClass().getSimpleName(),
                exception.getMessage(), exception);

        return GenericResponse
                .failed(String.format("[%s] An unexpected error occurred. Please contact support.", errorId));
    }
}
