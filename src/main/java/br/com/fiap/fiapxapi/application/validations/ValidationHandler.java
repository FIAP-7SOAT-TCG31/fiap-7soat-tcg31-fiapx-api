package br.com.fiap.fiapxapi.application.validations;

import br.com.fiap.fiapxapi.domain.entities.File;

import java.util.Map;

public interface ValidationHandler {

    void handle(Map<String, String> metadata, File file);
}
