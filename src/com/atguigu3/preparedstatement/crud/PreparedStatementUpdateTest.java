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
 * ʹ��PreparedStatement���滻Statement��ʵ�ֶ����ݱ����ɾ�Ĳ���
 * 
 * ��ɾ�ģ���
 * */
public class PreparedStatementUpdateTest {

	//ͨ�õ���ɾ�Ĳ���
		public void update(String sql,Object ...args) {//sql��ռλ���ĸ�����ɱ��Բҵĳ�����ͬ��
			
			Connection conn=null;
			PreparedStatement ps=null;
			try {
				//1.��ȡ���ݿ������
				conn = JDBCUtils.getConnection();
				//2.Ԥ����sql��䣬����PreparedStatement��ʵ��
				ps = conn.prepareStatement(sql);
				//3.���ռλ��
				for(int i=0;i<args.length;i++) {
					ps.setObject(i+1, args[i]);//С�Ĳ�����������
				}
				//4.ִ��
				ps.execute();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				//5.��Դ�Ĺر�
				JDBCUtils.closeResource(conn, ps);
			}
		}
	
	//��course�������һ����¼
	@Test
	public void testInsert() {
		
			//3.��ȡ����
			Connection conn=null;
			PreparedStatement ps=null;
			try {
				//1.��ȡ�����ļ��е�4��������Ϣ
				InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
				
				Properties pros=new Properties();
				pros.load(is);
				
				String user = pros.getProperty("user");
				String password = pros.getProperty("password");
				String url = pros.getProperty("url");
				String driverClass = pros.getProperty("driverClass");
				
				//2.��������
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				//3.��ȡ����
				conn = DriverManager.getConnection(url, user, password);
				
//			System.out.println(conn);
				
				
				//4.Ԥ����sql��䣬����PreparedStatement��ʵ��
				String sql="insert into course(Cno,Cname,Cpno,Cpredit) values(?,?,?,?)";//?Ϊռλ��
				ps = conn.prepareStatement(sql);
				
				//5.���ռλ��
				ps.setString(1, "8");
				ps.setString(2, "Linux�����ŵ���ͨ");
				ps.setString(3, "6");
				ps.setInt(4, 5);
				
				//6.ִ�в���
				ps.execute();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				//7.��Դ�Ĺر�
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
	
	//�޸�course���е�һ����¼
	@Test
	public void testUpdate() {
		
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			//1.��ȡ���ݿ������
			conn = JDBCUtils.getConnection();
			//2.Ԥ����sql��䣬����PreparedStatement��ʵ��
			String sql="update course set Cpno=? where Cno=?";
			ps = conn.prepareStatement(sql);
			//3.���ռλ��
			ps.setObject(1, "5");
			ps.setObject(2, "8");
			//4.ִ��
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//5.��Դ�Ĺر�
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
