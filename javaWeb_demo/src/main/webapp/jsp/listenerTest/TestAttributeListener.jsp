﻿<html>
  <head>
    <title>This is test page</title>
  </head>
  	<body>
		<H4>这是一个测试对象属性信息监听器的页面</H4>
 		<%
 			getServletContext().setAttribute("username","it315");
 			getServletContext().setAttribute("username","zxx");
 			getServletContext().removeAttribute("username");
 			session.setAttribute("username","it315");
 			session.setAttribute("username","zxx");
 			session.removeAttribute("username");
 			request.setAttribute("username","it315");
 			request.setAttribute("username","zxx");
 			request.removeAttribute("username");
 		%>  
  	</body>
</html>