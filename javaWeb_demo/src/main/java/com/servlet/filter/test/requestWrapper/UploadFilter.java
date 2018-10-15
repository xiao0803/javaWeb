package com.servlet.filter.test.requestWrapper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class UploadFilter implements Filter {
	long sizeMax = 0;
	int sizeThreshold = 0;
	String repositoryPath = null;
	String headerEncoding = null;

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String type = req.getHeader("Content-Type");

		// 对非"multipart/form-data"类型的请求消息不进行过滤处理
		if (type == null || !type.startsWith("multipart/form-data")) {
			chain.doFilter(request, response);
		} else {
			MultipartRequest mreq = null;
			try {
				mreq = new MultipartRequest(req);
			} catch (Exception e) {
				throw new ServletException(e);
			}
			chain.doFilter(mreq, response);
		}
	}

	@Override
	public void destroy() {
	}
}