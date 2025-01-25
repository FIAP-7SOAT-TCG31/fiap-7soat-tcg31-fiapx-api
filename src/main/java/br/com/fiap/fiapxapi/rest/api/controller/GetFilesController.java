package br.com.fiap.fiapxapi.rest.api.controller;

import br.com.fiap.fiapxapi.domain.usecases.GetFilesUseCase;
import br.com.fiap.fiapxapi.rest.api.constants.RestApiConstants;
import br.com.fiap.fiapxapi.rest.api.dto.GetFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(RestApiConstants.FILE_BASE_PATH)
public class GetFilesController {

    private final GetFilesUseCase getFilesUseCase;

    public GetFilesController(GetFilesUseCase getFilesUseCase) {
        this.getFilesUseCase = getFilesUseCase;
    }

    @GetMapping(RestApiConstants.UPLOAD_PATH)
    public ResponseEntity<?> getFiles(
            @RequestParam Map<String, String> headers) throws IOException {

        var files = getFilesUseCase.get(headers);
        return ResponseEntity.ok(files.stream().map(file -> new GetFileResponse(file.fileName(), file.content().length, file.extension())));
    }
}
