package br.com.fiap.fiapxapi.infra.dataprovider.dto;

import java.time.Instant;

public record FileDto(String fileName, String contentType, Instant createdAt) {
}
