package br.com.fiap.fiapxapi.rest.api.controller;

import br.com.fiap.fiapxapi.domain.entities.File;
import br.com.fiap.fiapxapi.domain.usecases.FileUploadUseCase;
import br.com.fiap.fiapxapi.rest.api.constants.RestApiConstants;
import br.com.fiap.fiapxapi.rest.api.dto.FileUploadResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(RestApiConstants.FILE_BASE_PATH)
public class FileUploadController {

    private final FileUploadUseCase fileUploadUseCase;

    public FileUploadController(FileUploadUseCase fileUploadUseCase) {
        this.fileUploadUseCase = fileUploadUseCase;
    }

    @PostMapping(RestApiConstants.UPLOAD_PATH)
    public ResponseEntity<?> uploadFileWithMetadata(
            @RequestParam("file") MultipartFile file,
            @RequestParam Map<String, String> metadata) throws IOException {

        String fileName = file.getOriginalFilename();
        fileUploadUseCase.upload(metadata, new File(fileName, fileName.substring(fileName.lastIndexOf('.') + 1), file.getBytes()));

        return ResponseEntity.ok(new FileUploadResponseDto(fileName, file.getSize()));
    }
}
