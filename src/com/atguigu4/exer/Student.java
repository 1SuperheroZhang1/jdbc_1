package com.atguigu4.exer;

public class Student {
	private int FlowID;//��ˮ��
	private int type;//��������
	private String IDCard;//���֤��
	private String ExamCard;//׼��֤��
	private String StudentName;//ѧ������
	private String Location;//���ڳ���
	private int grade;//�ɼ�
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
		System.out.println("=========��ѯ���=========");
		return info();
	}

	private String info() {
		// TODO Auto-generated method stub
		return "��ˮ�ţ�"+FlowID+"\n�ļ�/������"+type+"\n���֤�ţ�"+IDCard+"\n׼��֤�ţ�"
				+ExamCard+"\nѧ��������"+StudentName+"\n����"+Location+"\n�ɼ���"+grade;
	}
	
	
}
