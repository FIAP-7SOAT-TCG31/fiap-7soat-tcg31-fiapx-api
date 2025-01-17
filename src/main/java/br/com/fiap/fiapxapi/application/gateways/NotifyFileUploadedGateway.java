package br.com.fiap.fiapxapi.application.gateways;

import br.com.fiap.fiapxapi.domain.entities.File;

public interface NotifyFileUploadedGateway {

    void notify(File file);
}
