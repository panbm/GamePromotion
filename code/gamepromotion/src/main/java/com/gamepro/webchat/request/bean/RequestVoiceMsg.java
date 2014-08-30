package com.gamepro.webchat.request.bean;
/** 
 * 音频消息 
 *  
 * @author liufeng 
 * @date 2013-05-19 
 */  
public class RequestVoiceMsg extends RequestBaseMsg {  
    // 媒体ID  
    private String MediaId;  
    // 语音格式  
    private String Format;
    //语音识别结果
    private String Recognition;
  
    public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
    public String getFormat() {  
        return Format;  
    }  
  
    public void setFormat(String format) {  
        Format = format;  
    }  
} 