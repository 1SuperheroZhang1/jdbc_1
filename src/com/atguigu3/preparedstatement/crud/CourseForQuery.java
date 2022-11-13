package com.atguigu3.preparedstatement.crud;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.Test;

import com.atguigu3.utils.JDBCUtils;
import com.atguigu3.bean.Course;

/**
 * @Description �����course��Ĳ�ѯ����
 * */
public class CourseForQuery {
	@Test
	public void testQuery1()  {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet resultSet=null;
		try {
			conn = JDBCUtils.getConnection();
			String sql="select Cno,Cname,Cpno,Cpredit from course where Cno=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,"5");
			//ִ�У������ؽ����
			resultSet = ps.executeQuery();
			//��������
			if(resultSet.next()) {//next():�жϽ��������һ���Ƿ������ݣ���������ݷ���true����ָ�����ƣ��������false��ָ�벻�����ơ�
				//��ȡ��ǰ�������ݵĸ����ֶ�
				String Cno = resultSet.getString(1);
				String Cname = resultSet.getString(2);
				String Cpno = resultSet.getString(3);
				int Cpredit = resultSet.getInt(4);
				
				//��ʽһ��
//			System.out.println("Cno="+Cno+",Cname="+Cname+",Cpno="+Cpno+"Cpredit="+Cpredit);
				//��ʽ����
//			Object[] data=new Object[] {Cno,Cname,Cpno,Cpredit};
				//��ʽ���������ݷ�װΪһ������(�Ƽ�)
				Course course=new Course(Cno,Cname,Cpno,Cpredit);
				System.out.println(course);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//�ر���Դ
			JDBCUtils.closeResource(conn, ps, resultSet);	
		}
	}
	/**
	 * �����Course���ͨ�õĲ�ѯ����
	 * @throws Exception 
	 * */
/*
 * ��Ա���ֶ����������������һ�µ������
 * 1.��������sqlʱ��ʹ������������������ֶεı�����
 * 2.ʹ��ResultSetMetaDataʱ����Ҫʹ��getColumnLabel()���滻getColumnName()����ȡ�еı�����
 * ˵�������sql��û�и��ֶ��������getColumnLabel()��ȡ�ľ���������
 * */
	public Course QueryForCourse(String sql,Object ...args) {
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
				Course course=new Course();
				
				for(int i=0;i<columnCount;i++) {
					
					//��ȡÿ���е���ֵ��ͨ��ResultSet
					Object columnValue = rs.getObject(i+1);
					
					//ͨ��ResultSetMetaData
					//��ȡÿ���е�������getColumnName() -- ���Ƽ�ʹ��
					//��ȡÿ���еı�����getColumnLabel()
//					String columnName = rsmd.getColumnName(i+1);
					String columnLabel = rsmd.getColumnLabel(i+1);
					//��course����ָ����columnName���ԣ���ֵΪcolumnValue��ͨ������
					Field field = Course.class.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(course, columnValue);
				}
				return course;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
	@Test
	public void testQueryForCourse() {
		String sql="select Cno,Cname,Cpno,Cpredit from course where Cno=? ";
		Course course = QueryForCourse(sql,"6");
		System.out.println(course);
	}
}
