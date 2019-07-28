package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService {
	
	@Autowired
	private TbItemDescMapper itemDescMpper;
	
	@Override
	public TaotaoResult getItemDescById(long itemId) {
		TbItemDesc desc = itemDescMpper.selectByPrimaryKey(itemId);
		return TaotaoResult.ok(desc);
	}

}
