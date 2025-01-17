package br.com.fiap.fiapxapi.application.validations.files;

import br.com.fiap.fiapxapi.application.exceptions.FileNotUploadedException;
import br.com.fiap.fiapxapi.application.gateways.GetParameterGateway;
import br.com.fiap.fiapxapi.application.validations.ValidationHandler;
import br.com.fiap.fiapxapi.domain.entities.File;
import br.com.fiap.fiapxapi.domain.entities.Parameter;

import java.util.Arrays;
import java.util.Map;

public class FileExtensionValidation implements ValidationHandler {

    private final GetParameterGateway getParameterGateway;
    private final ValidationHandler next;

    public FileExtensionValidation(GetParameterGateway getParameterGateway, ValidationHandler next) {
        this.getParameterGateway = getParameterGateway;
        this.next = next;
    }

    @Override
    public void handle(Map<String, String> metadata, File file) {

        String[] extensions = getParameterGateway.get(Parameter.EXTENSION).split(",");

        if (!Arrays.asList(extensions).contains(file.extension())) {
            throw new FileNotUploadedException("Extension not allowed");
        }

        if (next != null) {
            next.handle(metadata, file);
        }
    }
}
