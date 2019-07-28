package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FTPTest {
	
	@Test
	public void testFTPClient()	throws Exception {
		//创建一个FTPClient对象
		FTPClient ftpClient = new FTPClient();	
		//创建FTP连接
		ftpClient.connect("192.168.254.129", 21);
		//登录FTP服务器，使用用户名和密码
		ftpClient.login("ftpuser", "ftp12138");
		//上传文件
		//读取本地文件
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\zigzag\\Pictures\\Saved Pictures\\hello.jpg"));
		//设置上传路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//修改上传文件格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//第一个参数：服务器端文件名
		//第二个参数：上传文件的inputStream
		ftpClient.storeFile("hello.jpg", inputStream);
		//关闭连接
		ftpClient.logout();
	}
	
	@Test
	public void testFTPUtil() throws Exception {
		FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\zigzag\\Pictures\\Saved Pictures\\hello.jpg"));
		FtpUtil.uploadFile("192.168.254.131", 21, "ftpuser", "ftp12138", "/home/ftpuser/www/images", "/2019/05/12", "hello.jpg", inputStream);
	}

}
