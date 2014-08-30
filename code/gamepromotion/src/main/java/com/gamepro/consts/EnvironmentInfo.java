package com.gamepro.consts;

import com.sina.sae.util.SaeUserInfo;

public class EnvironmentInfo {
	public static final String username=SaeUserInfo.getAccessKey();
	public static final String password=SaeUserInfo.getSecretKey();
	public static final String driver="com.mysql.jdbc.Driver";
	public static final String url="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_gamejoy";
	public static final String token = "tcyjd2013";
}
