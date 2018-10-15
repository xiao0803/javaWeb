package com.servlet.filter.test.responseWrapper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class CompressionResponseStream extends ServletOutputStream {
	protected int compressionThreshold = 0; // 是否启用压缩的临界值
	protected byte[] buffer = null; // 临时容纳写入的数据的缓冲区
	protected int bufferCount = 0; // 缓冲区中实际写如的数据量
	protected GZIPOutputStream gzipstream = null;
	protected boolean closed = false; // 当前流对象是否处于关闭状态
	protected int length = -1;
	protected HttpServletResponse response = null;
	protected ServletOutputStream output = null;

	public CompressionResponseStream(HttpServletResponse response) throws IOException {
		super();
		closed = false;
		this.response = response;
		this.output = response.getOutputStream();
	}

	// 设置是否启用压缩的临界值
	protected void setBuffer(int threshold) {
		compressionThreshold = threshold;
		buffer = new byte[compressionThreshold];
	}

	public void close() throws IOException {
		if (closed)
			throw new IOException("This output stream has already been closed");

		/*
		 * 根据gzipstream是否为null，就可以知道写入的内容是否达到了 启用压缩的临界值，因为达到临界值时，write方法会自动创建
		 * gzipstream实例对象。
		 */
		if (gzipstream != null) {
			flushToGZip();
			gzipstream.close();
			gzipstream = null;
		} else {
			if (bufferCount > 0) {
				output.write(buffer, 0, bufferCount);
				bufferCount = 0;
			}
		}

		/*
		 * 笔者认为这句代码有些问题，如果启用了压缩，在关闭gzipstream流时， 底层的output流应该自动关闭，所以，
		 * 应该将下句代码放进上面的else从语中
		 */
		output.close();
		closed = true;
	}

	public void flush() throws IOException {
		if (closed) {
			throw new IOException("Cannot flush a closed output stream");
		}
		if (gzipstream != null) {
			gzipstream.flush();
		}

	}

	// 将buffer缓冲区中的数据写入到gzipstream对象中
	public void flushToGZip() throws IOException {
		if (bufferCount > 0) {
			writeToGZip(buffer, 0, bufferCount);
			bufferCount = 0;
		}
	}

	public void write(int b) throws IOException {
		if (closed)
			throw new IOException("Cannot write to a closed output stream");

		/*
		 * 当已经写入的数据达到了启用压缩的临界值时， 则先将buffer缓冲区中的数据写入到gzipstream对象中
		 */
		if (bufferCount >= buffer.length) {
			flushToGZip();
		}

		buffer[bufferCount++] = (byte) b;
	}

	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	public void write(byte b[], int off, int len) throws IOException {
		if (closed)
			throw new IOException("Cannot write to a closed output stream");
		if (len == 0)
			return;

		/*
		 * 如果buffer缓冲区中剩余的空间能够容纳要写入的数据， 则先将这些数据写入到buffer缓冲区中
		 */
		if (len <= (buffer.length - bufferCount)) {
			System.arraycopy(b, off, buffer, bufferCount, len);
			bufferCount += len;
			return;
		}

		/*
		 * 如果buffer缓冲区中剩余的空间不能够容纳要写入的数据， 则先将buffer缓冲区中原有的数据写入到gzipstream对象中
		 */
		flushToGZip();

		/*
		 * 腾空buffer缓冲区中原有的数据后，判断整个buffer缓冲区是否能够 容纳要写入的数据，如果能，将这些数据写入到buffer缓冲区中
		 */
		if (len <= (buffer.length - bufferCount)) {
			System.arraycopy(b, off, buffer, bufferCount, len);
			bufferCount += len;
			return;
		}

		/*
		 * 如果整个buffer缓冲区的空间都不能够容纳要写入的数据， 则直接将这些数据写入到gzipstream对象中
		 */
		writeToGZip(b, off, len);
	}

	/*
	 * 向gzipstream对象中写入数据，如果是第一次写入， 则还需要首先创建出gzipstream实例对象
	 */
	public void writeToGZip(byte b[], int off, int len) throws IOException {
		if (gzipstream == null) {
			response.addHeader("Content-Encoding", "gzip");
			gzipstream = new GZIPOutputStream(output);
		}
		gzipstream.write(b, off, len);
	}

	/*
	 * ServletOutputStream类与OutputStream类中都没有定义closed这个方法，
	 * 不知这里为何实现这一个方法，笔者看来纯属多余
	 */
	public boolean closed() {
		return (this.closed);
	}
}