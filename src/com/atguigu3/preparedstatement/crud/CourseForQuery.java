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
 * @Description 针对于course表的查询操作
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
			//执行，并返回结果集
			resultSet = ps.executeQuery();
			//处理结果集
			if(resultSet.next()) {//next():判断结果集的下一条是否有数据，如果有数据返回true，并指针下移；如果返回false，指针不会下移。
				//获取当前这条数据的各个字段
				String Cno = resultSet.getString(1);
				String Cname = resultSet.getString(2);
				String Cpno = resultSet.getString(3);
				int Cpredit = resultSet.getInt(4);
				
				//方式一：
//			System.out.println("Cno="+Cno+",Cname="+Cname+",Cpno="+Cpno+"Cpredit="+Cpredit);
				//方式二：
//			Object[] data=new Object[] {Cno,Cname,Cpno,Cpredit};
				//方式三：将数据封装为一个对象(推荐)
				Course course=new Course(Cno,Cname,Cpno,Cpredit);
				System.out.println(course);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//关闭资源
			JDBCUtils.closeResource(conn, ps, resultSet);	
		}
	}
	/**
	 * 针对于Course表的通用的查询操作
	 * @throws Exception 
	 * */
/*
 * 针对表的字段名与类的属性名不一致的情况：
 * 1.必须声明sql时，使用类的属性名来明明字段的别名。
 * 2.使用ResultSetMetaData时，需要使用getColumnLabel()来替换getColumnName()，获取列的别名。
 * 说明：如果sql中没有给字段起别名，getColumnLabel()获取的就是列名。
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
			//获取结果集的元数据：ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			//通过ResultSetMetaData获取结果集中的列数
			int columnCount = rsmd.getColumnCount();
			
			if(rs.next()) {
				Course course=new Course();
				
				for(int i=0;i<columnCount;i++) {
					
					//获取每个列的列值：通过ResultSet
					Object columnValue = rs.getObject(i+1);
					
					//通过ResultSetMetaData
					//获取每个列的列名：getColumnName() -- 不推荐使用
					//获取每个列的别名：getColumnLabel()
//					String columnName = rsmd.getColumnName(i+1);
					String columnLabel = rsmd.getColumnLabel(i+1);
					//给course对象指定的columnName属性，赋值为columnValue：通过反射
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
