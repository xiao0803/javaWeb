package com.example.dto;

import com.example.common.PageBaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebsiteInfoReqParams extends PageBaseParam {

    @ApiModelProperty(value = "网站名称")
    private String websiteName;

    @ApiModelProperty(value = "网站地址")
    private String websiteUrl;

    @ApiModelProperty(value = "登录名称")
    private String loginName;

    @ApiModelProperty(value = "登录密码")
    private String loginPwd;

    @ApiModelProperty(value = "是否备份")
    private Integer backupsFlag;

    @ApiModelProperty(value = "状态；1-Enable-可用、2-Disable--不可用")
    private Integer statusValue;
}
