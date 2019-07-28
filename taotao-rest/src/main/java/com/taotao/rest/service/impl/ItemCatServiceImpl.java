package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * 
 * @description 商品分类服务
 * @author zigzag
 * @date 2019年6月5日
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_ITEM_CATEGORY_REDIS_KEY}")
	private String INDEX_ITEM_CATEGORY_REDIS_KEY;

	@Override
	public CatResult getItemCatList() {

		CatResult catResult = new CatResult();
		// 查询分类列表
		catResult.setData(getCatList(0));
		return catResult;
	}

	/**
	 * 查询分类列表
	 * 
	 * @param parentId
	 * @return
	 */
	private List<?> getCatList(long parentId) {
		// 从缓存中取内容
		try {
			String result = jedisClient.hGet(INDEX_ITEM_CATEGORY_REDIS_KEY, parentId + "");
			if (!StringUtils.isBlank(result)) {
				// 把字符串转换成list
				List<CatNode> resultList = JsonUtils.jsonToList(result, CatNode.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 返回值list
		List resultList = new ArrayList<>();
		// 向list中添加节点
		int count = 0;
		for (TbItemCat itemCat : list) {
			// 判断是否为父结点
			if (itemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName(
							"<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
				} else {
					catNode.setName(itemCat.getName());
				}
				catNode.setUrl("/products/" + itemCat.getId() + ".html");
				catNode.setItem(getCatList(itemCat.getId()));

				resultList.add(catNode);
				count++;
				// 第一层只取14条记录
				if (parentId == 0 && count >= 14) {
					break;
				}

				// 如果是叶子结点
			} else {
				resultList.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName());
			}

		}
		// 向缓存中添加内容
		try {
			// 把list转换成字符串
			String cacheString = JsonUtils.objectToJson(resultList);
			jedisClient.hSet(INDEX_ITEM_CATEGORY_REDIS_KEY, parentId + "", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
}
