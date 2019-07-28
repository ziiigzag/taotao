package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(long itemId);

	EUDataGridResult getItemList(int page, int rows);

	TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception;
		
	TaotaoResult updateItem(TbItem item, String desc, String itemParams) throws Exception;

	TaotaoResult updateItemStatus(List<Long> ids, String method);

}
