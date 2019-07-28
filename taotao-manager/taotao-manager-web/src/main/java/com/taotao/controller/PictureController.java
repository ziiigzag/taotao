package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

/**
 * @description 上传图片处理
 * @author zigzag
 * @date 2019年5月15日
 */

@Controller
public class PictureController {
	@Autowired
	private PictureService pictureService;

	@RequestMapping(value="/pic/upload", produces= MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile) {
		Map result = pictureService.uploadPicture(uploadFile);
		// 为了保证功能性的兼容性，需要把result转换成JSON格式的字符串
		String json = JsonUtils.objectToJson(result);
		return json;
	}

}
