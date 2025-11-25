package org.utn.mutantesapi.dto;

public class ErrorResponse {
    private String message;
    private String details;

    // Constructor con dos parámetros (el que necesitas)
    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    // Getters y Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}