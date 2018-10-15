package com.servlet.session.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieTestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CookieTestServlet() {
		super();
	}

    public void destroy() {
        super.destroy(); 
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	//获取cookie测试
    	/*Cookie[] cookies = request.getCookies();
    	Cookie cookie1 = cookies[0];*/
    	//响应cookie测试
    	Cookie[] cookies = request.getCookies();
    	Cookie cookie1 = new Cookie("test_cookie11", "test_value11");
    	cookie1.setComment("test_cookie11_Comment");
    	cookie1.setMaxAge(30*60);//30分钟
    	Cookie cookie2 = new Cookie("test_cookie12", "test_value12");
    	cookie2.setMaxAge(30*60);//30分钟
    	response.addCookie(cookie1);
    	response.addCookie(cookie2);
    	
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
        out.println("  <BODY>");
        out.print("hello world !  This is ");
        out.print(this.getClass());
        out.println(", using the GET method!");
        out.println("</BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
        out.println("  <BODY>");
        out.print("  hello world!  This is ");
        out.print(this.getClass());
        out.println(", using the POST method");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

}