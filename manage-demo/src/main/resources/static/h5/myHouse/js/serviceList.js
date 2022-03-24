ServiceList = function(){
	var temp = this;
	//页码
	this.startNum = 0;
	//每页大小
	this.pageSize= 10;
	//
	this.areaId = null;

	this.init=function(){
		temp.areaId = sessionStorage.getItem("area_id");
		temp.initQueryService();
		temp.bindMethod();
	}
	
	//初始化信息
	this.initQueryService=function(){
		$('#dropload').dropload({
	        scrollArea : window,
	        loadDownFn : function(me){
        	var url = '/web-common/common/serviceList'; 
    		var param ={'startNum':temp.startNum,'pageSize':temp.pageSize,'service_name':$("#search_service").val(),'area_id':temp.areaId};
			var successfn = function(data) {
				  var tempHtml = [];
				  var tmpObj = {};
				  var dataList = null;
				  if(data.data){
					  dataList = data.data.dataList;
				  }else{
					  dataList=data.dataList; 
				  }
		          if(null!=dataList&&""!=dataList){
		        	    var lastPinyin = $("h4").last().text();
		        	    var pinyin = -1;
						tempHtml.push('<div class="ts_list">');
						for(var i =0;i<dataList.length;i++){
							tmpObj = dataList[i];
							pinyin2= tmpObj.service_initials;
							if(pinyin != pinyin2 ){
								if("-1"!=pinyin){
									tempHtml.push('</ul>');
								}						
								pinyin=tmpObj.service_initials;
								//防止二次加载时出现相同的拼音抬头
								if(lastPinyin != pinyin2){
									tempHtml.push('<h4  class="pl10">'+processUndefined(tmpObj.service_initials)+'</h4>');
								}
								tempHtml.push('<ul class="bsbx_list pl10 pr10">');
								if(lastPinyin == pinyin2){
									tempHtml.push('<li style="border-top: 1px solid #eee;"><a class="sel_line" serviceName="'+processUndefined(tmpObj.service_name)+'" onclick="serviceList.getServiceVal(this,'+processUndefined(tmpObj.service_id)+')">'+processUndefined(tmpObj.service_name)+'</a></li>');
									lastPinyin = "-9";
								}else{
									tempHtml.push('<li><a class="sel_line" serviceName="'+processUndefined(tmpObj.service_name)+'" onclick="serviceList.getServiceVal(this,'+processUndefined(tmpObj.service_id)+')">'+processUndefined(tmpObj.service_name)+'</a></li>');
								}
								
							}else{
								tempHtml.push('<li><a class="sel_line" serviceName="'+processUndefined(tmpObj.service_name)+'" onclick="serviceList.getServiceVal(this,'+processUndefined(tmpObj.service_id)+')">'+processUndefined(tmpObj.service_name)+'</a></li>'); 
							}
						}
						tempHtml.push('</div>');
						if(dataList.length == serviceList.pageSize){
							serviceList.startNum = serviceList.startNum + 10;
						}else{
							  // 无更多数据
							  me.dataType = "NO_MORE";
				        	  // 锁定
			                  me.lock();
			                  // 无数据
			                  me.noData(true);
			                  /*$(".dropload-load").remove();
			                  $(".dropload-down").remove();*/
						}
						
		          }else{
					  // 无数据
					  me.dataType = "NO";
		        	  // 锁定
	                  me.lock();
	                  // 无数据
	                  me.noData(); 
		          }
		            $("#serviceList").append(tempHtml.join(''));
					me.resetload();
					
			}
			var errorfn = function(e){	
				// 即使加载出错，也得重置
	            //me.resetload();	
				webToast("请求失败！","middle");
			}
			$.JsonAjax(url,param,null,"POST",successfn,errorfn);
			
	      }	   
	   });
	}
	
	//绑定事件
	this.bindMethod = function(){		
		//返回上页面
		$("#back").unbind().on("click",function(){
			//window.location.href = "/web-cust/mobile/proprietor/mine/myHouse/edit-website.html";
			window.history.back();
		});
		//搜索查询
		//$("#search_service").unbind().on("input propertychange",function(){
		$("#search_service").unbind().on("change",function(){
            $(".dropload-load").remove();
            $(".dropload-down").remove();
			temp.startNum = 0;
			$("#serviceList").html('');
			temp.initQueryService();
		});
	}
	
	//获取小区信息，返回给认证页面
	this.getServiceVal = function(obj,serviceId){
		var service_id=serviceId;
		var service_name=$(obj).attr("serviceName");
		sessionStorage.setItem("service_id", service_id);
		sessionStorage.setItem("service_name", service_name);
		window.location.href = "/web-cust/mobile/proprietor/mine/myHouse/edit-website.html";
	}
	
}

var serviceList = null;
$(function(){
	serviceList = new ServiceList();
	serviceList.init();
});




