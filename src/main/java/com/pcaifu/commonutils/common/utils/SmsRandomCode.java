/**
 * 
 */
package com.pcaifu.commonutils.common.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: SmsRandomCode
 * @Description: TODO(随机生成短信验证码)
 * @author lichao@ppdai.com
 * @date 2015年8月29日 下午6:02:22
 * 
 */
public class SmsRandomCode {
	public static String getRamdomCode() {
		String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "0", "1" };
		List<String> list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		System.out.println(afterShuffle);
		String result = afterShuffle.substring(3, 9);
		return result;
	}

	public static String getInviteCode() {
		String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "0", "1" };
		List<String> list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		System.out.println(afterShuffle);
		String result = afterShuffle.substring(2, 8);
		return result;
	}

}
