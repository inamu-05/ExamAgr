package bean;

import java.io.Serializable;

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
	 * テスト回数:int
	 */
	private int no;

	/**
	 * 得点:int
	 */
	private Integer point;


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

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}


}