package com.example.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebsiteInfo extends beseEntity{

    private String websiteName;

    private String websiteUrl;

    private String loginName;

    private String loginPwd;

    // 终端类型:PC网站、PC应用、APP、微信公众号、微信小程序等
    private String terminalType;

    // 网站一级分类:普通网站、开发工具等
    private String category1;

    // 网站二级分类:政府平台、版本控制等
    private String category2;

    private String otherInfo;

    private String remark;

    // 是否备份；1-否、2-是
    private Integer backupsFlag;
}