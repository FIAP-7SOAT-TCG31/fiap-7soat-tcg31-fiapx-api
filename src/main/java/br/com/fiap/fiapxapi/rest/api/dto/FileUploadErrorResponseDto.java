package br.com.fiap.fiapxapi.rest.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record FileUploadErrorResponseDto(String message, String detailedMessage) {
}
