package com.emanuelhonorio.uploaddownloadapi.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.emanuelhonorio.uploaddownloadapi.exception.FileStorageException;
import com.emanuelhonorio.uploaddownloadapi.property.ApiProperties;

@Component
public class FileStorageLocal implements FileStorage {

	private final Path storageLocation;
	
	@Autowired
	public FileStorageLocal(ApiProperties ApiProperties) {
		this.storageLocation = Paths.get(ApiProperties.getFile().getUploadDir())
				.toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.storageLocation);
		} catch (IOException e) {
			throw new FileStorageException("Nao foi possivel criar o diretorio para upload de arquivos");
		}
	}
	
	@Override
	public String salvar(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		// Check if the file's name contains invalid characters
		if(fileName.contains("..")) {
			throw new FileStorageException("Sorry! File contains invalid characters!");
		}
		
		// Copy file to the target location (Replacing existing file with the same name)
		Path targetLocation = this.storageLocation.resolve(fileName);
		
		System.out.println("targetLocation" + targetLocation.toUri().toString());
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
		}
	}

	@Override
	public Resource loadAsResource(String fileName) {
		Path filePath = this.storageLocation.resolve(fileName).normalize();
		try {
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			} else {
				throw new FileStorageException("File not found " + fileName);
			}
			
		} catch (MalformedURLException e) {
			throw new FileStorageException("File not found " + fileName);
		}
	}

}
