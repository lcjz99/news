package com.pcaifu.commonutils.listener;

import java.util.concurrent.Executor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.starit.diamond.manager.DiamondManager;
import com.starit.diamond.manager.ManagerListener;
import com.starit.diamond.manager.impl.DefaultDiamondManager;

@WebListener
public class DiamondConfInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DiamondManager manager = new DefaultDiamondManager("config", "riches_newser_config.properties",
				new ManagerListener() {// 填写你服务端后台保存过的group和dataId
					public void receiveConfigInfo(String configInfo) {
					}

					public Executor getExecutor() {
						return null;
					}
				});
		// 设置diamond-server服务的端口
		manager.getDiamondConfigure().setPort(80);
		String availableConfigureInfomation = manager.getAvailableConfigureInfomation(8000);
	}
}