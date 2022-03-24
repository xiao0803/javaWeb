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

    // 是否备份；1-否、2-是
    private Integer backupsFlag;
}