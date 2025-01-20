package br.com.fiap.fiapxapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FiapXApiApplication {

	public static void main(String[] args) {
		System.setProperty("com.amazonaws.sdk.enableDefaultMetrics", "true");
		SpringApplication.run(FiapXApiApplication.class, args);
	}

}
