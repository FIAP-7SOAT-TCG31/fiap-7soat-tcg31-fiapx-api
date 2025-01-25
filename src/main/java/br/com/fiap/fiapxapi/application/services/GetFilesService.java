package br.com.fiap.fiapxapi.application.services;

import br.com.fiap.fiapxapi.application.gateways.GetFilesGateway;
import br.com.fiap.fiapxapi.domain.entities.File;
import br.com.fiap.fiapxapi.domain.usecases.GetFilesUseCase;

import java.util.List;
import java.util.Map;

public class GetFilesService implements GetFilesUseCase {

    private final GetFilesGateway getFilesGateway;

    public GetFilesService(GetFilesGateway getFilesGateway) {
        this.getFilesGateway = getFilesGateway;
    }

    @Override
    public List<File> get(Map<String, String> parameters) {
        return getFilesGateway.get(parameters);
    }
}
