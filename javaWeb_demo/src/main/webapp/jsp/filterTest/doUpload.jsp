<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="java.util.*,org.apache.commons.fileupload.*"%>
<%@ page import="java.io.*,com.servlet.filter.test.*"%>
�û���<%=request.getParameter("author")%><br>
���ԣ�<%=request.getParameter("company")%><br>
�ϴ��������ļ���
<ul>
	<%
		if (!(request instanceof MultipartRequest)) {
			return;
		}

		//���ñ����ϴ��ļ���Ŀ¼
		String uploadDir = getServletContext().getRealPath("/upload");
		if (uploadDir == null) {
			out.println("�޷����ʴ洢Ŀ¼��");
			return;
		}
		File fUploadDir = new File(uploadDir);
		if (!fUploadDir.exists()) {
			if (!fUploadDir.mkdir()) {
				out.println("�޷������洢Ŀ¼!");
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