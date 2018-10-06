package com.emanuelhonorio.uploaddownloadapi.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
	public String salvar(MultipartFile file);
	public Resource loadAsResource(String fileName);
}
