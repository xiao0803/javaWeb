package com.servlet.listener.test;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class MyAttributeListener implements ServletContextAttributeListener, HttpSessionAttributeListener, ServletRequestAttributeListener {
	/********** 环境域对象的属性监听start **********/
	@Override
	public void attributeAdded(ServletContextAttributeEvent scae) {
		System.out.println("servletContext对象中增加了一个名为" + scae.getName() + "的属性,该属性值为" + scae.getValue());
	}
	@Override
	public void attributeRemoved(ServletContextAttributeEvent scae) {
		System.out.println("servletContext对象中的" + scae.getName() + "属性被删除了");
	}
	@Override
	public void attributeReplaced(ServletContextAttributeEvent scae) {
		System.out.println("servletContext对象中" + scae.getName() + "的属性值被替换成了" + scae.getValue());
	}
	/********** 环境域对象的属性监听end **********/
	
	/********** 用户会话域对象的属性监听start **********/
	@Override
	public void attributeAdded(HttpSessionBindingEvent hbe) {
		System.out.println("httpSession对象中增加了一个名为" + hbe.getName() + "的属性，该属性值为" + hbe.getValue());
	}
	@Override
	public void attributeRemoved(HttpSessionBindingEvent hbe) {
		System.out.println("httpSession对象中的" + hbe.getName() + "属性被删除了");
	}
	@Override
	public void attributeReplaced(HttpSessionBindingEvent hbe) {
		System.out.println("httpSession对象中" + hbe.getName() + "的属性值被替换成了" + hbe.getValue());
	}
	/********** 用户会话域对象的属性监听end **********/
	
	/********** 请求域对象的属性监听start **********/
	@Override
	public void attributeAdded(ServletRequestAttributeEvent srae) {
		System.out.println("servletRequest对象中增加了一个名为" + srae.getName() + "的属性,该属性值为" + srae.getValue());
	}
	@Override
	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		System.out.println("servletRequest对象中的" + srae.getName() + "属性被删除了");
	}
	@Override
	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		System.out.println("servletRequest对象中" + srae.getName() + "的属性值被替换成了" + srae.getValue());
	}
	/********** 请求域对象的属性监听start **********/
}