package com.emanuelhonorio.uploaddownloadapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.emanuelhonorio.uploaddownloadapi.property.ApiProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApiProperties.class)
public class UploadDownloadApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadDownloadApiApplication.class, args);
	}
}
