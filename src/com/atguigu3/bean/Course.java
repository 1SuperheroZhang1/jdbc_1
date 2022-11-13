package com.atguigu3.bean;

/**
 * ORM���˼��(Object Relational Mapping):�����ϵӳ��
 * һ�����ݱ��Ӧһ��java��
 * ���е�һ����¼��Ӧjava���е�һ������
 * ���е�һ���ֶζ�Ӧjava���е�һ������
 * */
public class Course {
	private String Cno;
	private String Cname;
	private String Cpno;
	private int Cpredit;
	
	
	public Course() {
		super();
	}


	public Course(String cno, String cname, String cpno, int cpredit) {
		super();
		Cno = cno;
		Cname = cname;
		Cpno = cpno;
		Cpredit = cpredit;
	}


	public String getCno() {
		return Cno;
	}


	public void setCno(String cno) {
		Cno = cno;
	}


	public String getCname() {
		return Cname;
	}


	public void setCname(String cname) {
		Cname = cname;
	}


	public String getCpno() {
		return Cpno;
	}


	public void setCpno(String cpno) {
		Cpno = cpno;
	}


	public int getCpredit() {
		return Cpredit;
	}


	public void setCpredit(int cpredit) {
		Cpredit = cpredit;
	}


	@Override
	public String toString() {
		return "Course [Cno=" + Cno + ", Cname=" + Cname + ", Cpno=" + Cpno + ", Cpredit=" + Cpredit + "]";
	}
	
	

}
