package org.asodev.monolithic.warehousemanagement.dto.response;

import org.asodev.monolithic.warehousemanagement.constants.WMSConstants;
import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public class GenericResponse<T> {
    private String status;
    private HttpStatus httpStatus;
    private T data;
    private T error;

    public GenericResponse(String status, HttpStatus httpStatus, T data, T error) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.data = data;
        this.error = error;
    }

    public static GenericResponse<ExceptionResponse> failed(String message) {
        ExceptionResponse error = ExceptionResponse.builder()
                .message(message)
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return GenericResponse.<ExceptionResponse>builder()
                .status(WMSConstants.STATUS_FAILED)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .error(error)
                .build();
    }

    public static GenericResponse<ExceptionResponse> failed(ExceptionResponse exceptionResponse) {
        return GenericResponse.<ExceptionResponse>builder()
                .status(WMSConstants.STATUS_FAILED)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .error(exceptionResponse)
                .build();
    }

    public static <T> GenericResponse<T> success(T data) {
        return GenericResponse.<T>builder()
                .status(WMSConstants.STATUS_SUCCESS)
                .httpStatus(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> GenericResponse<T> success(T data, HttpStatus httpStatus) {
        return GenericResponse.<T>builder()
                .status(WMSConstants.STATUS_SUCCESS)
                .httpStatus(httpStatus)
                .data(data)
                .build();
    }

    public static <T> GenericResponse<T> success(T data, String message, HttpStatus httpStatus) {
        return GenericResponse.<T>builder()
                .status(message)
                .httpStatus(httpStatus)
                .data(data)
                .build();
    }

    public String getStatus() {
        return this.status;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public T getData() {
        return this.data;
    }

    public T getError() {
        return this.error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setError(T error) {
        this.error = error;
    }
}
