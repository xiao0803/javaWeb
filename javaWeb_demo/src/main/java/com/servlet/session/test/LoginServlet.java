package com.servlet.session.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

    public void destroy() {
        super.destroy(); 
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session != null) {
			System.out.println("打印sessionID：" + session.getId());
		} else {
			session = request.getSession();
			System.out.println("session为空，创建一个session。sessionID：" + session.getId());
		}
    	session.setAttribute("user_name", request.getParameter("user_name"));
    	session.setAttribute("user_password", request.getParameter("user_password"));
    	response.sendRedirect("/javaWeb_demo/html/sessionTest/urlTest.html");
    	//session.setMaxInactiveInterval(1);//一秒钟失效
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

}