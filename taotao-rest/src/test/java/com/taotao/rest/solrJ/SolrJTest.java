package com.taotao.rest.solrJ;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	@Test
	public void addDocument() throws Exception {
		// 创建一个连接
		SolrClient solrClient = new HttpSolrClient.Builder("http://192.168.254.129:8080/solr/collection1").build();
		// 创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test002");
		document.addField("item_title", "测试商品001");
		document.addField("item_price", 123);
		// 把文档对象写入索引库
		solrClient.add(document);
		// 提交
		solrClient.commit();
	}

	@Test
	public void queryDocument() throws Exception {
		// 创建一个连接
		SolrClient solrClient = new HttpSolrClient.Builder("http://192.168.254.129:8080/solr/collection1").build();
		// 创建一个查询条件
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery("*:*");
		query.setStart(0);
		// 执行查询
		QueryResponse response = solrClient.query(query);
		// 取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println(solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
		}
	}
}
