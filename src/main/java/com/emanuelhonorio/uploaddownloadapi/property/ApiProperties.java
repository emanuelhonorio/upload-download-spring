package com.emanuelhonorio.uploaddownloadapi.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
public class ApiProperties {

	private final FileStorageProperties file = new FileStorageProperties();

	public static class FileStorageProperties {
		private String uploadDir;

		public String getUploadDir() {
			return uploadDir;
		}

		public void setUploadDir(String uploadDir) {
			this.uploadDir = uploadDir;
		}
	}

	public FileStorageProperties getFile() {
		return file;
	}

}
