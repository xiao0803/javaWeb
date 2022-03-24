//document.write('<script type="text/javascript" src="/web-cust/mobile/js/dsBridge.js"></script>');
// 页面监控方法 Piwik 的全局变量
var _paq = _paq || [];

$(function(){	
	//获取URL参数
	$.getUrlParam = function (name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) return unescape(r[2]); return null;
	};

	//获取中文参数
	$.getUrlParamCN = function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return decodeURI(unescape(r[2])); return null;
	};
	//设置中文参数
	$.setUrlParamCN = function(nameCN){
		return encodeURI(encodeURI(nameCN));
	};
	
	/*============  页面监控方法 Piwik begin =============*/
	/*tracker methods like "setCustomDimension" should be called before "trackPageView" */
	_paq.push(['trackPageView']);
	_paq.push(['enableLinkTracking']);
	var u="//piwik.cohl.com/";
	_paq.push(['setTrackerUrl', u+'piwik.php']);
	_paq.push(['setSiteId', '5']);
	var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];
	g.type='text/javascript'; 
	g.async=true; 
	g.defer=true; 
	g.src=u+'piwik.js'; 
	s.parentNode.insertBefore(g,s);
	/*============  页面监控方法 Piwik end =============*/
});

/**
 * 提示文本框还能输入多少字符
 * @param num_id
 * @param num
 * @param text_id
 * @returns
 */
function handle(num_id,num,text_id){ 
	var text_lenght=document.getElementById(text_id).value.length;
	if(text_lenght>num){
		document.getElementById(num_id).innerHTML=0;
		var area_val = document.getElementById(text_id).value.substring(0, num); 
		document.getElementById(text_id).value=area_val;
	}else{
		document.getElementById(num_id).innerHTML=num-text_lenght;
	}
      
}  

/**
 * 判断data是不为undefined、null
 * @param data 需验证的数据
 * @returns {Boolean}
 */
function isNull(data){
	return undefined!=data&&null!=data;
}
/**
 * 过滤空字符
 * @param tempData
 * @returns
 */
function processUndefined(tempData) {
	return tempData == undefined ? '' : tempData; 
}

//日期加多少分钟
function judgFailTime(dateTime,minute) {
    var x = dateTime; // 取得的TextBox中的时间
    var time = new Date(x.replace("-","/"));           
    var b = minute; //分钟数
    time.setMinutes(time.getMinutes() + b, time.getSeconds(), 0);
    return time.Format("yyyy-MM-dd hh:mm:ss");
}

/*日期转化工具*/
DateUtil = function(){
	/**
	* 字符串转换为日期对象
	* @param date Date 格式为yyyy-MM-dd HH:mm:ss，必须按年月日时分秒的顺序，中间分隔符不限制
	*/
	this.strToDate = function(dateStr){
		var data = dateStr;  
		var reCat = /(\d{1,4})/gm;   
		var t = data.match(reCat);
		t[1] = t[1] - 1;
		eval('var d = new Date('+t.join(',')+');');
		return d;
	}
	
	/**
	* 比较日期差 dtEnd 格式为日期型或者有效日期格式字符串
	* @param strInterval string  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒  
	* @param dtStart Date  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	* @param dtEnd Date  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒 
	*/
	this.dateDiff = function(strInterval, dtStart, dtEnd) {   
		switch (strInterval) {   
			case 's' :return parseInt((dtEnd - dtStart) / 1000);  
			case 'n' :return parseInt((dtEnd - dtStart) / 60000);  
			case 'h' :return parseInt((dtEnd - dtStart) / 3600000);  
			case 'd' :return parseInt((dtEnd - dtStart) / 86400000);  
			case 'w' :return parseInt((dtEnd - dtStart) / (86400000 * 7));  
			case 'm' :return (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);  
			case 'y' :return dtEnd.getFullYear() - dtStart.getFullYear();  
		}  
	}
}

/**
 * 转化时间为描述，如'昨天'、'3小时前'
 * @param time 时间  格式为 2015/11/16 17:00 
 */
function convertDateTime(dateTime){
	/*var date = dateTime.substring(0,dateTime.indexOf(' '));
	var time = dateTime.substring(dateTime.indexOf(' ')+1);
	var dateArr = date.split('/');
	var now = new Date();*/
	var dateUtil = new DateUtil();
	var now = new Date();
	var date = dateUtil.strToDate(dateTime);
	var result = "";
	if(dateUtil.dateDiff('s',date,now)<=0){
		result = "刚刚";
	} else if(dateUtil.dateDiff('s',date,now)<60){
		result = dateUtil.dateDiff('s',date,now)+"秒前";
	}else if(dateUtil.dateDiff('n',date,now)<60){
		result = dateUtil.dateDiff('n',date,now)+"分前";
	}else if(dateUtil.dateDiff('h',date,now)<24){
		result = dateUtil.dateDiff('h',date,now)+"小时前";
	}else if(dateUtil.dateDiff('d',date,now)<7){
		result = dateUtil.dateDiff('d',date,now)+"天前";
	}else if(dateUtil.dateDiff('w',date,now)<4){
		result = dateUtil.dateDiff('w',date,now)+"周前";
	}else if(dateUtil.dateDiff('m',date,now)<12){
		result = dateUtil.dateDiff('m',date,now)+"月前";
	}else if(dateUtil.dateDiff('y',date,now)>0){
		result = dateUtil.dateDiff('y',date,now)+"年前";
	}
	return result;
}


//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
}

//两个时间字符串相减得到天数  格式yyyy-MM-dd
function dateDiff(date1, date2) {
    var type1 = typeof date1, type2 = typeof date2;
    if (type1 == 'string')
        date1 = stringToTime(date1);
    else if (date1.getTime)
        date1 = date1.getTime();
    if (type2 == 'string')
        date2 = stringToTime(date2);
    else if (date2.getTime)
        date2 = date2.getTime();
    //alert((date1 - date2) / (1000*60*60)); 
    return (date1 - date2) / (1000 * 60 * 60 * 24); //结果是小时 
}
//字符串转成Time(dateDiff)所需方法 
function stringToTime(string) {
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
   parseInt(d[0], 10) || null,
   (parseInt(d[1], 10) || 1) - 1,
    parseInt(d[2], 10) || null,
    parseInt(t[0], 10) || null,
    parseInt(t[1], 10) || null,
    parseInt(t[2], 10) || null
    )).getTime();
}



/**
 * ajax封装
 * url 发送请求的地址
 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
 * async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。
 *       注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
 * type 请求方式("POST" 或 "GET")， 默认为 "GET"
 * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text,此处只允许json
 * successfn 成功回调函数
 * errorfn 失败回调函数
 */
jQuery.JsonAjax=function(url, data, async, type,successfn, errorfn)
{
	if (async!==false){
		async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
	}	
	type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
	//data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
	$.ajax({
	    type: type,
	    async: async,
		data:data,
	    url: url,
	    contentType : "application/x-www-form-urlencoded; charset=utf-8",
	    dataType: "json",
	    success: function(d){
	        successfn(d);
	    },
	    error: function(e){
	        errorfn(e);
	    }
	});
};

jQuery.JsonAjax2=function(url, data, async, type,successfn, errorfn)
{
	if (async!==false){
		async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
	}	
	type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
	//data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
	$.ajax({
		type: type,
		async: async,
		data:data,
		url: url,
		contentType:"application/json; charset=utf-8",
//	    contentType : "application/x-www-form-urlencoded; charset=utf-8",
	    dataType: "json",
//	    dataType: "text",
		success: function(d){
			successfn(d);
		},
		error: function(e){
			errorfn(e);
		}
	});
};

function validationNumber(e) {
    var regu = /^[0-9]+\.?[0-9]*$/;
    var value=$(e).val();
    if (value != "") {
      if (!regu.test(value)) {
        alert("请输入正确的数字");
        $(e).val("");
        $(e).focus();
      }
    }
  }
function checkMoney(e) {
    var regu = /^[0-9]+\.?[0-9]*$/;
    var value=$(e).val();
    if (value != "") {
      if (!regu.test(value)) {
        alert("请输入正确的数字");
        $(e).val("");
        $(e).focus();
        return 1;
      }else{
    	  return 0;
      }
    }else{
    	return 0;
    }
  }

var IS_FOCUS = false;
//把页面滚动到底部
function scrollBottom(divId) {
	$(divId).focus(function(){
		IS_FOCUS = true;
	    window.setTimeout(function(){
	    	window.scrollTo(0, $(document).height());
	    }, 500);
	});
	
	$(divId).blur(function(){
		IS_FOCUS = false;
	});
}

// 缓存赋值
function setCacheValue(key, value){
	//window.sessionStorage.setItem(key, value);
	localStorage.setItem(key, value);
}

// 缓存取值
function getCacheValue(key){
	//return window.sessionStorage.getItem(key);
	return localStorage.getItem(key);
}

//删除缓存
function removeCacheValue(key){
	localStorage.removeItem(key);
}

// 判断是否连接网络
function isOnLine(){
	var onLine = false;
	if(window.navigator.onLine == true) {　
　　　　//alert("已连接");
		onLine = true; 
　　}
	return onLine;
}

function autoUploadImg(){
	var beginParam ={"uploadFileName": "repairSave_begin_uploadFileName","servicePath": "repairSave_begin_servicePath"};
	bridge.call("autoUploadfileForHtml", beginParam);
}

function autoAddData(key, cacheValue){
	var newTokey = bridge.call("getToken", {});
	var repairSaveBeginDataObj = JSON.parse(cacheValue);
	repairSaveBeginDataObj['tokenObj'] = tokenObj;
	var successfn = function(data){
        var stat_cd=data.status;
        var msg=data.msg;
        // 清除缓存
        removeCacheValue(key);
	}
	var errorfn = function(e){
	}
	$.JsonAjax(repairSaveBeginDataObj.url,repairSaveBeginDataObj,null,"POST",successfn,errorfn);
}

/**
 * 初始化可输入字数
 */
function initLimitCom(textObj,backCount,step){
	var curCount=250;
	if('yzbs'==step){
		curCount=250;
	}else if('yzts'==step){
		curCount=250;
	}else if('ygbs'==step){ //员工报事报修
		curCount=250;
	}else if('note'==step){
		curCount=150;
	}
	$(textObj).attr("maxlength",curCount);	
	var length= $(textObj).val().length;
	if(length>0){
		var Count=curCount-length;
		$("#"+backCount).text(Count);
	}else{
		$("#"+backCount).text(curCount);
	}	
}

/**
 * 限制文本域文字数量
 */
function textLimitCom(textObj,backCount,step,flag){
	var maxLength=250;
	if('yzbs'==step){ //业主报事报修
		maxLength=250;
	}else if('yzts'==step){ //业主投诉
		maxLength=250;
	}else if('ygbs'==step){ //员工报事报修
		maxLength=250;
	}else if('note'==step){
		maxLength=150;
	}
	/*回写span的值，当前填写文字的数量*/ 
	if ($(textObj).val().length > maxLength){ 
		backCount.innerText=0;
		var area_val = $(textObj).val().substring(0, maxLength); 
		$(textObj).val(area_val);
		$(textObj).focus();
	}
	var curCount= maxLength - $(textObj).val().length;
	if('2'==flag){
		$("#"+backCount).text(curCount);
	}else{
		backCount.innerText=curCount;
	}	
}
