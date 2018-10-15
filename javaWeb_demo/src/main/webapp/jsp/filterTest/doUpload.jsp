<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="java.util.*,org.apache.commons.fileupload.*"%>
<%@ page import="java.io.*,com.servlet.filter.test.*"%>
用户：<%=request.getParameter("author")%><br>
来自：<%=request.getParameter("company")%><br>
上传了如下文件：
<ul>
	<%
		if (!(request instanceof MultipartRequest)) {
			return;
		}

		//设置保存上传文件的目录
		String uploadDir = getServletContext().getRealPath("/upload");
		if (uploadDir == null) {
			out.println("无法访问存储目录！");
			return;
		}
		File fUploadDir = new File(uploadDir);
		if (!fUploadDir.exists()) {
			if (!fUploadDir.mkdir()) {
				out.println("无法创建存储目录!");
				return;
			}
		}

		MultipartRequest mreq = null;
		mreq = (MultipartRequest) request;
		Enumeration fileItemNames = mreq.getFileItemNames();
		while (fileItemNames.hasMoreElements()) {
			String name = (String) fileItemNames.nextElement();
			FileItem fi = mreq.getFileItem(name);
			String pathSrc = fi.getName();
			int start = pathSrc.lastIndexOf('\\');
			String fileName = pathSrc.substring(start + 1);
			File pathDest = new File(uploadDir, fileName);
			fi.write(pathDest);
			out.println("<li>" + fileName + "</li>");
		}
	%>
</ul>