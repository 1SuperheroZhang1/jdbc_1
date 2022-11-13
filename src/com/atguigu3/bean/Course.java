package com.atguigu3.bean;

/**
 * ORM编程思想(Object Relational Mapping):对象关系映射
 * 一个数据表对应一个java类
 * 表中的一条记录对应java类中的一个对象
 * 表中的一个字段对应java类中的一个属性
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
