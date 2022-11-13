package com.atguigu5.blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.atguigu3.utils.JDBCUtils;
import com.atguigu3.bean.Customer;

/**
 * @Description ����ʹ��PreparedStatement����Blob���͵�����
 * @author ����ɭ
 * @version
 * @date 
 * */
public class BlobTest {
	
	//�����ݱ�customer�в���Blob���͵��ֶ� - �����ݱ��в������������
	@Test
	public void testInsert() {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn = JDBCUtils.getConnection();
			String sql="insert into customer(name,email,birth,photo) values(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setObject(1, "����");
			ps.setObject(2, "zhangmi@126.com");
			ps.setObject(3, "2002-09-07");
			FileInputStream is=new FileInputStream(new File("photo.jpg"));
			ps.setBlob(4, is);
			
			ps.execute();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JDBCUtils.closeResource(conn, ps);
	}
	
	//��ѯ���ݱ�customer�е�Blob���͵��ֶ� - �����ݱ��ж�ȡ����������
	@Test
	public void testQuery() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		InputStream is =null;
		FileOutputStream fos=null;
		try {
			conn = JDBCUtils.getConnection();
			String sql="select id,name,email,birth,photo from customer where id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 2);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				//��ʽһ
//			int id = rs.getInt(1);
//			String name = rs.getString(2);
//			String email = rs.getString(3);
//			Date birth = rs.getDate(4);
				//��ʽ����
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date birth = rs.getDate("birth");
				
				Customer cust = new Customer(id,name,email,birth);
				System.out.println(cust);
				
				//��Blob���͵��ֶ��������������ļ��ķ�ʽ�����ڱ���
				Blob photo = rs.getBlob("photo");
				is = photo.getBinaryStream();
				fos=new FileOutputStream("zhangmi.jpg");
				byte[] buffer=new byte[1024];
				int len;
				while((len=is.read(buffer))!=-1) {
					fos.write(buffer,0,len);
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, ps, rs);
			try {
				if(is!=null)
				{					
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(fos!=null) {					
					fos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
