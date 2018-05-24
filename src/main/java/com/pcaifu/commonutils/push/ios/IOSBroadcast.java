package com.pcaifu.commonutils.push.ios;

import com.pcaifu.commonutils.push.IOSNotification;

public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "broadcast");

	}
}
