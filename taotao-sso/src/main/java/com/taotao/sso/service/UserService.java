package com.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {
	// 用户注册接口校验
	TaotaoResult checkData(String content, Integer type);

	TaotaoResult createUser(TbUser user);

	TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response); // 参数名命名以接口文档定以为准

	// 通过token查询用户信息
	TaotaoResult getUserByToken(String token);

	// 安全退出
	TaotaoResult userLogout(String token);
}
