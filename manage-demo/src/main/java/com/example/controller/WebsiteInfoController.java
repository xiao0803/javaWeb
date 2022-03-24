/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.controller;

import com.example.common.PageDto;
import com.example.common.RespResult;
import com.example.common.RespUtil;
import com.example.dto.WebsiteInfoReqParams;
import com.example.entity.WebsiteInfo;
import com.example.impl.WebsiteInfoServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "网站消息管理", tags = "WebsiteInfo")
@RestController
@RequestMapping("/api/websiteInfo")
public class WebsiteInfoController {

	@Autowired
	private WebsiteInfoServiceImpl websiteInfoServiceImpl;

//	@GetMapping(value="/test")
//	public String getAllUser(){
//		websiteInfoServiceImpl.selectByPrimaryKey(1);
//		return null;
//	}
//
//	@RequestMapping("/welcome")
//	public String hello() {
//		return "Hello World";
//	}


	@PostMapping(value = "/addWebsiteInfo")
	public RespResult<Void> addWebsiteInfo(@Validated @RequestBody WebsiteInfo form, BindingResult bindingResult) {
		websiteInfoServiceImpl.addWebsiteInfo(form);
		return RespUtil.success();
	}

	@PutMapping(value = "/updateWebsiteInfo/{id}")
	public RespResult<Void> updateWebsiteInfo(@PathVariable(value = "id") Long id, @RequestBody WebsiteInfo form) {
		form.setId(id);
		websiteInfoServiceImpl.updateWebsiteInfo(form);
		return RespUtil.success();
	}

	@GetMapping(value = "/queryWebsiteInfoById/{id}")
	public RespResult<WebsiteInfo> queryWebsiteInfoById(@PathVariable(value = "id") Long id) {
		return websiteInfoServiceImpl.queryWebsiteInfoById(id);
	}

	@PostMapping(value = "/queryWebsiteInfoList")
	public RespResult<PageDto<WebsiteInfo>> queryWebsiteInfoList(@RequestBody WebsiteInfoReqParams params) {
		return websiteInfoServiceImpl.queryWebsiteInfoList(params);
	}

}
