Index = function() {
	this.business_id = null;
	this.service_id = null;
	this.status_cd = null;

	this.init = function() {

		// 是否同意退订；默认为“同意”
		// localStorage.setItem("is_refund", "1");
		this.bindMethod();
		//git-featureV1-test
		
	}

	/*
	 * ******************************** 初始化区 *********************************
	 */
	// 设置cookies
	this.setCookie = function(name, value) {
		/*var strcookie = document.cookie;// 获取cookie字符串
		var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
		document.cookie = name + "=" + escape(value) + ";expires="
				+ exp.toGMTString();*/
		
		var strcookie1 = $.cookie('test_cookie4');
		var strcookie2 = $.cookie('test_cookie5');
		
		$.cookie('test_cookie4','test_value4',{
		    expires:7,  
		    path:'/javaWeb_demo/',
		    //domain:'jquery.com',
		    secure:false
		});
		
		$.cookie('test_cookie5','test_value5',{
		    //expires:7,  
		    //path:'/',
		    //domain:'jquery.com',
		    secure:false
		});
		
		var strcookie4 = $.cookie('test_cookie4');
		var strcookie5 = $.cookie('test_cookie5');

		window.location.href = "/javaWeb_demo/cookieTestServlet";

	}

	// 获取cookie
	this.getCookie = function(name) {
		/*var strcookie = document.cookie;// 获取cookie字符串
		var arrcookie = strcookie.split("; ");// 分割
		// 遍历匹配
		for (var i = 0; i < arrcookie.length; i++) {
			var arr = arrcookie[i].split("=");
			if (arr[0] == name) {
				return arr[1];
			}
		}
		return "";*/
		
		var strcookie = document.cookie;
		var strcookie11 = $.cookie('test_cookie11');
		var strcookie12 = $.cookie('test_cookie12');
	}

	// 删除cookie
	this.delCookie = function(name) {
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval = getCookie(name);
		if (cval != null) {
			document.cookie = name + "=" + cval + ";expires="
					+ exp.toGMTString();
		}

	}

	/*
	 * ******************************** 事件函数区 *********************************
	 */

	// 绑定事件
	this.bindMethod = function() {
		//提交
		$("#hollowWorldTest").unbind().on("click",function(){
			index.setCookie("setCookieTestaaa","test0329v1aaa");
			//index.getCookie("aaaTest");
			
		});
	}

	/*
	 * ******************************** 辅助函数区 *********************************
	 */

	// 时间数据格式化
	this.datetimeFormat = function(time) {
		var datetime = new Date();
		datetime.setTime(time);
		var year = datetime.getFullYear();
		var month = datetime.getMonth() + 1 < 10 ? "0"
				+ (datetime.getMonth() + 1) : datetime.getMonth() + 1;
		var date = datetime.getDate() < 10 ? "0" + datetime.getDate()
				: datetime.getDate();
		var hour = datetime.getHours() < 10 ? "0" + datetime.getHours()
				: datetime.getHours();
		var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes()
				: datetime.getMinutes();
		var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds()
				: datetime.getSeconds();
		return year + "-" + month + "-" + date + " " + hour + ":" + minute
				+ ":" + second;
	}

	/*
	 * ******************************** end *********************************
	 */
}

var index = new Index();
$(document).ready(function() {
	// 初始化
	index.init();

});
