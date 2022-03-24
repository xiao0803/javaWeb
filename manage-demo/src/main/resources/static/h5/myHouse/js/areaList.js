AreaList = function(){
	var temp = this;
	//页码
	this.startNum = 0;
	//每页大小
	this.pageSize = 10;
	this.init=function(){
		temp.initQueryData();
		//temp.initQueryArea();
		temp.bindMethod();
	}
	
	
	this.initQueryData = function(){
		$('#dropload').dropload({
	        scrollArea : window,
	        //向下加载
	        loadDownFn : function(me){
	        	areaList.initQueryArea(me)
	        }
		 });
	}
	
	//初始化信息
	this.initQueryArea = function(me){
        var url = '/web-common/common/areaList';
		var param ={'startNum':temp.startNum,'pageSize':temp.pageSize,'area_name':$("#search_area").val()};
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
	        	    var pinyin = -1;
	  				tempHtml.push('<div class="ts_list">');
	  				for(var i =0;i<dataList.length;i++){
	  					tmpObj = dataList[i];
	  					pinyin2= tmpObj.area_initial;
	  					if(pinyin!=pinyin2){
	  						if("-1"!=pinyin){
	  							tempHtml.push('</ul>');
	  						}						
	  						pinyin=tmpObj.area_initial;
	  						tempHtml.push('<h4  class="pl10 city_index" data-ch="'+tmpObj.area_initial+'">'+tmpObj.area_initial+'</h4>');
	  						tempHtml.push('<ul class="bsbx_list pl10 pr10">');
	  						tempHtml.push('<li><a class="sel_line" areaName="'+tmpObj.area_name+'" onclick="getAreaVal(this,'+tmpObj.area_id+')">'+tmpObj.area_name+'</a></li>');
	  					}else{
	  						tempHtml.push('<li><a class="sel_line" areaName="'+tmpObj.area_name+'" onclick="getAreaVal(this,'+tmpObj.area_id+')">'+tmpObj.area_name+'</a></li>'); 
	  					}
	  				}
	  				tempHtml.push('</div>');
	  				
					//当最后查询条数不足设置页面显示的条数时，无需再加载
					if(dataList.length == areaList.pageSize){
						areaList.startNum = areaList.startNum + 10;
					}else{
						  // 无数据
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
                  me.noData(true);
	          }
				$("#infoList").append(tempHtml.join(''));
				app.main();
				me.resetload();
		}
		var errorfn = function(e){	
			// 即使加载出错，也得重置
			webToast("请求失败！","middle");
		}
		$.JsonAjax(url,param,null,"POST",successfn,errorfn);
	}
	
	
	//绑定事件
	this.bindMethod=function(){
		//返回上页面
		$("#back").unbind().on("click",function(){
			//window.location.href = "/web-cust/mobile/proprietor/mine/myHouse/edit-website.html";
			window.history.back();
		});
		//搜索查询
		//$("#search_area").unbind().on("input propertychange",function(){
		$("#search_area").unbind().on("change",function(){
            $(".dropload-load").remove();
            $(".dropload-down").remove();
			areaList.startNum = 0;
			$("#infoList").html('');
			areaList.initQueryData();
		});
	}
	
}

function getAreaVal(obj,areaId){
	var area_id=areaId;
	var area_name=$(obj).attr("areaName");
	sessionStorage.setItem("area_id", area_id);
	sessionStorage.setItem("area_name", area_name);
	window.location.href = "/web-cust/mobile/proprietor/mine/myHouse/edit-website.html";
}
var areaList = null;

var app = app || {};
app.ItemList = function () {
	var elItemList = document.querySelector('#dropload');
	return {
		gotoChar: function (ch) {
			if (ch === '#') {
				//elItemList.scrollTop = elItemList.scrollHeight;
				//elItemList.scrollTop = 0;
				document.querySelector("#search").scrollIntoView(false);  
			} else {
				target = elItemList.querySelector('[data-ch="' + ch + '"]');
				if (target) {
					target.scrollIntoView(true);
				}
			}
		}
	}
};
app.main = function () {
	var itemList = app.ItemList();
	var chars = "#";
	$(".city_index").each(function(){
		chars += $(this).html();
	});
	$(".current-char").parent().remove();
	new IndexSidebar({
		chars:chars
	}).on('charChange', itemList.gotoChar);
};
$(function(){
	areaList = new AreaList();
	areaList.init();
});