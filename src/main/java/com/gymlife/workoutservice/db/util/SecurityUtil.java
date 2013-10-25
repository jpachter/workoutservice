package com.gymlife.workoutservice.db.util;

public class SecurityUtil {

	public static String getHash(String salt, String password){
		return org.apache.commons.codec.digest.DigestUtils.sha256Hex(salt+password);
	}
}
