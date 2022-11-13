package com.atguigu4.exer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import org.junit.Test;

import com.atguigu3.utils.JDBCUtils;

//�κ���ϰ1
public class Exer1Test {
	@Test
	public void testInsert() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("������γ̺ţ�");
		String Cno=scanner.next();
		System.out.print("������γ�����");
		String Cname=scanner.next();
		System.out.print("���������޿γ̺ţ�");
		String Cpno=scanner.next();
		System.out.print("������γ�ѧ�֣�");
		int Cpredit=scanner.nextInt();
		
		String sql="insert into course(Cno,Cname,Cpno,Cpredit) values(?,?,?,?)";
		int insertCount = update(sql,Cno,Cname,Cpno,Cpredit);
		if(insertCount>0) {
			System.out.println("��ӳɹ�");
		}else {
			System.out.println("���ʧ��");
		}
		
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
//					return ps.execute();
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
}
