package com.servlet.listener.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyListener implements ServletContextListener, HttpSessionListener, ServletRequestListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		System.out.println("ServletContext对象被创建了");
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		System.out.println("ServletContext对象被销毁了");
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession httpSession = se.getSession();
		System.out.println("HttpSession对象被创建了");
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession httpSession = se.getSession();
		System.out.println("HttpSession对象被销毁了");
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		ServletRequest servletRequest = sre.getServletRequest();
		System.out.println("servletRequest对象被销毁了");
	}
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		ServletRequest servletRequest = sre.getServletRequest();
		System.out.println("servletRequest对象被创建了");
	}
}