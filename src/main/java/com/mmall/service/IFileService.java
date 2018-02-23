package com.mmall.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

	Map<String, Object> upload(MultipartFile file, String path);

}
