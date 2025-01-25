package br.com.fiap.fiapxapi.domain.usecases;

import br.com.fiap.fiapxapi.domain.entities.File;

import java.util.List;
import java.util.Map;

public interface GetFilesUseCase {

    List<File> get(Map<String, String> parameters);
}
