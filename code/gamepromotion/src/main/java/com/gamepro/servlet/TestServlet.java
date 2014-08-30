package com.gamepro.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gamepro.consts.EnvironmentInfo;
import com.gamepro.util.ValidateUtils;

public class TestServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//微信接口认证
		//webChatCheck(request,response);
		getMsg(request,response);
	}
    public void getMsg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	try {
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = null;
			db = dbf.newDocumentBuilder();
			Document document = db.parse(request.getInputStream());
			Element root = document.getDocumentElement();
			NodeList  list = root.getChildNodes();
			Map<String,String> map = new HashMap<String,String>(); 
			for(int i=0;i<list.getLength();i++){
				Node node = list.item(i);
				//获得结点的类型  
	            short nodeType = node.getNodeType();  
	            if(nodeType == Node.ELEMENT_NODE)  
	            {  
	               String nodeName = node.getNodeName();
	               String nodeVal = node.getFirstChild().getNodeValue();
	               map.put(nodeName, nodeVal);
	               
	            }  
			}
			if(map.size() > 0){
				insertData(map.get("FromUserName"),map.get("Content"),0);
				responsePage(map,response);
			}
			/*RequestDispatcher rd = request.getRequestDispatcher("/2048.htm?user="+map.get("FromUserName")); 
			rd.forward(request,response);*/
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    public void responsePage(Map<String,String> valueMap,HttpServletResponse response) throws IOException{
    	PrintWriter out = response.getWriter();
		response.setContentType("text/xml;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		//out.println("<?xml version=\"1.0\" encoding='UTF-8'\?>");
		out.println("<xml>");
		
		out.print("<ToUserName><![CDATA[");
		out.print(valueMap.get("FromUserName"));
		out.println("]]></ToUserName>");
		
		out.print("<FromUserName><![CDATA[");
		out.print(valueMap.get("ToUserName"));
		out.println("]]></FromUserName>");
		
		out.print("<CreateTime><![CDATA[");
		out.print("20140511");
		out.println("]]></CreateTime>");
		
		out.print("<MsgType><![CDATA[");
		out.print("news");
		out.println("]]></MsgType>");
		
		out.print("<ArticleCount><![CDATA[");
		out.print("1");
		out.println("]]></ArticleCount>");
		
		out.print("<Articles>");
		out.print("<item>");
		
		out.print("<Title><![CDATA[");
		out.print("2048");
		out.println("]]></Title>");
		
		out.print("<Description><![CDATA[");
		out.print("welcome 2048!");
		out.println("]]></Description>");
		
		out.print("<PicUrl><![CDATA[");
		out.print("http://gamejoy.sinaapp.com/2048.png");
		out.println("]]></PicUrl>");
		
		out.print("<Url><![CDATA[");
		out.print("http://gamejoy.sinaapp.com/2048.htm?user="+valueMap.get("FromUserName"));
		out.println("]]></Url>");
		
		out.print("</item>");
		out.println("</Articles>");
		out.print("</xml>");
    }
   
	public void signature(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		if (null != timestamp && null != nonce && null != signature) {
			if (ValidateUtils
					.checkSignature(timestamp, nonce, signature, EnvironmentInfo.token)) {
				out.println(echostr);
			}
		} else {
			out.println(echostr);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	public String insertData(String name,String res,int score){
		String sql = "insert into gameinfo values('"+name+"','"+res+"',"+score+")";
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(EnvironmentInfo.driver).newInstance();
			conn = DriverManager.getConnection(EnvironmentInfo.url,EnvironmentInfo.username,EnvironmentInfo.password);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			return "success";
		} catch (InstantiationException e) {
			return "InstantiationException";
		} catch (IllegalAccessException e) {
			return "IllegalAccessException";
		} catch (ClassNotFoundException e) {
			return "ClassNotFoundException";
		} catch (SQLException e) {
			return "SQLException";
		}finally{
			if(null != stmt){
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null != conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}