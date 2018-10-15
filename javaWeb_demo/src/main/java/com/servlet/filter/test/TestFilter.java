package com.servlet.filter.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestFilter implements Filter {

	private FilterConfig filterConfig = null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = filterConfig;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		//打印filter配置参数
		String configParamValueTest = this.filterConfig.getInitParameter("configParamValueTest");
		System.out.println("configTest:" + configParamValueTest);
		/*//打印请求消息头信息
		System.out.println("@@@@@@@@@ 打印请求消息头信息开始  @@@@@@@@@");
		Enumeration headerNames = httpServletRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			System.out.println(headerName + ":" + httpServletRequest.getHeader(headerName));
		}
		System.out.println("@@@@@@@@@ 打印请求消息头信息结束  @@@@@@@@@");*/
		
		//前置代码块
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		//out.println("您的IP地址为：" + request.getRemoteHost() + "<br>");
		
		System.out.println("@@@@@@@@@ 前置代码块1  @@@@@@@@@");
		chain.doFilter(request, response);
		System.out.println("@@@@@@@@@ 后置代码块1  @@@@@@@@@");
		
		//后置代码块	
		out.println("<br>名称为encoding的初始化参数值为：" +  configParamValueTest);
		out.println("<br>当前web程序的真实路径为：" + this.filterConfig.getServletContext().getRealPath("/"));
		out.write("<br>名称为encoding的初始化参数值为：");
    	out.flush();
        out.close();
    	
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		this.filterConfig = null;
	}

}