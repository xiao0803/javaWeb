package com.servlet.filter.test.responseWrapper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CompressionServletResponseWrapper extends HttpServletResponseWrapper {

	protected HttpServletResponse origResponse = null;
	protected static final String info = "CompressionServletResponseWrapper";
	protected ServletOutputStream stream = null;
	protected PrintWriter writer = null;
	protected int threshold = 0;
	protected String contentType = null;

	public CompressionServletResponseWrapper(HttpServletResponse response) {
		super(response);
		origResponse = response;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
		origResponse.setContentType(contentType);
	}

	// 设置是否启用压缩的临界值
	public void setCompressionThreshold(int threshold) {
		this.threshold = threshold;
	}

	// 创建这个自定义response对象包含的输出流对象
	public ServletOutputStream createOutputStream() throws IOException {
		CompressionResponseStream stream = new CompressionResponseStream(origResponse);
		stream.setBuffer(threshold);
		return stream;
	}

	/*
	 * 为了防止目标Servlet没有关闭输出流对象，Filter程序必须调用这个方法， 以便自动帮助拥护关闭输出流对象
	 */
	public void finishResponse() {
		try {
			if (writer != null) {
				writer.close();
			} else {
				if (stream != null)
					stream.close();
			}
		} catch (IOException e) {
		}
	}

	// 覆盖flushBuffer方法
	public void flushBuffer() throws IOException {
		((CompressionResponseStream) stream).flush();
	}

	// 覆盖getOutputStream方法
	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null)
			throw new IllegalStateException("getWriter() has already been called for this response");

		if (stream == null)
			stream = createOutputStream();

		return (stream);
	}

	// 覆盖getWriter方法
	public PrintWriter getWriter() throws IOException {
		if (writer != null)
			return (writer);

		if (stream != null)
			throw new IllegalStateException("getOutputStream() has already been called for this response");

		stream = createOutputStream();
		// String charset = getCharsetFromContentType(contentType);
		String charEnc = origResponse.getCharacterEncoding();
		if (charEnc != null) {
			writer = new PrintWriter(new OutputStreamWriter(stream, charEnc));
		} else {
			writer = new PrintWriter(stream);
		}

		return (writer);
	}

	// 最终的数据要被压缩，外界调用setContentLength方法不应该起作用
	public void setContentLength(int length) {
	}

	// 从Content-Type头字段中提取当前的字符集编码信息
	private static String getCharsetFromContentType(String type) {
		if (type == null) {
			return null;
		}
		int semi = type.indexOf(";");
		if (semi == -1) {
			return null;
		}
		String afterSemi = type.substring(semi + 1);
		int charsetLocation = afterSemi.indexOf("charset=");
		if (charsetLocation == -1) {
			return null;
		} else {
			String afterCharset = afterSemi.substring(charsetLocation + 8);
			String encoding = afterCharset.trim();
			return encoding;
		}
	}

}