package br.com.fiap.fiapxapi.infra.dataprovider;

import br.com.fiap.fiapxapi.application.gateways.NotifyFileUploadedGateway;
import br.com.fiap.fiapxapi.domain.entities.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotifyFileUploadedDataProvider implements NotifyFileUploadedGateway {

    @Override
    public void notify(File file) {
        log.info("file notified");
    }
}
