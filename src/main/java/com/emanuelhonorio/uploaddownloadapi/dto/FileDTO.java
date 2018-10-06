package com.emanuelhonorio.uploaddownloadapi.dto;

public class FileDTO {
	private String name;
	private String downloadUri;
	private String fileType;
	private long size;

	public FileDTO(String name, String downloadUri, String fileType, long size) {
		this.name = name;
		this.downloadUri = downloadUri;
		this.fileType = fileType;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDownloadUri() {
		return downloadUri;
	}

	public void setDownloadUri(String downloadUri) {
		this.downloadUri = downloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
