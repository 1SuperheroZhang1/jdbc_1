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
	//��ʽһ��
	@Test
	public void testConnection1() throws SQLException {
		//��ȡDriverʵ�������
		Driver driver=new com.mysql.cj.jdbc.Driver();
		
		//url: http://localhost:8080/gmall/keyboard.jpg
		//jdbc:mysql :Э��
		//localhost :ip��ַ
		//3306 :Ĭ��mysql�Ķ˿ں�
		//test :���ݿ��� 
		String url="jdbc:mysql://localhost:3306/tmp";
		//���û����������װ��Properties��
		Properties info=new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "020704");
		
		Connection conn=driver.connect(url, info);
		
		System.out.println(conn);
	}
	//��ʽ�����Է�ʽһ�ĵ���:�����µĳ����в����ֵ�������api��ʹ�ó�����и��õĿ���ֲ�ԡ�
	@Test
	public void testConnection2() throws Exception {
		//1.��ȡDriverʵ�������ʹ�÷���
		Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
		Driver driver=(Driver) clazz.newInstance();
		
		//2.�ṩҪ���ӵ����ݿ�
		String url="jdbc:mysql://localhost:3306/tmp";
		
		//3.�ṩ������Ҫ���û���������
		Properties info=new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "020704");
		
		//4.��ȡ����
		Connection conn = driver.connect(url, info);
		System.out.println(conn);
	}
	//��ʽ����ʹ��DriverManager�滻Driver
	@Test
	public void testConnection3() throws Exception {
		
		//1.��ȡDriverʵ����Ķ���
		Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
		Driver driver=(Driver) clazz.newInstance();
		
		//2.��ȡ����������������Ҫ����Ϣ
		String url="jdbc:mysql://localhost:3306/tmp";
		String user="root";
		String password="020704";
		
		//ע������
		DriverManager.registerDriver(driver);
		
		//��ȡ����
		Connection conn = DriverManager.getConnection(url, user, password);
		
		System.out.println(conn);
	}
	//��ʽ�ģ�����ֻ�Ǽ���������������ʾ��ע�������ˡ�
		@Test
		public void testConnection4() throws Exception {
			
			//1.��ȡ����������������Ҫ����Ϣ
			String url="jdbc:mysql://localhost:3306/tmp";
			String user="root";
			String password="020704";
			
			//2.����Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//����ڷ�ʽ��������ʡ�����²�����
//			Driver driver=(Driver) clazz.newInstance();
//			//ע������
//			DriverManager.registerDriver(driver);
			//Ϊʲô����ʡ�����������أ�
			/**
			 * ��mysql��Driverʵ�����У����������µĲ�����
			 * static{
			 * 		try{
			 * 				java.sql.DriverManager.registerDriver(new Driver());
			 * 		}catch(SQLException E){
			 * 				throw new RuntimeException("Can't register driver!");
			 * 		}
			 * }
			 * */
			
			//��ȡ����
			Connection conn = DriverManager.getConnection(url, user, password);
			
			System.out.println(conn);
		}
//��ʽ��(final��)�������ݿ�������Ҫ��4��������Ϣ�����������ļ��У�ͨ����ȡ�����ļ��ķ�ʽ����ȡ����
	 /**
	  * ���ַ�ʽ�ĺô���
	  * 1.ʵ�������������ķ��롣ʵ���˽���
	  * 2.�����Ҫ�޸������ļ���Ϣ�����Ա���������´����
	  * */
		@Test
	 public void testConnection5() throws Exception {
		 
		 //1.��ȡ�����ļ��е�4��������Ϣ
		 InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
		 
		 Properties pros=new Properties();
		 pros.load(is);
		 
		 String user=pros.getProperty("user");
		 String password=pros.getProperty("password");
		 String url=pros.getProperty("url");
		 String driverClass=pros.getProperty("driverClass");
		 
		 //2.��������
		 Class.forName(driverClass);
		 
		 //3.��ȡ����
		 Connection conn = DriverManager.getConnection(url, user, password);
		 System.out.println(conn);
	 }
	
}
