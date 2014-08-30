package com.gamepro.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sina.sae.util.SaeUserInfo;

public class GetMsgServlet  extends HttpServlet {
	String username=SaeUserInfo.getAccessKey();
	String password=SaeUserInfo.getSecretKey();
	String driver="com.mysql.jdbc.Driver";
	String url="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_gamejoy";
	String token = "tcyjd2013";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userId = req.getParameter("user");
		String score = req.getParameter("score");
		if(null != userId && null != score){
			insertData(userId,"score",Integer.parseInt(score));
		}
	}
	public String insertData(String name,String res,int score){
		String sql = "insert into gameinfo values('"+name+"','"+res+"',"+score+")";
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url,username,password);
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
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	

}
