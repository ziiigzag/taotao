package com.taotao.service.impl;

import java.awt.Robot;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.IdUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

/**
 * @description 商品管理服务
 * 
 * @author zigzag
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Value("${SOLR_SERVER_URL}")
	private String SOLR_SERVER_URL;

	@Override
	public TbItem getItemById(long itemId) {

		// TbItem item = itemMapper.selectByPrimaryKey(itemId);
		// 添加查询条件
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}

	/**
	 * @description 商品列表查询
	 */
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		// 查询商品列表
		TbItemExample example = new TbItemExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 添加商品
	 */
	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception {
		// item补全
		// 生成商品ID
		Long itemId = IdUtils.genItemId();
		item.setId(itemId);
		// 商品状态：1-正常 2-下架 3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 插入到数据库
		itemMapper.insert(item);
		// 添加商品描述信息
		TaotaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		// 添加规格参数
		result = insertItemParamItem(itemId, itemParams);
		if (result.getStatus() != 200) {
			throw new Exception();
		}

		Robot r = new Robot();
		r.delay(1000);

//		// 添加检索库同步逻辑
//		try {		
//			HttpClientUtil.doGet(SOLR_SERVER_URL + itemId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return TaotaoResult.ok();
	}

	/**
	 * @description 添加商品描述
	 * 
	 * @param itemId
	 * @param desc
	 */
	private TaotaoResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();

	}

	/**
	 * @description 修改商品描述
	 * 
	 * @param itemId
	 * @param desc
	 */
	private TaotaoResult updateItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(itemDescMapper.selectByPrimaryKey(itemId).getCreated());
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
		return TaotaoResult.ok();

	}

	/**
	 * 添加规格参数
	 * 
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private TaotaoResult insertItemParamItem(Long itemId, String itemParams) {
		// 创建一个pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		// 向表中插入数据
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}

	/**
	 * 修改规格参数
	 * 
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private TaotaoResult updateItemParamItem(Long itemId, String itemParam) {
		// 创建一个pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		// 补全pojo
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);

		itemParamItem.setId(itemParamItemMapper.selectByExample(example).get(0).getId());
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(itemParamItemMapper.selectByExample(example).get(0).getCreated());
		itemParamItem.setUpdated(new Date());
		// 向表中插入数据
		itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);

		return TaotaoResult.ok();
	}

	/**
	 * 修改商品
	 */
	@Override
	public TaotaoResult updateItem(TbItem item, String desc, String itemParam) throws Exception {
		// item补全
		item.setCreated(itemMapper.selectByPrimaryKey(item.getId()).getCreated());
		item.setUpdated(new Date());
		item.setStatus(itemMapper.selectByPrimaryKey(item.getId()).getStatus());
		// 修改数据
		itemMapper.updateByPrimaryKey(item);
		// 修改商品描述信息
		TaotaoResult result = updateItemDesc(item.getId(), desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		// 修改商品规格参数
		result = updateItemParamItem(item.getId(), itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}

		Robot r = new Robot();
		r.delay(1000);

//		// 添加检索库同步逻辑
//		try {		
//			HttpClientUtil.doGet(SOLR_SERVER_URL + itemId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateItemStatus(List<Long> ids, String method) {
		TbItem item = new TbItem();
		if (method.equals("reshelf")) {
			// 正常，更新status=3即可
			item.setStatus((byte) 1);
		} else if (method.equals("instock")) {
			// 下架，更新status=3即可
			item.setStatus((byte) 2);
		} else if (method.equals("delete")) {
			// 删除，更新status=3即可
			item.setStatus((byte) 3);
		}

		for (Long id : ids) {
			// 创建查询条件，根据id更新
			TbItemExample tbItemExample = new TbItemExample();
			Criteria criteria = tbItemExample.createCriteria();
			criteria.andIdEqualTo(id);
			// 第一个参数 是要修改的部分值组成的对象，其中有些属性为null则表示该项不修改。
			// 第二个参数 是一个对应的查询条件的类， 通过这个类可以实现 order by 和一部分的where 条件。
			itemMapper.updateByExampleSelective(item, tbItemExample);
		}
		return TaotaoResult.ok();
	}

}
