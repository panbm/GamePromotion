package com.gamepro.webchat.response.bean;

import com.gamepro.base.bean.BaseBean;

/** 
 * 消息基类（公众帐号 -> 普通用户） 
 *  
 * @author liufeng 
 * @date 2013-05-19 
 */  
public class ResponseBaseMsg extends BaseBean{  

	// 接收方帐号（收到的OpenID）  
    private String ToUserName;  
    // 开发者微信号  
    private String FromUserName;  
    // 消息创建时间 （整型）  
    private long CreateTime;  
    // 消息类型（text/music/news）  
    private String MsgType;  
    // 图文消息个数，限制为10条以内  
    private int ArticleCount;  
   // 位0x0001被标志时，星标刚收到的消息  
    private int FuncFlag;  
  
   
  
    public int getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public String getToUserName() {  
        return ToUserName;  
    }  
  
    public void setToUserName(String toUserName) {  
        ToUserName = toUserName;  
    }  
  
    public String getFromUserName() {  
        return FromUserName;  
    }  
  
    public void setFromUserName(String fromUserName) {  
        FromUserName = fromUserName;  
    }  
  
    public long getCreateTime() {  
        return CreateTime;  
    }  
  
    public void setCreateTime(long createTime) {  
        CreateTime = createTime;  
    }  
  
    public String getMsgType() {  
        return MsgType;  
    }  
  
    public void setMsgType(String msgType) {  
        MsgType = msgType;  
    }  
 
}  