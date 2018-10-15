package com.servlet.filter.test.requestWrapper;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

public class MultipartRequest extends HttpServletRequestWrapper {
	HashMap parameters = new HashMap();
	HashMap files = new HashMap();

	public MultipartRequest(HttpServletRequest request) throws FileUploadException {
		super(request);
		DiskFileUpload fu = new DiskFileUpload();
		// 最多上传200M数据
		fu.setSizeMax(1024 * 1024 * 200);
		// 超过1M的字段数据采用临时文件缓存
		fu.setSizeThreshold(1024 * 1024);
		// 采用默认的临时文件存储位置
		// fu.setRepositoryPath(...);
		// 设置上传的普通字段内容和文件字段的文件名所采用的字符集编码
		fu.setHeaderEncoding("gb2312");

		// 得到所有表单字段对象的集合
		List fileItems = null;
		// 如果解析数据时出现问题，直接将FileUploadException外抛
		fileItems = fu.parseRequest(request);

		// 处理每个表单字段
		Iterator i = fileItems.iterator();
		while (i.hasNext()) {
			FileItem fi = (FileItem) i.next();
			if (fi.isFormField()) {
				String fieldName = fi.getFieldName();
				String content = null;
				try {
					content = fi.getString("GB2312");
				} catch (Exception e) {
				}
				setParameter(fieldName, content);
			} else {
				String pathSrc = fi.getName();
				/*
				 * 如果用户没有在FORM表单的文件字段中选择任何文件， 那么忽略对该字段项的处理
				 */
				if (pathSrc.trim().equals("")) {
					continue;
				}

				String fieldName = fi.getFieldName();
				files.put(fieldName, fi);
			}
		}
	}

	/**自定义方法
	 * 向集合中添加一个参数，主要是考虑多个同名字段的情况。
	 */
	private void setParameter(String name, String value) {
		String[] mValue = (String[]) parameters.get(name);
		if (mValue == null) {
			mValue = new String[0];
		}
		String[] newValue = new String[mValue.length + 1];
		System.arraycopy(mValue, 0, newValue, 0, mValue.length);
		newValue[mValue.length] = value;
		parameters.put(name, newValue);
	}

	/**覆盖方法
	 * 返回某个名称的参数值，如果一个名称对应有多个值， 那么只返回其中的第一个。
	 */
	@Override
	public String getParameter(String name) {
		String[] mValue = (String[]) parameters.get(name);
		if ((mValue != null) && (mValue.length > 0)) {
			return mValue[0];
		}

		return null;
	}

	/**覆盖方法
	 * 返回所有参数名称的集合
	 */
	@Override
	public Enumeration /* String */getParameterNames() {
		Collection c = parameters.keySet();
		return Collections.enumeration(c);
	}

	/**覆盖方法
	 * 返回某个名称所对应的所有参数值
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] mValue = (String[]) parameters.get(name);
		return mValue;
	}

	/**覆盖方法
	 * 返回包含所有参数名和对应的参数值的Map集合
	 */
	@Override
	public Map getParameterMap() {
		return parameters;
	}

	/**自定义方法
	 * 返回某个表单字段名所对应的FileItem对象
	 */
	public FileItem getFileItem(String name) {
		FileItem fItem = (FileItem) files.get(name);
		return fItem;
	}

	/**自定义方法
	 * 返回所有上传了文件的文件字段的名称的集合
	 */
	public Enumeration /* String */getFileItemNames() {
		Collection c = files.keySet();
		return Collections.enumeration(c);
	}
}