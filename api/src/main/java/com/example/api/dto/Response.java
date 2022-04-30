package com.example.api.dto;

public class Response {

    private final int status;
    private Object data;
    private final String message;

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(int status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Response(Exception exception) {
        this.status = -1;
        this.data = null;
        this.message = exception.getMessage();

        System.out.println(exception);
    }

    public int getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
