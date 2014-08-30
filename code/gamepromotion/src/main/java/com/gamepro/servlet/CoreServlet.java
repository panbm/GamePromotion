package com.gamepro.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gamepro.consts.EnvironmentInfo;
import com.gamepro.service.CoreService;
import com.gamepro.util.ValidateUtils;

public class CoreServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 微信加密签名  
        String signature = req.getParameter("signature");  
        // 时间戳  
        String timestamp = req.getParameter("timestamp");  
        // 随机数  
        String nonce = req.getParameter("nonce");  
        // 随机字符串  
        String echostr = req.getParameter("echostr");  
  
        PrintWriter out = resp.getWriter();  
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (ValidateUtils.checkSignature(signature, timestamp, nonce,EnvironmentInfo.token)) {  
            out.print(echostr);  
        }  
        out.close();  
    }  

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
		req.setCharacterEncoding("UTF-8");  
        resp.setCharacterEncoding("UTF-8");  
        // 调用核心业务类接收消息、处理消息  
        String respMessage = CoreService.processRequest(req);  
        // 响应消息  
        PrintWriter out = resp.getWriter();  
        out.print(respMessage);  
        out.close();  
	}

}
