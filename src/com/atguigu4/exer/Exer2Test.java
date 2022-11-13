package com.atguigu4.exer;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import org.junit.Test;

import com.atguigu3.utils.JDBCUtils;

//课后练习2
public class Exer2Test {
	
	//问题1：向examstudent表中添加一条记录
	/*
	 * 	Type:
		IDCard:
		ExamCard:
		StudentName:
		Location:
		Grade:
	 * */
	@Test
	public void testInsert() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("四级/六级：");
		int type = scanner.nextInt();
		System.out.print("身份证号：");
		String IDCard = scanner.next();
		System.out.print("准考证号：");
		String ExamCard = scanner.next();
		System.out.print("学生姓名：");
		String StudentName = scanner.next();
		System.out.print("所在城市：");
		String Location = scanner.next();
		System.out.print("学生成绩：");
		int grade = scanner.nextInt();
		
		String sql="insert into examstudent(type,IDCard,ExamCard,StudentName,Location,grade) values(?,?,?,?,?,?)";
		int insertCount = update(sql,type,IDCard,ExamCard,StudentName,Location,grade);
		if(insertCount>0) {
			System.out.println("添加成功");
		}else {
			System.out.println("添加失败");
		}
	}
	
	//问题2：根据身份证号或准考证号查询学生成绩信息
	@Test
	public void queryWithIDCardOrExamCard(){
		System.out.println("请输入您要输入的类型：");
		System.out.println("a.准考证号");
		System.out.println("b.身份证号");
		Scanner scanner=new Scanner(System.in);
		String selection = scanner.next();
		if("a".equalsIgnoreCase(selection)) {
			System.out.print("请输入准考证号：");
			String examCard = scanner.next();
			String sql="select FlowID,Type type,IDCard,ExamCard,StudentName,Location,Grade grade from examstudent where ExamCard=? ";
			Student student = getInstance(Student.class,sql,examCard);
			if(student!=null) {
				System.out.println(student);	
			}else {
				System.out.println("输入的准考证号有误！");
			}
		}else if("b".equalsIgnoreCase(selection)) {
			System.out.print("请输入身份证号：");
			String idCard = scanner.next();
			String sql="select FlowID,Type type,IDCard,ExamCard,StudentName,Location,Grade grade from examstudent where idCard=? ";
			Student student = getInstance(Student.class,sql,idCard);
			if(student!=null) {
				System.out.println(student);	
			}else {
				System.out.println("输入的身份证号有误！");
			}
		}else {
			System.out.println("您的输入有误，请重新输入程序。");
		}
	}
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
	
	//通用的增删改操作
	public int update(String sql,Object ...args) {//sql中占位符的个数与可变性惨的长度相同！
		
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
			/*
			 * ps.execute();
			 * 如果执行的是查询操作，有返回结果，则此方法返回true；
			 * 如果执行的是增、删、改操作，没有返回结果，则此方法返回false;
			 * */
			//方式一：
//			return ps.execute();
			//方式二：
			return ps.executeUpdate();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//5.资源的关闭
			JDBCUtils.closeResource(conn, ps);
		}
		return 0;
	}
	
	//问题3：删除指定的学生信息
	@Test
	public void testDeleteByExamCard() {
		System.out.print("请输入学生考号：");
		Scanner scanner=new Scanner(System.in);
		String examCard = scanner.next();
		//查询指定准考证号的学生信息
		String sql="select FlowID,Type type,IDCard,ExamCard,StudentName,Location,Grade grade from examstudent where ExamCard=? ";
		Student student = getInstance(Student.class,sql,examCard);
		if(student==null) {
			System.out.println("查无此人，请重新输入准考证号！");
		}else {
			String sql1="delete from examStudent where examCard=?";
			int deleteCount = update(sql1,examCard);
			if(deleteCount>0) {
				System.out.println("删除成功！");
			}
		}
	}
	//优化后的操作：
	@Test
	public void testDeleteByExamCard1() {
		System.out.print("请输入学生考号：");
		Scanner scanner=new Scanner(System.in);
		String examCard = scanner.next();
		String sql="delete from examStudent where examCard=?";
		int deleteCount = update(sql,examCard);
		if(deleteCount>0) {
			System.out.println("删除成功！");
		}else {
			System.out.println("查无此人，请重新输入准考证号！");
		}
	}
}
