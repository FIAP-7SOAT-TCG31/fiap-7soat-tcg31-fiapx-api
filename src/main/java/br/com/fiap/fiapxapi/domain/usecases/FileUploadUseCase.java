package br.com.fiap.fiapxapi.domain.usecases;

import br.com.fiap.fiapxapi.domain.entities.File;

import java.util.Map;

public interface FileUploadUseCase {

    void upload(Map<String, String> metadata, File file);
}
