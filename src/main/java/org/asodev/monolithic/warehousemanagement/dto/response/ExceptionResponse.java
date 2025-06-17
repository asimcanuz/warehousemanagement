package org.asodev.monolithic.warehousemanagement.dto.response;

public class ExceptionResponse {
    private String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public ExceptionResponse() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
