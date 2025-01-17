package br.com.fiap.fiapxapi.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class Properties {

    private String maxFileSize;
    private String fileExtensions;

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getFileExtensions() {
        return fileExtensions;
    }

    public void setFileExtensions(String fileExtensions) {
        this.fileExtensions = fileExtensions;
    }
}
