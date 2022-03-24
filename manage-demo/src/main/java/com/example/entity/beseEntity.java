package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class beseEntity {
    // 主键ID
    private Long id;

    // 名称
    private String fullName;

    // 状态
    private Integer statusValue;

    // 修改者ID
    private String modificatorId;

    // 修改时间
    private Timestamp updateTime;

    // 创建者ID
    private String creatorId;

    // 创建时间
    private Timestamp createTime;
}