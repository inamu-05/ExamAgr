package bean;

import java.io.Serializable;

import javax.security.auth.Subject;

public class Test implements Serializable {

	/**
	 * 学生コード:student
	 */
	private Student student;

	/**
	 * クラスナンバー:String
	 */
	private String classNum;


	/**
	 * 科目コード:Subject
	 */
	private Subject subject;

	/**
	 * 学校コード:School
	 */
	private School school;

	/**
	 * クラス名:int
	 */
	private int no;

	/**
	 * 得点:int
	 */
	private int point;


	/**
	 * ゲッター・セッター
	 */
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject  subject) {
		this.subject = subject;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public int getno() {
		return no;
	}

	public void setno(int no) {
		this.no = no;
	}

	public int getpoint() {
		return point;
	}

	public void setint(int point) {
		this.point = point;
	}


}
