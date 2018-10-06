package com.emanuelhonorio.uploaddownloadapi.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.emanuelhonorio.uploaddownloadapi.dto.FileDTO;
import com.emanuelhonorio.uploaddownloadapi.storage.FileStorage;

@Controller
@RequestMapping("/files")
public class FileController {
	
	@Autowired
	private FileStorage fileStorage;
	
	@GetMapping
	public String filesPage() {
		return "file";
	}
	
	@ResponseBody
	@PostMapping("/uploadFile")
	public FileDTO uploadOneFile(@RequestParam("file") MultipartFile file) {
		
		String fileName = fileStorage.salvar(file);
		
		String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/files/download/")
					.path(fileName)
					.toUriString();
		
		return new FileDTO(fileName, downloadUri, file.getContentType(), file.getSize());
	}
	
	@ResponseBody
	@PostMapping("/uploadFiles")
	public List<FileDTO> downloadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files)
                .stream()
                .map(file -> uploadOneFile(file))
                .collect(Collectors.toList());
	}

	@ResponseBody
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) throws IOException {
		
		Resource resource = fileStorage.loadAsResource(fileName);
		
		// Try to determine file's content type
		String contentType = null;
		contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		
		 // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
}
