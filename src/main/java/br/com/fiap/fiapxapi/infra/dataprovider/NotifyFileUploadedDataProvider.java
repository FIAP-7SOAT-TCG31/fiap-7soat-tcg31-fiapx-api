package br.com.fiap.fiapxapi.infra.dataprovider;

import br.com.fiap.fiapxapi.application.gateways.NotifyFileUploadedGateway;
import br.com.fiap.fiapxapi.domain.entities.File;
import org.springframework.stereotype.Component;

@Component
public class NotifyFileUploadedDataProvider implements NotifyFileUploadedGateway {

    @Override
    public void notify(File file) {

    }
}
