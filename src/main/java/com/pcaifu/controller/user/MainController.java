package com.pcaifu.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pcaifu.commonutils.base.BaseController;

import io.swagger.annotations.Api;

@RestController
@Api(description = "Main类可忽略")
public class MainController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)

	public String helloOpenApi() {
		logger.info("欢迎...");
		return "welcome to newsApi";
	}
}
