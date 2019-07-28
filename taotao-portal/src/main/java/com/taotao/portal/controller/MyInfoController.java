package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @description 个人信息页面展示
 * @author zigzag
 * @date 2019年6月22日
 */
@Controller
public class MyInfoController {
	
	@RequestMapping("/myinfo")
	public String showMyInfo() {
		return "my-info";
	}
}
