package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCatService;

/**
 * 
 * @description 内容分类管理
 * @author zigzag
 * @date 2019年6月5日
 */
@Controller
@RequestMapping("/content/category")
@ResponseBody
public class ContentCatController {
	@Autowired
	private ContentCatService contentCatService;

	@RequestMapping("/list")
	public List<EUTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EUTreeNode> list = contentCatService.getCatList(parentId);
		return list;
	}

	@RequestMapping("/create")
	public TaotaoResult createContentCat(Long parentId, String name) {
		TaotaoResult result = contentCatService.insertContentCat(parentId, name);
		return result;
	}

	@RequestMapping("/delete")
	public TaotaoResult deleteContentCat(Long id) {
		TaotaoResult result = contentCatService.deleteContentCat(id);
		return result;
	}

	@RequestMapping("/update")
	public TaotaoResult updateContentCat(Long id, String name) {
		TaotaoResult result = contentCatService.updateContentCat(id, name);
		return result;
	}

}
