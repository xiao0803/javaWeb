package com.example.mapper;

import com.example.dto.WebsiteInfoReqParams;
import com.example.entity.WebsiteInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WebsiteInfoMapper {

	int addWebsiteInfo(@Param("params") WebsiteInfo params);

	int updateWebsiteInfo(@Param("params") WebsiteInfo params);

	List<WebsiteInfo> queryWebsiteInfoList(@Param("params") WebsiteInfoReqParams params);

	@Select("SELECT * FROM t_website_info WHERE id = #{id}")
	WebsiteInfo queryWebsiteInfoById(@Param("id") Long id);
}