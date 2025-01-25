package br.com.fiap.fiapxapi.application.gateways;

import br.com.fiap.fiapxapi.domain.entities.File;

import java.util.List;
import java.util.Map;

public interface GetFilesGateway {

    List<File> get(Map<String, String> parameters);
}
