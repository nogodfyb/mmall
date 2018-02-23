package com.mmall.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.plaf.synth.SynthStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;

@Service
public class FileServiceImpl implements IFileService {

	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public Map<String, Object> upload(MultipartFile file, String path) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		String fileName = file.getOriginalFilename();
		// 扩展名
		// abc.jpg
		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
		logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

		File fileDir = new File(path);
		if (!fileDir.exists()) {
			//赋予可写权限
			fileDir.setWritable(true);
			fileDir.mkdirs();
		}
		File targetFile = new File(path, uploadFileName);
		try {
			file.transferTo(targetFile);
			// 文件已经上传成功了
			Boolean result=FTPUtil.uploadFile(Lists.newArrayList(targetFile));
			map.put("result", result);
			map.put("targetFileName", targetFile.getName());
			// 已经上传到ftp服务器上
			//删除在tomcat下的文件
			targetFile.delete();
		} catch (IOException e) {
			logger.error("上传文件异常", e);
			return null;
		}
		// A:abc.jpg
		// B:abc.jpg
		return map;
	}

}
