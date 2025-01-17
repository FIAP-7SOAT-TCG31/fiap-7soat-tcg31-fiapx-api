package br.com.fiap.fiapxapi.rest.api.controller;

import br.com.fiap.fiapxapi.application.exceptions.FileNotUploadedException;
import br.com.fiap.fiapxapi.rest.api.dto.FileUploadErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<FileUploadErrorResponseDto> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new FileUploadErrorResponseDto(
                        e.getLocalizedMessage(),
                        e.getCause().getLocalizedMessage()));
    }

    @ExceptionHandler(FileNotUploadedException.class)
    public ResponseEntity<FileUploadErrorResponseDto> handleFileNotUploadedException(FileNotUploadedException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new FileUploadErrorResponseDto(
                        e.getLocalizedMessage(),
                        e.getCause().getLocalizedMessage()));
    }
}