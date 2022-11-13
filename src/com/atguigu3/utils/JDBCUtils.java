package com.atguigu3.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Description 获取数据库的连接
 * @author 张先森
 * @date 
 * */
public class JDBCUtils {
	public static Connection getConnection() throws Exception {
		//1.获取配置文件中的4个基本信息
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
		
		Properties pros=new Properties();
		pros.load(is);
		
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");
		
		//2.加载驱动
		Class.forName(driverClass);
		//3.获取连接
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	/**
	 * @Description 关闭连接和Statement的操作
	 * @author 张先森
	 * */
	public static void closeResource(Connection conn,Statement ps) {
		//7.资源的关闭
		try {
			if(ps!=null) {
				ps.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @Description 对资源的关闭
	 * @author 张先森
	 * */
	public static void closeResource(Connection conn,Statement ps,ResultSet rs) {
		//7.资源的关闭
		try {
			if(ps!=null) {
				ps.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}try {
			if(rs!=null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}
