package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemParamItemService;
import com.taotao.service.ItemService;

/**
 * @description 商品管理Controller
 * 
 * @author zigzag
 */
@Controller
@ResponseBody
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemDescService itemDescService;

	@Autowired
	private ItemParamItemService itemParamItemService;

	@RequestMapping("/item/{itemId}")
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		return item;
	}

	@RequestMapping("/item/list")
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	private TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception {
		TaotaoResult result = itemService.createItem(item, desc, itemParams);
		return result;
	}

	@RequestMapping("/item/desc/{itemId}")
	public TaotaoResult getItemDescById(@PathVariable Long itemId) {
		TaotaoResult result = itemDescService.getItemDescById(itemId);
		return result;
	}

	@RequestMapping("/item/param/{itemId}")
	public TaotaoResult getItemParamById(@PathVariable Long itemId) {
		TaotaoResult result = itemParamItemService.getItemParamById(itemId);
		return result;
	}

	@RequestMapping(value = "/item/update", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams) throws Exception {
		TaotaoResult result = itemService.updateItem(item, desc, itemParams);
		return result;
	}

	/**
	 * 更新商品状态
	 */
	@RequestMapping(value = "/item/{method}", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItemStatus(@RequestParam("ids") List<Long> ids, @PathVariable String method) {
		TaotaoResult result = itemService.updateItemStatus(ids, method);
		return result;
	}

}
