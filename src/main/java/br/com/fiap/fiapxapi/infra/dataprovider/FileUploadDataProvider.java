package br.com.fiap.fiapxapi.infra.dataprovider;

import br.com.fiap.fiapxapi.application.gateways.FileUploadGateway;
import br.com.fiap.fiapxapi.domain.entities.File;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileUploadDataProvider implements FileUploadGateway {

    @Override
    public void upload(Map<String, String> metadata, File file) {

    }
}
