package com.atguigu3.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.atguigu3.utils.JDBCUtils;
import com.atguigu3.bean.Course;

/**
 * @Description ʹ��PreparedStatementʵ������ڲ�ͬ���ͨ�õĲ�ѯ����
 * @author ����ɭ
 * @version
 * */
public class PreparedStatementQueryTest {
	
	@Test
	public void testGetInstance() {
		String sql="select Cno,Cname,Cpno,Cpredit from course where Cno=? ";
		Course course = getInstance(Course.class,sql,5);
		System.out.println(course);
	}
 /**
  * @Description ����ڲ�ͬ�ı��ͨ�õĲ�ѯ���������ر��е�һ����¼
  * @author ����ɭ
  * @param clazz
  * @param sql
  * @param args
  * @return 
  * */
	public <T> T getInstance(Class<T> clazz,String sql,Object ...args) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = JDBCUtils.getConnection();
			
			ps = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++) {
				ps.setObject(i+1, args[i]);
			}
			
			rs = ps.executeQuery();
			//��ȡ�������Ԫ���ݣ�ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			//ͨ��ResultSetMetaData��ȡ������е�����
			int columnCount = rsmd.getColumnCount();
			
			if(rs.next()) {
				T t = clazz.newInstance();
				
				for(int i=0;i<columnCount;i++) {
					
					//��ȡÿ���е���ֵ��ͨ��ResultSet
					Object columnValue = rs.getObject(i+1);
					
					//ͨ��ResultSetMetaData
					//��ȡÿ���е�������getColumnName() -- ���Ƽ�ʹ��
					//��ȡÿ���еı�����getColumnLabel()
//					String columnName = rsmd.getColumnName(i+1);
					String columnLabel = rsmd.getColumnLabel(i+1);
					//��course����ָ����columnName���ԣ���ֵΪcolumnValue��ͨ������
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnValue);
				}
				return t;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
	
	public <T> List<T> getForList(Class<T> clazz,String sql,Object ...args){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = JDBCUtils.getConnection();
			
			ps = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++) {
				ps.setObject(i+1, args[i]);
			}
			
			rs = ps.executeQuery();
			//��ȡ�������Ԫ���ݣ�ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			//ͨ��ResultSetMetaData��ȡ������е�����
			int columnCount = rsmd.getColumnCount();
			
			//�������϶���
			ArrayList<T> list = new ArrayList<T>();
			while(rs.next()) {
				T t = clazz.newInstance();
				//��������һ�������е�ÿһ�У���t����ָ�������Ը�ֵ
				for(int i=0;i<columnCount;i++) {
					
					//��ȡÿ���е���ֵ��ͨ��ResultSet
					Object columnValue = rs.getObject(i+1);
					
					//ͨ��ResultSetMetaData
					//��ȡÿ���е�������getColumnName() -- ���Ƽ�ʹ��
					//��ȡÿ���еı�����getColumnLabel()
//					String columnName = rsmd.getColumnName(i+1);
					String columnLabel = rsmd.getColumnLabel(i+1);
					//��course����ָ����columnName���ԣ���ֵΪcolumnValue��ͨ������
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnValue);
				}
				 list.add(t);
			}
			return list;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
	@Test
	public void testGetForList() {
		String sql="select Cno,Cname,Cpno,Cpredit from course where Cno<?";
		List<Course> list = getForList(Course.class,sql,6);
		list.forEach(System.out::println);
	}
}
