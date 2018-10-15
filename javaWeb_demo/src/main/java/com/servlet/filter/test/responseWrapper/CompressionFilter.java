package com.servlet.filter.test.responseWrapper;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompressionFilter implements Filter {
	private FilterConfig config = null;
	private int minThreshold = 128;
	protected int compressionThreshold;

	public void init(FilterConfig filterConfig) {
		config = filterConfig;
		if (filterConfig != null) {
			String str = filterConfig.getInitParameter("compressionThreshold");
			if (str != null) {
				compressionThreshold = Integer.parseInt(str);
				if (compressionThreshold != 0 && compressionThreshold < minThreshold) {
					compressionThreshold = minThreshold;
				}
			} else {
				// 不对响应消息的实体内容进行压缩
				compressionThreshold = 0;
			}
		} else {
			compressionThreshold = 0;
		}

	}

	public void destroy() {
		this.config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// 不对响应消息的实体内容进行压缩
		if (compressionThreshold == 0) {
			chain.doFilter(request, response);
			return;
		}

		// 检测客户端浏览器是否支持gzip数据压缩格式
		boolean supportCompression = false;
		if (request instanceof HttpServletRequest) {
			Enumeration e = ((HttpServletRequest) request).getHeaders("Accept-Encoding");
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				if (name.indexOf("gzip") != -1) {
					supportCompression = true;
				}
			}
		}

		if (!supportCompression) {
			chain.doFilter(request, response);
			return;
		} else {
			if (response instanceof HttpServletResponse) {
				// 创建自定义的response对象
				CompressionServletResponseWrapper wrappedResponse = new CompressionServletResponseWrapper((HttpServletResponse) response);
				wrappedResponse.setCompressionThreshold(compressionThreshold);

				try {
					chain.doFilter(request, wrappedResponse);
				} finally {
					wrappedResponse.finishResponse();
				}
				return;
			}
		}
	}
}