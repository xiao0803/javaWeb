MyHouse = function () {
    //mobile接口对象
    this.bridge = null;
    //当前服务中心ID
    this.service_id = null;
    //登录认证信息;
    this.tokenObj = null;

    //登录用户信息
    this.cache_cust_info = null;

    //页码
    this.startNum = 1;
    //每页大小
    this.pageSize = 10;
    //1表示已认证页面2表示待认证页面3表示已驳回页面
    this.state = null;

    this.init = function () {
        this.initQueryMyhouse();
        //初始化事件
        this.bindMethod();
    }

    /*
    ********************************* 初始化区 *********************************
    */

    //初始化已认证信息
    this.initQueryMyhouse = function () {
        $('#dropload').dropload({
            scrollArea: window,
            loadDownFn: function (me) {
                var url = '/api/websiteInfo/queryWebsiteInfoList';
                var param = JSON.stringify({
                    "websiteName":$("#search_service").val(),
                    "pageNum": myHouse.startNum,
                    "pageSize": myHouse.pageSize
                })
                var successfn = function (retResult) {
                    var tempHtml = [];
                    var tmpObj = {};
                    var dataList = retResult.data.list;
                    if (null != dataList && "" != dataList) {
                        for (var i = 0; i < dataList.length; i++) {
                            tmpObj = dataList[i];
                            //1表示“默认房产”
                            var divClass = "c_checkbox";
                            if ('1' == '1') {
                                divClass = "c_checkbox after";
                            }

                            tempHtml.push('<div class="bgFFF house_plr15"><p class="house_num">'
                                + processUndefined(tmpObj.websiteName)
                                + '<span class="house_delete_ele">'
                                + '<a onclick="myHouse.deleteWebsite(this)" itemId="'+ processUndefined(tmpObj.id)+'"><img src="../../../images/delete.png" width="24" height="24" /></a>'
                                + '<a onclick="myHouse.goEditWebsitePage(this)" itemId="'+ processUndefined(tmpObj.id)+'"><img src="../../../images/btn_new@2x.png" width="24" height="24" /></a>'
                                + '</span></p>'
                                + '<p class="detail_address">登录名：' + processUndefined(tmpObj.loginName) + '</p>'
                                + '<p class="detail_address">登录密码：' + processUndefined(tmpObj.loginPwd) + '</p>'
                                + '</div>');

                        }

                        //当最后查询条数不足设置页面显示的条数时，无需再加载
                        if (dataList.length == myHouse.pageSize) {
                            myHouse.startNum = myHouse.startNum + 10;
                        } else {
                            // 无更多数据
                            me.dataType = "NO_MORE";
                            // 锁定
                            me.lock();
                            // 无数据
                            me.noData(true);
                            /*$(".dropload-load").remove();
                            $(".dropload-down").remove();*/
                        }

                    } else {
                        // 无数据
                        me.dataType = "NO";
                        // 锁定
                        me.lock();
                        // 无数据
                        me.noData();
                    }
                    $("#myHouseInfo").append(tempHtml.join(''));
                    me.resetload();

                }
                var errorfn = function (e) {
                    // 即使加载出错，也得重置
                    //me.resetload();
                    webToast("请求失败！", "middle");
                }
                $.JsonAjax2(url, param, null, "POST", successfn, errorfn);

            }
        });


    }

    /*
    ********************************* 事件区 *********************************
    */
    //绑定事件
    this.bindMethod = function () {
        //返回上级页面
        $("#back").unbind().on("click", function () {
            window.location.href = "/h5/myHouse/my-house.html";
            //myHouse.bridge.call("backToMain", {msg: "返回主页"});
        });

        //搜索查询
        //$("#search_service").unbind().on("input propertychange",function(){
        $("#search_service").unbind().on("change",function(){
            $(".dropload-load").remove();
            $(".dropload-down").remove();
            myHouse.startNum = 0;
            $("#myHouseInfo").html('');
            myHouse.initQueryMyhouse();
        });

        //新增页面
        $("#goAddPage").unbind().on("click", function () {
            sessionStorage.setItem("websiteInfoOperateType", "1");
            window.location.href = "/h5/myHouse/edit-website.html";
        });

    }

    //访问编辑页
    this.goEditWebsitePage = function (element) {
        sessionStorage.setItem("websiteInfoOperateType", "2");
        sessionStorage.setItem("websiteInfoId", $(element).attr("itemId"));
        window.location.href = "/h5/myHouse/edit-website.html";
    }

    //删除
    this.deleteWebsite = function (element) {
        var url = '/api/websiteInfo/updateWebsiteInfo/' + $(element).attr("itemId");
        var obj = new Object()
        obj["statusValue"] = 2;
        var successfn = function (retResult) {
            var code = retResult.respCode;
            if (code == "00000000") {
                webToast(retResult.respMsg,"middle");
                setTimeout(function(){
                    window.location.href = "/h5/myHouse/my-house.html";
                },3000);
            } else {
                webToast("删除失败！", "middle");
            }
        }
        var errorfn = function (e) {
            webToast("请求失败！", "middle");
        }

        Confirm("是否删除？", "温馨提示", function (b) {
            //callback 处理按钮事件
            if (b) {
                $.JsonAjax2(url, JSON.stringify(obj), null, "PUT", successfn, errorfn);
            } else {
                return;
            }
        });
    }


    /*
    ********************************* end *********************************
    */
}

var myHouse = new MyHouse();
$(document).ready(function () {
    // 初始化
    myHouse.init();

});

myHouse.Url = {};
myHouse.Url.getpar = function () {
    var url = location.href;
    return url.substring(url.indexOf("?") + 1, url.length);
}
myHouse.Url.getParams = function (paras) {
    var paraString = myHouse.Url.getpar().split("&");
    var paraObj = {}
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
                .indexOf("=")
            + 1, j.length);
    }
    if (paras == null)
        return paraObj;
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof(returnValue) == "undefined") {
        return "";
    } else {
        return returnValue.replace(/#/g, "");
    }
}

