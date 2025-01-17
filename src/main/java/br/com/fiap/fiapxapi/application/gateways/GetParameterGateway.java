package br.com.fiap.fiapxapi.application.gateways;

import br.com.fiap.fiapxapi.domain.entities.Parameter;

public interface GetParameterGateway {

    String get(Parameter parameter);
}
