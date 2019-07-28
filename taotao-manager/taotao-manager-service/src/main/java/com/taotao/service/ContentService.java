package com.taotao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	public EUDataGridResult getContentList(long categoryId, int page, int rows);

	TaotaoResult insertContent(TbContent content);

	TaotaoResult updateContent(TbContent content);
	
	TaotaoResult deleteContent(long[] ids);

}
