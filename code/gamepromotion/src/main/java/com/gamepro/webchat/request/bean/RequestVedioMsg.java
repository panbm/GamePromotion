package com.gamepro.webchat.request.bean;

public class RequestVedioMsg extends RequestBaseMsg{
private String MediaId;
 private String ThumbMediaId;
 public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
