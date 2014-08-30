package com.gamepro.webchat.request.bean;
/** 
 * 文本消息 
 *  
 * @author liufeng 
 * @date 2013-05-19 
 */  
public class RequestTextMsg extends RequestBaseMsg {  
    // 消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  