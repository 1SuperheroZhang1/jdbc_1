package com.atguigu4.exer;

public class Student {
	private int FlowID;//流水号
	private int type;//考试类型
	private String IDCard;//身份证号
	private String ExamCard;//准考证号
	private String StudentName;//学生姓名
	private String Location;//所在城市
	private int grade;//成绩
	public Student() {
		super();
	}
	
	public Student(int flowID, int type, String iDCard, String examCard, String studentName, String location,
			int grade) {
		super();
		FlowID = flowID;
		this.type = type;
		IDCard = iDCard;
		ExamCard = examCard;
		StudentName = studentName;
		Location = location;
		this.grade = grade;
	}
	public int getFlowID() {
		return FlowID;
	}
	public void setFlowID(int flowID) {
		FlowID = flowID;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIDCard() {
		return IDCard;
	}
	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}
	public String getExamCard() {
		return ExamCard;
	}
	public void setExamCard(String examCard) {
		ExamCard = examCard;
	}
	public String getStudentName() {
		return StudentName;
	}
	public void setStudentName(String studentName) {
		StudentName = studentName;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		System.out.println("=========查询结果=========");
		return info();
	}

	private String info() {
		// TODO Auto-generated method stub
		return "流水号："+FlowID+"\n四级/六级："+type+"\n身份证号："+IDCard+"\n准考证号："
				+ExamCard+"\n学生姓名："+StudentName+"\n区域："+Location+"\n成绩："+grade;
	}
	
	
}
