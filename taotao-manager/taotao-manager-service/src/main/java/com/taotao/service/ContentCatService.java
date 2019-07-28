package com.taotao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCatService {
	List<EUTreeNode> getCatList(long parentId);

	TaotaoResult insertContentCat(long parentId, String name);
	
	TaotaoResult deleteContentCat(long id);
	
	TaotaoResult updateContentCat(long id, String name);

}
