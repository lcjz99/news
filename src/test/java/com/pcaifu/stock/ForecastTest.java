package com.pcaifu.stock;

/**  
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2017年9月13日 下午6:34:54  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcaifu.Application;
import com.pcaifu.dto.ForecastDto;

//网上很多会在这里使用import static,主要导入的是MockMvcRequestBuilders，MockMvcResultMatchers，Matchers这三个类中的方法。

/**
 * @author zz
 * @date 2017年7月4日
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ForecastTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();// 建议使用这种
	}

	@Test
	public void testForecast1() throws Exception {
		ForecastDto dto = new ForecastDto();
		dto.setUserId("180123102110000018");
		dto.setNickName("股宝031468");
		dto.setMobile("13671120416");
		dto.setFromType("1");
		dto.setStockCode("sh600000");
		dto.setStockName("浦发银行");
		dto.setPeriodType("1002");
		dto.setForecastType("1002");
		dto.setExpectDate("2018-01-29");
		dto.setCurrentValue("100.25");
		dto.setForecastValue("100.35");
		dto.setForecastCond("测试123--");
		dto.setForecastNote("测试阿德发放");

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBook = objectMapper.writeValueAsString(dto);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post("/stock/forecast").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(jsonBook.getBytes()).accept(MediaType.APPLICATION_JSON)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(status);
		System.out.println(content);

	}

	@Test
	public void testForecast2() throws Exception {
		ForecastDto dto = new ForecastDto();
		dto.setUserId("180123102110000018");
		dto.setNickName("股宝031468");
		dto.setMobile("13671120416");
		dto.setFromType("2");
		dto.setStockCode("sh600000");
		dto.setStockName("浦发银行");
		dto.setPeriodType("1005");
		dto.setForecastType("1003");
		dto.setExpectDate("2018-02-02");
		dto.setCurrentValue("100.25");
		dto.setForecastCond("测试123--");
		dto.setForecastNote("测试阿德发放");
		dto.setPkSerial("28");

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBook = objectMapper.writeValueAsString(dto);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post("/stock/forecast").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(jsonBook.getBytes()).accept(MediaType.APPLICATION_JSON)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(status);
		System.out.println(content);

	}

}