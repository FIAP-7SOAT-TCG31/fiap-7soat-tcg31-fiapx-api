package br.com.fiap.fiapxapi.configuration;

import br.com.fiap.fiapxapi.application.gateways.FileUploadGateway;
import br.com.fiap.fiapxapi.application.gateways.GetFilesGateway;
import br.com.fiap.fiapxapi.application.gateways.GetParameterGateway;
import br.com.fiap.fiapxapi.application.gateways.NotifyFileUploadedGateway;
import br.com.fiap.fiapxapi.application.services.FileUploadService;
import br.com.fiap.fiapxapi.application.services.GetFilesService;
import br.com.fiap.fiapxapi.application.validations.files.FileExtensionValidation;
import br.com.fiap.fiapxapi.application.validations.files.FileSizeValidation;
import br.com.fiap.fiapxapi.domain.usecases.FileUploadUseCase;
import br.com.fiap.fiapxapi.domain.usecases.GetFilesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiapXApiConfiguration {

    @Bean
    public FileUploadUseCase beanFileUploadUseCase(GetParameterGateway getParameterGateway, FileUploadGateway fileUploadGateway, NotifyFileUploadedGateway notifyFileUploadedGateway) {

        var fileSizeValidation = new FileSizeValidation(getParameterGateway, null);
        var fileExtensionValidation = new FileExtensionValidation(getParameterGateway, fileSizeValidation);

        return new FileUploadService(fileExtensionValidation, fileUploadGateway, notifyFileUploadedGateway);
    }

    @Bean
    public GetFilesUseCase beanGetFilesService(GetFilesGateway getFilesGateway) {

        return new GetFilesService(getFilesGateway);
    }

}
