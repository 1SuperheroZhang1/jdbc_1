package com.atguigu4.exer;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import org.junit.Test;

import com.atguigu3.utils.JDBCUtils;

//�κ���ϰ2
public class Exer2Test {
	
	//����1����examstudent�������һ����¼
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
		System.out.print("�ļ�/������");
		int type = scanner.nextInt();
		System.out.print("���֤�ţ�");
		String IDCard = scanner.next();
		System.out.print("׼��֤�ţ�");
		String ExamCard = scanner.next();
		System.out.print("ѧ��������");
		String StudentName = scanner.next();
		System.out.print("���ڳ��У�");
		String Location = scanner.next();
		System.out.print("ѧ���ɼ���");
		int grade = scanner.nextInt();
		
		String sql="insert into examstudent(type,IDCard,ExamCard,StudentName,Location,grade) values(?,?,?,?,?,?)";
		int insertCount = update(sql,type,IDCard,ExamCard,StudentName,Location,grade);
		if(insertCount>0) {
			System.out.println("��ӳɹ�");
		}else {
			System.out.println("���ʧ��");
		}
	}
	
	//����2���������֤�Ż�׼��֤�Ų�ѯѧ���ɼ���Ϣ
	@Test
	public void queryWithIDCardOrExamCard(){
		System.out.println("��������Ҫ��������ͣ�");
		System.out.println("a.׼��֤��");
		System.out.println("b.���֤��");
		Scanner scanner=new Scanner(System.in);
		String selection = scanner.next();
		if("a".equalsIgnoreCase(selection)) {
			System.out.print("������׼��֤�ţ�");
			String examCard = scanner.next();
			String sql="select FlowID,Type type,IDCard,ExamCard,StudentName,Location,Grade grade from examstudent where ExamCard=? ";
			Student student = getInstance(Student.class,sql,examCard);
			if(student!=null) {
				System.out.println(student);	
			}else {
				System.out.println("�����׼��֤������");
			}
		}else if("b".equalsIgnoreCase(selection)) {
			System.out.print("���������֤�ţ�");
			String idCard = scanner.next();
			String sql="select FlowID,Type type,IDCard,ExamCard,StudentName,Location,Grade grade from examstudent where idCard=? ";
			Student student = getInstance(Student.class,sql,idCard);
			if(student!=null) {
				System.out.println(student);	
			}else {
				System.out.println("��������֤������");
			}
		}else {
			System.out.println("�������������������������");
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
	
	//ͨ�õ���ɾ�Ĳ���
	public int update(String sql,Object ...args) {//sql��ռλ���ĸ�����ɱ��Բҵĳ�����ͬ��
		
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
			/*
			 * ps.execute();
			 * ���ִ�е��ǲ�ѯ�������з��ؽ������˷�������true��
			 * ���ִ�е�������ɾ���Ĳ�����û�з��ؽ������˷�������false;
			 * */
			//��ʽһ��
//			return ps.execute();
			//��ʽ����
			return ps.executeUpdate();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//5.��Դ�Ĺر�
			JDBCUtils.closeResource(conn, ps);
		}
		return 0;
	}
	
	//����3��ɾ��ָ����ѧ����Ϣ
	@Test
	public void testDeleteByExamCard() {
		System.out.print("������ѧ�����ţ�");
		Scanner scanner=new Scanner(System.in);
		String examCard = scanner.next();
		//��ѯָ��׼��֤�ŵ�ѧ����Ϣ
		String sql="select FlowID,Type type,IDCard,ExamCard,StudentName,Location,Grade grade from examstudent where ExamCard=? ";
		Student student = getInstance(Student.class,sql,examCard);
		if(student==null) {
			System.out.println("���޴��ˣ�����������׼��֤�ţ�");
		}else {
			String sql1="delete from examStudent where examCard=?";
			int deleteCount = update(sql1,examCard);
			if(deleteCount>0) {
				System.out.println("ɾ���ɹ���");
			}
		}
	}
	//�Ż���Ĳ�����
	@Test
	public void testDeleteByExamCard1() {
		System.out.print("������ѧ�����ţ�");
		Scanner scanner=new Scanner(System.in);
		String examCard = scanner.next();
		String sql="delete from examStudent where examCard=?";
		int deleteCount = update(sql,examCard);
		if(deleteCount>0) {
			System.out.println("ɾ���ɹ���");
		}else {
			System.out.println("���޴��ˣ�����������׼��֤�ţ�");
		}
	}
}
