package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCatMapper;
import com.taotao.pojo.TbContentCat;
import com.taotao.pojo.TbContentCatExample;
import com.taotao.pojo.TbContentCatExample.Criteria;
import com.taotao.service.ContentCatService;

/**
 * 
 * @description 内容分类管理
 * @author zigzag
 * @date 2019年6月5日
 */
@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCatMapper contentCatMapper;

	@Override
	public List<EUTreeNode> getCatList(long parentId) {
		// 根据parentId查询
		TbContentCatExample example = new TbContentCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCat> list = contentCatMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCat tbContentCat : list) {
			// 创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCat.getId());
			node.setText(tbContentCat.getName());
			node.setState(tbContentCat.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertContentCat(long parentId, String name) {
		// 创建pojo
		TbContentCat contentCat = new TbContentCat();
		contentCat.setName(name);
		contentCat.setIsParent(false);
		// 状态 可选值： 1（正常），2（删除）
		contentCat.setStatus(1);
		contentCat.setParentId(parentId);
		contentCat.setSortOrder(1);
		contentCat.setCreated(new Date());
		contentCat.setUpdated(new Date());
		// 添加记录
		contentCatMapper.insert(contentCat);
		// 查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCat parentCat = contentCatMapper.selectByPrimaryKey(parentId);
		// 判断是否为true
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			// 更新父节点
			contentCatMapper.updateByPrimaryKey(parentCat);
		}
		// 返回结果
		return TaotaoResult.ok(contentCat);
	}

	@Override
	public TaotaoResult deleteContentCat(long id) {
		// 获取该节点
		TbContentCat contentCat = contentCatMapper.selectByPrimaryKey(id);
		
//		// 更新节点状态
//		contentCat.setStatus(2);
//		contentCatMapper.updateByPrimaryKey(contentCat);
		
		// 如果该节点是父节点，则递归删除其所有子节点
		if (contentCat.getIsParent()) {
			TbContentCatExample example = new TbContentCatExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(id);
			contentCatMapper.deleteByExample(example);

		}
		// 删除该节点
		contentCatMapper.deleteByPrimaryKey(id);
		// 判断该节点的父节点是否还有其他子节点，如果没有需要把父节点的isParent改为false
		TbContentCat parentNode = contentCatMapper.selectByPrimaryKey(contentCat.getParentId());
		TbContentCatExample example = new TbContentCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(contentCat.getParentId());
		List<TbContentCat> list = contentCatMapper.selectByExample(example);
		if (list.size() == 0) {
			parentNode.setIsParent(false);
			contentCatMapper.updateByPrimaryKey(parentNode);
		}
		// 返回结果
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContentCat(long id, String name) {
		// 封装pojo
		TbContentCat contentCat = contentCatMapper.selectByPrimaryKey(id);
		contentCat.setId(id);
		contentCat.setName(name);
		contentCat.setUpdated(new Date());
		// 更新内容分类表
		contentCatMapper.updateByPrimaryKey(contentCat);
		return TaotaoResult.ok();
	}

}
