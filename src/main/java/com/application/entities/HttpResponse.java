package com.application.entities;

import org.springframework.http.HttpStatus;

public class HttpResponse {

    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public HttpResponse() { }

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }

    public void setHttpStatusCode(int httpStatusCode) { this.httpStatusCode = httpStatusCode; }
    public void setHttpStatus(HttpStatus httpStatus) { this.httpStatus = httpStatus; }
    public void setReason(String reason) { this.reason = reason; }
    public void setMessage(String message) { this.message = message; }

    public int getHttpStatusCode() { return httpStatusCode; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getReason() { return reason; }
    public String getMessage() { return message; }

}
