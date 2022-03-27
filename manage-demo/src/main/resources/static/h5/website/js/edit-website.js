EditWebsite = function(){
    this.websiteInfoOperateType = null;
    this.websiteInfoId = null;
	this.init=function(){
        this.websiteInfoOperateType = sessionStorage.getItem("websiteInfoOperateType");
        this.websiteInfoId = sessionStorage.getItem("websiteInfoId");
        $("#itemId").val(this.websiteInfoId);
        // 根据ID查询信息
        if(this.websiteInfoOperateType == 2){ // 编辑操作才需要进行回填
            this.queryWebsiteInfoById(this.websiteInfoId);
        }
		//绑定事件
		this.bindMethod();
	}

    //编辑
    this.queryWebsiteInfoById = function (itemId) {
        var url = '/api/websiteInfo/queryWebsiteInfoById/' + itemId;
        var successfn = function (retResult) {
            var code = retResult.respCode;
            if (code == "00000000") {
                var dataInfo = retResult.data;
                $("#websiteName").val(dataInfo.websiteName);
                $("#websiteUrl").val(dataInfo.websiteUrl);
                $("#loginName").val(dataInfo.loginName);
                $("#loginPwd").val(dataInfo.loginPwd);
                $("#terminalType").val(dataInfo.terminalType);
                $("#category1").val(dataInfo.category1);
                $("#category2").val(dataInfo.category2);
                $("#otherInfo").val(dataInfo.otherInfo);
                $("#remark").val(dataInfo.remark);
            } else {
                webToast("操作失败！", "middle");
            }
        }
        var errorfn = function (e) {
            webToast("请求失败！", "middle");
        }
        $.JsonAjax2(url, null, null, "GET", successfn, errorfn);
    }

    //编辑
    this.editWebsite = function (element) {
        $("#sumBtn").attr("disabled", true);
        var reqMethod = null;
        var url = null;
        if(this.websiteInfoOperateType == 1){
            url = '/api/websiteInfo/addWebsiteInfo';
            reqMethod = "POST";
        } else if(this.websiteInfoOperateType == 2){
            url = '/api/websiteInfo/updateWebsiteInfo/' + $("#itemId").val();
            reqMethod = "PUT";
        } else {
            webToast("websiteInfoOperateType is invalid","middle");
            return;
        }
        var obj = new Object()
        obj["websiteName"] = $("#websiteName").val();
        obj["websiteUrl"] = $("#websiteUrl").val();
        obj["loginName"] = $("#loginName").val();
        obj["loginPwd"] = $("#loginPwd").val();
        obj["terminalType"] = $("#terminalType").val();
        obj["category1"] = $("#category1").val();
        obj["category2"] = $("#category2").val();
        obj["otherInfo"] = $("#otherInfo").val();
        obj["remark"] = $("#remark").val();

        var successfn = function (retResult) {
            var code = retResult.respCode;
            if (code == "00000000") {
                webToast(retResult.respMsg,"middle");
                setTimeout(function(){
                    window.location.href = "/h5/website/website.html";
                },1000);
            } else {
                webToast("操作失败！", "middle");
            }
            $("#sumBtn").attr("disabled", false);
        }
        var errorfn = function (e) {
            webToast("请求失败！", "middle");
            $("#sumBtn").attr("disabled", false);
        }
        $.JsonAjax2(url, JSON.stringify(obj), null, reqMethod, successfn, errorfn);
    }

	/*************************************   输入保存start   **************************************/

	//保存有效参数到Cookie
	/*this.saveParamsToSession = function(){
		var relType = $("#relType").val();
		var contact = $("#contact").val();
	    sessionStorage.setItem("identPage_relType", relType);
	    sessionStorage.setItem("identPage_contact", contact);
	}

	this.setParamsFromSession = function() {
		var relType = processUndefined(sessionStorage.getItem("identPage_relType"))
		if(relType != null && relType != ""){
			$("#relType").val(relType);
		}
		var contact = processUndefined(sessionStorage.getItem("identPage_contact"))
		if(contact!= null && contact != ""){
			$("#contact").val(contact);
		}

	}*/
	/*************************************   输入保存end   **************************************/

	//绑定事件
	this.bindMethod=function(){
		//返回上页
		$("#back").unbind().on("click",function(){
            window.location.href = "/h5/website/website.html";
		});

		// 提交
		$("#sumBtn").unbind().on("click",function(){
			editWebsite.editWebsite();
		});

	}
	
}

var editWebsite;
$(function(){	
	editWebsite = new EditWebsite();
	editWebsite.init();
});