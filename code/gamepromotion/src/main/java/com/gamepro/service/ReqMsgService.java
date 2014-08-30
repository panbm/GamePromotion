package com.gamepro.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.gamepro.base.bean.BaseBean;
import com.gamepro.consts.FieldsConsts;
import com.gamepro.consts.MsgType;
import com.gamepro.util.XMLUtils;
import com.gamepro.webchat.request.bean.RequestImageMsg;
import com.gamepro.webchat.request.bean.RequestLinkMsg;
import com.gamepro.webchat.request.bean.RequestLocationMsg;
import com.gamepro.webchat.request.bean.RequestTextMsg;
import com.gamepro.webchat.request.bean.RequestVedioMsg;
import com.gamepro.webchat.request.bean.RequestVoiceMsg;

public class ReqMsgService {

	public <T extends BaseBean> T getRequestBean(Map<String,String> map) throws IOException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException{
		String msgType = map.get(FieldsConsts.MSGTYPE);
		Class<? extends BaseBean> classType = getReqBeanClass(msgType);
		return (T) XMLUtils.convertMapToBean(map, classType);
	}
	public  Class<? extends BaseBean> getReqBeanClass(String msgType){
		if(MsgType.TEXT.equals(msgType)){
			return RequestTextMsg.class;
		}else if(MsgType.IMAGE.equals(msgType)){
			return RequestImageMsg.class;
		}else if(MsgType.VOICE.equals(msgType)){
			return RequestVoiceMsg.class;
		}else if(MsgType.VIDEO.equals(msgType)){
			return  RequestVedioMsg.class;
		}else if(MsgType.LOCATION.equals(msgType)){
			return RequestLocationMsg.class;
		}else if(MsgType.LINK.equals(msgType)){
			return RequestLinkMsg.class;
		}
		return null;
	}
}
