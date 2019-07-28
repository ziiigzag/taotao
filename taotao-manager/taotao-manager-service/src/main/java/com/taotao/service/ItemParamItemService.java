package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParamItem;

public interface ItemParamItemService {
	String getItemParamByItemId(Long itemId);
	TaotaoResult getItemParamById(Long itemId);
}
