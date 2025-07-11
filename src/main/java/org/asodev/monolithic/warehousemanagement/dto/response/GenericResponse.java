package org.asodev.monolithic.warehousemanagement.dto.response;

import org.asodev.monolithic.warehousemanagement.constants.WMSConstants;
import org.springframework.http.HttpStatus;

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
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(message);
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

    public static <T> GenericResponseBuilder<T> builder() {
        return new GenericResponseBuilder<T>();
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

    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GenericResponse))
            return false;
        final GenericResponse<?> other = (GenericResponse<?>) o;
        if (!other.canEqual((Object) this))
            return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status))
            return false;
        final Object this$httpStatus = this.getHttpStatus();
        final Object other$httpStatus = other.getHttpStatus();
        if (this$httpStatus == null ? other$httpStatus != null : !this$httpStatus.equals(other$httpStatus))
            return false;
        final Object this$data = this.getData();
        final Object other$data = other.getData();
        if (this$data == null ? other$data != null : !this$data.equals(other$data))
            return false;
        final Object this$error = this.getError();
        final Object other$error = other.getError();
        if (this$error == null ? other$error != null : !this$error.equals(other$error))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GenericResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $httpStatus = this.getHttpStatus();
        result = result * PRIME + ($httpStatus == null ? 43 : $httpStatus.hashCode());
        final Object $data = this.getData();
        result = result * PRIME + ($data == null ? 43 : $data.hashCode());
        final Object $error = this.getError();
        result = result * PRIME + ($error == null ? 43 : $error.hashCode());
        return result;
    }

    public String toString() {
        return "GenericResponse(status=" + this.getStatus() + ", httpStatus=" + this.getHttpStatus() + ", data="
                + this.getData() + ", error=" + this.getError() + ")";
    }

    public static class GenericResponseBuilder<T> {
        private String status;
        private HttpStatus httpStatus;
        private T data;
        private T error;

        GenericResponseBuilder() {
        }

        public GenericResponseBuilder<T> status(String status) {
            this.status = status;
            return this;
        }

        public GenericResponseBuilder<T> httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public GenericResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public GenericResponseBuilder<T> error(T error) {
            this.error = error;
            return this;
        }

        public GenericResponse<T> build() {
            return new GenericResponse<T>(this.status, this.httpStatus, this.data, this.error);
        }

        public String toString() {
            return "GenericResponse.GenericResponseBuilder(status=" + this.status + ", httpStatus=" + this.httpStatus
                    + ", data=" + this.data + ", error=" + this.error + ")";
        }
    }
}
