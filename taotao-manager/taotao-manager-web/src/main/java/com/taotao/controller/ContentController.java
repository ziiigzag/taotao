package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

/**
 * 
 * @description 内容管理Controller
 * @author zigzag
 * @date 2019年6月6日
 */
@Controller
@RequestMapping("/content")
@ResponseBody
public class ContentController {

	@Autowired
	ContentService contentService;
	
	@RequestMapping("/query/list")
	public EUDataGridResult getContentList(Long categoryId, int page, int rows) {
		EUDataGridResult result = contentService.getContentList(categoryId, page, rows);
		return result;
	}
	
	@RequestMapping("/save")
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}
	
	@RequestMapping("/edit")
	public TaotaoResult updateContent(TbContent content) {
		TaotaoResult result = contentService.updateContent(content);
		return result;
	}
	
	@RequestMapping("/delete")
	public TaotaoResult updateContent(long[] ids) {
		TaotaoResult result = contentService.deleteContent(ids);
		return result;
	}
}
