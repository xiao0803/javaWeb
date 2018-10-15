CookieTest = function() {
	this.business_id = null;
	this.service_id = null;
	this.status_cd = null;

	this.init = function() {

		// �Ƿ�ͬ���˶���Ĭ��Ϊ��ͬ�⡱
		// localStorage.setItem("is_refund", "1");
		this.bindMethod();
	}

	/*
	 * ******************************** ��ʼ���� *********************************
	 */
	// ����cookies
	this.setCookie = function(name, value) {
		/*var strcookie = document.cookie;// ��ȡcookie�ַ���
		var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
		document.cookie = name + "=" + escape(value) + ";expires="
				+ exp.toGMTString();*/
		
		var strcookie1 = $.cookie('test_cookie4');
		var strcookie2 = $.cookie('test_cookie5');
		
		$.cookie('test_cookie4','test_value4',{
		    expires:7,  
		    path:'/javaWeb_demo/cookieTest.html',
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

	// ��ȡcookie
	this.getCookie = function(name) {
		/*var strcookie = document.cookie;// ��ȡcookie�ַ���
		var arrcookie = strcookie.split("; ");// �ָ�
		// ����ƥ��
		for (var i = 0; i < arrcookie.length; i++) {
			var arr = arrcookie[i].split("=");
			if (arr[0] == name) {
				return arr[1];
			}
		}
		return "";*/
		
		var strcookie = document.cookie;
		var strcookie13 = $.cookie('test_filter_cookie11');
		var strcookie11 = $.cookie('test_cookie11');
		var strcookie12 = $.cookie('test_cookie12');
	}

	// ɾ��cookie
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
	 * ******************************** �¼������� *********************************
	 */

	// ���¼�
	this.bindMethod = function() {
		//�ύ
		$("#hollowWorldTest").unbind().on("click",function(){
			//cookieTest.setCookie("setCookieTestaaa","test0329v1aaa");
			cookieTest.getCookie("aaaTest");
			
		});
	}

	/*
	 * ******************************** ���������� *********************************
	 */

	// ʱ�����ݸ�ʽ��
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

var cookieTest = new CookieTest();
$(document).ready(function() {
	// ��ʼ��
	cookieTest.init();

});
