package com.pcaifu;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pcaifu.commonutils.common.utils.GsonUtils;
import com.pcaifu.controller.msg.SmsHandleController;

public class ControllerTest extends TestBaseCase {

	@Autowired
	private SmsHandleController smsHandleController;

	@Test
	public void test05() {

		// startDate

	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", "233333");
		map.put("name", "张三");
		System.out.println(GsonUtils.getJson(map));
	}
}
