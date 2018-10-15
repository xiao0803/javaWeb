package com.servlet.listener.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyListener implements ServletContextListener, HttpSessionListener, ServletRequestListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ServletContext对象被创建了");
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ServletContext对象被销毁了");
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("HttpSession对象被创建了");
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("HttpSession对象被销毁了");
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		System.out.println("servletRequest对象被销毁了");
	}
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		System.out.println("servletRequest对象被创建了");
	}
}