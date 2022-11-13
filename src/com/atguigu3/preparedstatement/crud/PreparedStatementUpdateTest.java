package com.atguigu3.preparedstatement.crud;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import com.atguigu3.utils.JDBCUtils;

/**
 * 使用PreparedStatement来替换Statement，实现对数据表的增删改操作
 * 
 * 增删改；查
 * */
public class PreparedStatementUpdateTest {

	//通用的增删改操作
		public void update(String sql,Object ...args) {//sql中占位符的个数与可变性惨的长度相同！
			
			Connection conn=null;
			PreparedStatement ps=null;
			try {
				//1.获取数据库的连接
				conn = JDBCUtils.getConnection();
				//2.预编译sql语句，返回PreparedStatement的实例
				ps = conn.prepareStatement(sql);
				//3.填充占位符
				for(int i=0;i<args.length;i++) {
					ps.setObject(i+1, args[i]);//小心参数声明错误！
				}
				//4.执行
				ps.execute();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				//5.资源的关闭
				JDBCUtils.closeResource(conn, ps);
			}
		}
	
	//向course表中添加一条记录
	@Test
	public void testInsert() {
		
			//3.获取连接
			Connection conn=null;
			PreparedStatement ps=null;
			try {
				//1.读取配置文件中的4个基本信息
				InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
				
				Properties pros=new Properties();
				pros.load(is);
				
				String user = pros.getProperty("user");
				String password = pros.getProperty("password");
				String url = pros.getProperty("url");
				String driverClass = pros.getProperty("driverClass");
				
				//2.加载驱动
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				//3.获取连接
				conn = DriverManager.getConnection(url, user, password);
				
//			System.out.println(conn);
				
				
				//4.预编译sql语句，返回PreparedStatement的实例
				String sql="insert into course(Cno,Cname,Cpno,Cpredit) values(?,?,?,?)";//?为占位符
				ps = conn.prepareStatement(sql);
				
				//5.填充占位符
				ps.setString(1, "8");
				ps.setString(2, "Linux从入门到精通");
				ps.setString(3, "6");
				ps.setInt(4, 5);
				
				//6.执行操作
				ps.execute();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
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
	}
	
	//修改course表中的一条记录
	@Test
	public void testUpdate() {
		
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			//1.获取数据库的连接
			conn = JDBCUtils.getConnection();
			//2.预编译sql语句，返回PreparedStatement的实例
			String sql="update course set Cpno=? where Cno=?";
			ps = conn.prepareStatement(sql);
			//3.填充占位符
			ps.setObject(1, "5");
			ps.setObject(2, "8");
			//4.执行
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//5.资源的关闭
			JDBCUtils.closeResource(conn, ps);
		}	
	}
	@Test
	public void testCommonUpdate() {
//		String sql="delete from course where Cno=?";
//		update(sql,8);
		
		String sql="update sc set Grade=? where Sno=? ";
		update(sql,"98","3116003042");
	}
	
	
}
