package com.atguigu1.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class ConnectionTest {
	//方式一：
	@Test
	public void testConnection1() throws SQLException {
		//获取Driver实现类对象
		Driver driver=new com.mysql.cj.jdbc.Driver();
		
		//url: http://localhost:8080/gmall/keyboard.jpg
		//jdbc:mysql :协议
		//localhost :ip地址
		//3306 :默认mysql的端口号
		//test :数据库名 
		String url="jdbc:mysql://localhost:3306/tmp";
		//将用户名和密码封装在Properties中
		Properties info=new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "020704");
		
		Connection conn=driver.connect(url, info);
		
		System.out.println(conn);
	}
	//方式二：对方式一的迭代:在如下的程序中不出现第三方的api，使得程序具有更好的可移植性。
	@Test
	public void testConnection2() throws Exception {
		//1.获取Driver实现类对象：使用反射
		Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
		Driver driver=(Driver) clazz.newInstance();
		
		//2.提供要连接的数据库
		String url="jdbc:mysql://localhost:3306/tmp";
		
		//3.提供连接需要的用户名和密码
		Properties info=new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "020704");
		
		//4.获取连接
		Connection conn = driver.connect(url, info);
		System.out.println(conn);
	}
	//方式三：使用DriverManager替换Driver
	@Test
	public void testConnection3() throws Exception {
		
		//1.获取Driver实现类的对象
		Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
		Driver driver=(Driver) clazz.newInstance();
		
		//2.获取其他三个连接所需要的信息
		String url="jdbc:mysql://localhost:3306/tmp";
		String user="root";
		String password="020704";
		
		//注册驱动
		DriverManager.registerDriver(driver);
		
		//获取连接
		Connection conn = DriverManager.getConnection(url, user, password);
		
		System.out.println(conn);
	}
	//方式四：可以只是加载驱动，不用显示的注册驱动了。
		@Test
		public void testConnection4() throws Exception {
			
			//1.获取其他三个连接所需要的信息
			String url="jdbc:mysql://localhost:3306/tmp";
			String user="root";
			String password="020704";
			
			//2.加载Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//相较于方式三：可以省略如下操作：
//			Driver driver=(Driver) clazz.newInstance();
//			//注册驱动
//			DriverManager.registerDriver(driver);
			//为什么可以省略上述操作呢？
			/**
			 * 在mysql的Driver实现类中，声明了如下的操作：
			 * static{
			 * 		try{
			 * 				java.sql.DriverManager.registerDriver(new Driver());
			 * 		}catch(SQLException E){
			 * 				throw new RuntimeException("Can't register driver!");
			 * 		}
			 * }
			 * */
			
			//获取连接
			Connection conn = DriverManager.getConnection(url, user, password);
			
			System.out.println(conn);
		}
//方式五(final版)：将数据库连接需要的4个基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
	 /**
	  * 此种方式的好处？
	  * 1.实现了数据与代码的分离。实现了解耦
	  * 2.如果需要修改配置文件信息，可以避免程序重新打包。
	  * */
		@Test
	 public void testConnection5() throws Exception {
		 
		 //1.读取配置文件中的4个基本信息
		 InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
		 
		 Properties pros=new Properties();
		 pros.load(is);
		 
		 String user=pros.getProperty("user");
		 String password=pros.getProperty("password");
		 String url=pros.getProperty("url");
		 String driverClass=pros.getProperty("driverClass");
		 
		 //2.加载驱动
		 Class.forName(driverClass);
		 
		 //3.获取连接
		 Connection conn = DriverManager.getConnection(url, user, password);
		 System.out.println(conn);
	 }
	
}
