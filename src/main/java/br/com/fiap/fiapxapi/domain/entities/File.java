package br.com.fiap.fiapxapi.domain.entities;

public record File(
        String fileName,
        String extension,
        byte[] content
) {
}
