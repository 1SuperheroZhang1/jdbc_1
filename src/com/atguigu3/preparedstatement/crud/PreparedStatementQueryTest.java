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
 * @Description 使用PreparedStatement实现针对于不同表的通用的查询操作
 * @author 张先森
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
  * @Description 针对于不同的表的通用的查询操作，返回表中的一条记录
  * @author 张先森
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
			//获取结果集的元数据：ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			//通过ResultSetMetaData获取结果集中的列数
			int columnCount = rsmd.getColumnCount();
			
			if(rs.next()) {
				T t = clazz.newInstance();
				
				for(int i=0;i<columnCount;i++) {
					
					//获取每个列的列值：通过ResultSet
					Object columnValue = rs.getObject(i+1);
					
					//通过ResultSetMetaData
					//获取每个列的列名：getColumnName() -- 不推荐使用
					//获取每个列的别名：getColumnLabel()
//					String columnName = rsmd.getColumnName(i+1);
					String columnLabel = rsmd.getColumnLabel(i+1);
					//给course对象指定的columnName属性，赋值为columnValue：通过反射
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
			//获取结果集的元数据：ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			//通过ResultSetMetaData获取结果集中的列数
			int columnCount = rsmd.getColumnCount();
			
			//创建集合对象
			ArrayList<T> list = new ArrayList<T>();
			while(rs.next()) {
				T t = clazz.newInstance();
				//处理结果集一行数据中的每一列：给t对象指定的属性赋值
				for(int i=0;i<columnCount;i++) {
					
					//获取每个列的列值：通过ResultSet
					Object columnValue = rs.getObject(i+1);
					
					//通过ResultSetMetaData
					//获取每个列的列名：getColumnName() -- 不推荐使用
					//获取每个列的别名：getColumnLabel()
//					String columnName = rsmd.getColumnName(i+1);
					String columnLabel = rsmd.getColumnLabel(i+1);
					//给course对象指定的columnName属性，赋值为columnValue：通过反射
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
