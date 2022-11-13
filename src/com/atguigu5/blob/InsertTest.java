package com.atguigu5.blob;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.atguigu3.utils.JDBCUtils;

/*
 * ʹ��PreparedStatementʵ���������ݵĲ���
 * 
 * update��delete����;�������������Ч����
 * ��ʱ��������������Ҫָ�����������롣ʹ��PreparedStatement���ʵ�ָ���Ч���������룿
 * ��Ŀ����goods���в���20000������
 * CREATE TABLE goods(
	id INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(25)
	);
	��ʽһ��ʹ��Statement
	Connection conn=JDBCUtils.getConnection();
	Statement st=conn.createStatement();
	for(int i=1;i<=20000;i++){
		String sql="insert into goods(name) values('name_"+i+"')";
		st.execute(sql);
	}
 * */
public class InsertTest {
	
	//��������ķ�ʽ����ʹ��PreparedStatement
	@Test
	public void testInsert1()  {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			long start = System.currentTimeMillis();
			conn = JDBCUtils.getConnection();
			String sql="insert into goods(name) values(?)";
			ps = conn.prepareStatement(sql);
			for(int i=1;i<=20000;i++) {
				ps.setObject(1, "name_"+i);
				
				ps.execute();
			}
			long end = System.currentTimeMillis();
			System.out.println("����ʱ��Ϊ"+(end-start));//20000 -> 91124
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JDBCUtils.closeResource(conn, ps);
	}
	
	/*
	 * ��������ķ�ʽ����
	 * 1.addBatch()��executeBatch()��clearBatch()
	 * 2.mysql������Ĭ���ǹرմ���ģ�������Ҫͨ��һ����������mysql�����������֧�֡�
	 * 		?rewriteBatchedStatements=true д�������ļ���url����
	 * 3.ʹ�ø��µ�mysql������mysql-connector-java-8.0.27.jar
	 * 
	 * */
	@Test
	public void testInsert2()  {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			long start = System.currentTimeMillis();
			conn = JDBCUtils.getConnection();
			String sql="insert into goods(name) values(?)";
			ps = conn.prepareStatement(sql);
			for(int i=1;i<=1000000;i++) {
				ps.setObject(1, "name_"+i);
				
				//1.���ܡ�sql
				ps.addBatch();
				
				if(i%500==0) {
					//2.ִ��batch
					ps.executeBatch();
					
					//3.���batch
					ps.clearBatch();
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("����ʱ��Ϊ"+(end-start));//20000 -> 1210
														//1000000 -> 18668
														
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JDBCUtils.closeResource(conn, ps);
	}
	
	/*
	 * ��������ķ�ʽ�ģ��������Ӳ������Զ��ύ����
	 * */
	@Test
	public void testInsert3()  {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			long start = System.currentTimeMillis();
			conn = JDBCUtils.getConnection();
			
			//���ò������Զ��ύ����
			conn.setAutoCommit(false);
			
			
			String sql="insert into goods(name) values(?)";
			ps = conn.prepareStatement(sql);
			for(int i=1;i<=1000000;i++) {
				ps.setObject(1, "name_"+i);
				
				//1.���ܡ�sql
				ps.addBatch();
				
				if(i%500==0) {
					//2.ִ��batch
					ps.executeBatch();
					
					//3.���batch
					ps.clearBatch();
				}
			}
			
			//�ύ����
			conn.commit();
			
			long end = System.currentTimeMillis();
			System.out.println("����ʱ��Ϊ"+(end-start));//20000 -> 91124 -> 1210
														//1000000 -> 18668  ->13401
														
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JDBCUtils.closeResource(conn, ps);
	}
}
