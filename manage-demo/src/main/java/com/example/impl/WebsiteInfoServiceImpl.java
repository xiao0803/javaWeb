package com.example.impl;

import com.example.common.PageDto;
import com.example.common.RespResult;
import com.example.common.RespUtil;
import com.example.dto.WebsiteInfoReqParams;
import com.example.entity.WebsiteInfo;
import com.example.mapper.WebsiteInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteInfoServiceImpl {

    @Autowired
    private WebsiteInfoMapper websiteInfoMapper;

    public int addWebsiteInfo(WebsiteInfo params){
        return websiteInfoMapper.addWebsiteInfo(params);
    }

    public int updateWebsiteInfo(WebsiteInfo params){
        params.setBackupsFlag(2);
        return websiteInfoMapper.updateWebsiteInfo(params);
    }

    public RespResult<WebsiteInfo> queryWebsiteInfoById(Long id){
        WebsiteInfo websiteInfo = websiteInfoMapper.queryWebsiteInfoById(id);
        return RespUtil.success(websiteInfo);
    }

    public RespResult<PageDto<WebsiteInfo>> queryWebsiteInfoList(WebsiteInfoReqParams params){
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        if (params.getWebsiteName() != null) {
            params.setWebsiteName("%" + params.getWebsiteName() + "%");
        }
        params.setStatusValue(1);
        List<WebsiteInfo> list = websiteInfoMapper.queryWebsiteInfoList(params);
        PageInfo<WebsiteInfo> pageSource = new PageInfo<>(list);
        PageDto<WebsiteInfo> pageDto = new PageDto<>(pageSource.getTotal(), params.getPageNum(), params.getPageSize(), list);
        return RespUtil.success(pageDto);
    }


}

