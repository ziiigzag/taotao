package com.taotao.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {

	@Test
	public void testAddDocument() throws Exception {
		// 创建一个和solr集群的连接
		List<String> zkHosts = new ArrayList<>();
		zkHosts.add("192.168.254.129:2181");
		zkHosts.add("192.168.254.129:2182");
		zkHosts.add("192.168.254.129:2183");
		CloudSolrClient solrClient = new CloudSolrClient.Builder(zkHosts, Optional.empty()).build();
		// 默认的collection
		solrClient.setDefaultCollection("collection1");
		// 创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		// 向文档中添加域
		document.addField("id", "test001");
		document.addField("item_title", "测试商品");
		// 把文档中添加到索引库
		solrClient.add(document);
		// 提交
		solrClient.commit();
	}

	@Test
	public void testDeleteDocument() throws Exception {
		// 创建一个和solr集群的连接
		List<String> zkHosts = new ArrayList<>();
		zkHosts.add("192.168.254.129:2181");
		zkHosts.add("192.168.254.129:2182");
		zkHosts.add("192.168.254.129:2183");
		CloudSolrClient solrClient = new CloudSolrClient.Builder(zkHosts, Optional.empty()).build();
		// 默认的collection
		solrClient.setDefaultCollection("collection1");

		solrClient.deleteByQuery("*:*");
		// 提交
		solrClient.commit();
	}
}
