package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemDesc;

public interface ItemDescService {
	TaotaoResult getItemDescById(long itemId);
}
