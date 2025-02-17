package org.asodev.monolithic.warehousemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.asodev.monolithic.warehousemanagement.constants.WMSConstants;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T> {
    private String status;
    private HttpStatus httpStatus;
    private T data;
    private T error;

    public static GenericResponse<ExceptionResponse> failed(String message) {
        return GenericResponse.<ExceptionResponse>builder()
                .status(WMSConstants.STATUS_FAILED)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .error(new ExceptionResponse(message))
                .build();
    }

    public static <T> GenericResponse<T> success(T data) {
        return GenericResponse.<T>builder()
                .status(WMSConstants.STATUS_SUCCESS)
                .httpStatus(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> GenericResponse<T> success(T data,HttpStatus httpStatus) {
        return GenericResponse.<T>builder()
                .status(WMSConstants.STATUS_SUCCESS)
                .httpStatus(httpStatus)
                .data(data)
                .build();
    }

    public static <T> GenericResponse<T> success(T data,String message, HttpStatus httpStatus) {
        return GenericResponse.<T>builder()
                .status(message)
                .httpStatus(httpStatus)
                .data(data)
                .build();
    }
}
