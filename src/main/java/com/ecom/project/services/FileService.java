package com.ecom.project.services;

import org.springframework.web.multipart.MultipartFile;

// We will use this for all File related services
public interface FileService {
	String uploadImage(String path, MultipartFile productImage);
}
