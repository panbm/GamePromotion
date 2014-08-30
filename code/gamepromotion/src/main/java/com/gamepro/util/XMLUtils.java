package com.gamepro.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gamepro.base.bean.BaseBean;
import com.gamepro.consts.MsgType;
import com.gamepro.webchat.response.bean.ResponseArticleMsg;

public class XMLUtils {
	public static Map<String,String[]> extraMap;
	public static String specialHeader = "ArticleCount";
    static {
    	extraMap = new HashMap<String,String[]>();
    	extraMap.put(MsgType.IMAGE, new String[]{"MediaId"});
    	extraMap.put(MsgType.VOICE, new String[]{"MediaId"});
    	extraMap.put(MsgType.VIDEO, new String[]{"MediaId","Title","Description"});
    	extraMap.put(MsgType.MUSIC, new String[]{"Title","Description","MusicUrl","HQMusicUrl","ThumbMediaId"});
    	extraMap.put(MsgType.NEWS, new String[]{"Title","Description","PicUrl","Url"});
    }
	public static Map<String, String> getRequestParamMap(HttpServletRequest request)
			throws IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Map<String, String> map = null;
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(request.getInputStream());
			Element root = document.getDocumentElement();
			NodeList list = root.getChildNodes();
			map = new HashMap<String, String>();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				// 获得结点的类型
				short nodeType = node.getNodeType();
				if (nodeType == Node.ELEMENT_NODE) {
					String nodeName = node.getNodeName();
					String nodeVal = node.getFirstChild().getNodeValue();
					map.put(nodeName, nodeVal);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return map;
	}
	public static String newsListToXML(List<ResponseArticleMsg> newsList) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException{
		StringBuilder sb = new StringBuilder();
		if(null != newsList && newsList.size() >0){
			int count = newsList.size();
			newsList.get(0).setArticleCount(count);
			sb.append("<xml>").append("\n");
			sb.append(basicResponseBeanToXML(newsList.get(0),MsgType.NEWS));
			sb.append("<Articles>").append("\n");
			for(ResponseArticleMsg news:newsList){
				sb.append("<item>").append("\n");
				sb.append(getExtraValXml(news,MsgType.NEWS));
				sb.append("</item>").append("\n");
			}
			sb.append("</Articles>").append("\n");
			sb.append("</xml>");
		}
		return sb.toString();
	}
	public static <T extends BaseBean> String toXML(T bean,String msgType) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException{
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>").append("\n");
		sb.append(convertResponseBeanToXML(bean,msgType));
		sb.append("</xml>").append("\n");
		return sb.toString();
	}
	public static <T extends BaseBean> String convertResponseBeanToXML(T bean,String msgType) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException{
		StringBuilder sb = new StringBuilder();
		sb.append(basicResponseBeanToXML(bean,msgType));
		sb.append(appendExtraMsg(bean,msgType));
		return sb.toString();
		
	}
	public static <T extends BaseBean> String basicResponseBeanToXML(T bean,String msgType) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException{
		StringBuilder sb = new StringBuilder();
		for(String field:bean.getFields()){
			if(isExtraField(field,msgType)
					||(specialHeader.equals(field)&&!MsgType.NEWS.equals(msgType))){
				continue;
			}
			sb.append("<").append(field).append("><![CDATA[");
			sb.append(bean.getValue(field));
			sb.append("]]></").append(field).append(">").append("\n");
		}
		return sb.toString();
	}
	public static boolean isExtraField(String field,String msgType){
		String[] fieldArray = extraMap.get(msgType);
		if(null != fieldArray){
			for(String key:fieldArray){
				if(field.equals(key)){
					return true;
				}
			}
		}
		return false;
	}

	public static <T extends BaseBean> String getExtraValXml(T bean, String msgType)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, NoSuchFieldException {
		StringBuilder sb = new StringBuilder();
		String[] fields = extraMap.get(msgType);
		if (null != fields) {
			for (String key : fields) {
				sb.append("<").append(key).append("><![CDATA[");
				sb.append(bean.getValue(key));
				sb.append("]]></").append(key).append(">").append("\n");
			}
		}
		return sb.toString();
	}
	public static <T extends BaseBean> String appendExtraMsg(T bean,String msgType) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException{
		StringBuilder sb = new StringBuilder();
		if(MsgType.IMAGE.equals(msgType)){
			sb.append("<Image>").append("\n");
			sb.append(getExtraValXml(bean,msgType));
			sb.append("</Image>").append("\n");
		}else if(MsgType.VOICE.equals(msgType)){
			sb.append("<Voice>").append("\n");
			sb.append(getExtraValXml(bean,msgType));
			sb.append("</Voice>").append("\n");
		}else if(MsgType.VIDEO.equals(msgType)){
			sb.append("<Video>").append("\n");
			sb.append(getExtraValXml(bean,msgType));
			sb.append("</Video>").append("\n");
		}else if(MsgType.MUSIC.equals(msgType)){
			sb.append("<Music>").append("\n");
			sb.append(getExtraValXml(bean,msgType));
			sb.append("</Music>").append("\n");
		}else if(MsgType.NEWS.equals(msgType)){
			sb.append("<Articles>").append("\n");
			sb.append("<item>").append("\n");
			sb.append(getExtraValXml(bean,msgType));
			sb.append("</item>").append("\n");
			sb.append("</Articles>").append("\n");
		}
		return sb.toString();
	}
    public static <T extends BaseBean> T convertMapToBean(Map<String,String> valueMap,Class<T> classType) throws InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException{
    	T bean = classType.newInstance();
    	for(String field:bean.getFields()){
    		String value = valueMap.get(field);
    		bean.setValue(field, value);
    	}
    	return bean;
    }
   
}
