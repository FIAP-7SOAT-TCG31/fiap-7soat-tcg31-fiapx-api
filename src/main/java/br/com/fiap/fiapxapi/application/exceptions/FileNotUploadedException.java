package br.com.fiap.fiapxapi.application.exceptions;

public class FileNotUploadedException extends RuntimeException {

    private final String details;

    public FileNotUploadedException(String message) {
        super(message);
        this.details = "Validation rule not satisfied";
    }

    public FileNotUploadedException(String message, String details) {
        super(message);
        this.details = details;
    }


}
