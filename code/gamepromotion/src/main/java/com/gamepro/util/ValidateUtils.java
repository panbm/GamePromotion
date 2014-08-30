package com.gamepro.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ValidateUtils {
	public static boolean checkSignature(String timestamp,String nonce,String signature,String token){
		String[] array = new String[]{token,timestamp,nonce};
		Arrays.sort(array);
		StringBuilder sb = new StringBuilder();
		for(String s:array){
			sb.append(s);	
		}
		byte[] digest = null;
		String result = null;
		try {
			digest = MessageDigest.getInstance("SHA-1").digest(sb.toString().getBytes());
			result = new String(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(null != result && signature.equals(result)){
			return true;
		}else{
			return false;
		}
	}
}
