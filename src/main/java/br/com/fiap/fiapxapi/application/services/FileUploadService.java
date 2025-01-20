package br.com.fiap.fiapxapi.application.services;

import br.com.fiap.fiapxapi.application.gateways.FileUploadGateway;
import br.com.fiap.fiapxapi.application.gateways.NotifyFileUploadedGateway;
import br.com.fiap.fiapxapi.application.validations.ValidationHandler;
import br.com.fiap.fiapxapi.domain.entities.File;
import br.com.fiap.fiapxapi.domain.usecases.FileUploadUseCase;

import java.util.Map;

public class FileUploadService implements FileUploadUseCase {

    private final ValidationHandler validation;
    private final FileUploadGateway fileUploadGateway;
    private final NotifyFileUploadedGateway notifyFileUploadedGateway;

    public FileUploadService(ValidationHandler validation, FileUploadGateway fileUploadGateway, NotifyFileUploadedGateway notifyFileUploadedGateway) {
        this.validation = validation;
        this.fileUploadGateway = fileUploadGateway;
        this.notifyFileUploadedGateway = notifyFileUploadedGateway;
    }

    @Override
    public void upload(Map<String, String> metadata, File file) {

        validation.handle(metadata, file);
        //this.fileUploadGateway.upload(metadata, file);
        this.notifyFileUploadedGateway.notify(file);
    }
}
