package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

public interface SearchServie {
	SearchResult search(String queryString, int page);
}
