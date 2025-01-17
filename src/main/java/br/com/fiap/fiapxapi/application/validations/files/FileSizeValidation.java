package br.com.fiap.fiapxapi.application.validations.files;

import br.com.fiap.fiapxapi.application.exceptions.FileNotUploadedException;
import br.com.fiap.fiapxapi.application.gateways.GetParameterGateway;
import br.com.fiap.fiapxapi.application.validations.ValidationHandler;
import br.com.fiap.fiapxapi.domain.entities.File;
import br.com.fiap.fiapxapi.domain.entities.Parameter;

import java.util.Map;

public class FileSizeValidation implements ValidationHandler {

    private final GetParameterGateway getParameterGateway;
    private final ValidationHandler next;

    public FileSizeValidation(GetParameterGateway getParameterGateway, ValidationHandler next) {
        this.getParameterGateway = getParameterGateway;
        this.next = next;
    }

    @Override
    public void handle(Map<String, String> metadata, File file) {

        long sizeLimit = Long.parseLong(getParameterGateway.get(Parameter.SIZE));

        if (sizeLimit < file.content().length) {
            throw new FileNotUploadedException("File size is bigger than the limit");
        }

        if (next != null) {
            next.handle(metadata, file);
        }
    }
}
