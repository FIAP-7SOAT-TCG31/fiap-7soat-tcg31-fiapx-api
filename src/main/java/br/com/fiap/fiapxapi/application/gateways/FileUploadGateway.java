package br.com.fiap.fiapxapi.application.gateways;

import br.com.fiap.fiapxapi.domain.entities.File;

import java.util.Map;

public interface FileUploadGateway {

    void upload(Map<String, String> metadata, File file);
}
