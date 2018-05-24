package com.pcaifu.commonutils.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UmenPushUtils {

	private static final String appKeyForAndroid = "5a683e4ca40fa375bd000165";
	private static final String appMasterSecretForAndroid = "dj1nwchagbcgkga9hezie96kzupoxpef";
	private static final String appKeyForIOS = "5a68411e8f4a9d2068000174";
	private static final String appMasterSecretForIOS = "anlme20ym6jt0owl7ejjmw90i1smgirj";
	private final static Logger log = LoggerFactory.getLogger(UmenPushUtils.class);

	public static boolean pushAndroid(String title, String text, boolean testFlag, String deviceTokens) {
		boolean result = false;
		try {
			AndroidBroadcast broadcast = new AndroidBroadcast(appKeyForAndroid, appMasterSecretForAndroid);

			broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			broadcast.setTicker(title);
			broadcast.setTitle(title);
			broadcast.setText(text);
			broadcast.setPredefinedKeyValue("type", "listcast");
			if (testFlag) {
				broadcast.setTestMode();
			}
			broadcast.setPredefinedKeyValue("device_tokens", deviceTokens);
			PushClient client = new PushClient();
			result = client.send(broadcast);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return result;

	}

	public static boolean pushIOS(String text, boolean testFlag, String deviceToken) {
		boolean result = false;
		try {
			IOSBroadcast broadcast = new IOSBroadcast(appKeyForIOS, appMasterSecretForIOS);
			broadcast.setAlert(text);
			broadcast.setBadge(0);
			broadcast.setPredefinedKeyValue("type", "listcast");
			broadcast.setPredefinedKeyValue("device_tokens", deviceToken);
			broadcast.setSound("default");
			if (testFlag) {
				broadcast.setTestMode();
			}
			PushClient client = new PushClient();
			result = client.send(broadcast);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return result;
	}

	public static void main(String[] args) {
		pushIOS("hello worldaa!", false, "0a542056398bd109a06dd65cf114e82524e0cdd296634c5dfc0f62c072922754");
		// pushAndroid("title","text", true,
		// "AmiRDk5jLN1mEUr5ZEc9WFFqs5C6VhbKrnPFE5dr-JqY,");
	}

}
