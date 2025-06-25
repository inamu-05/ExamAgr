package bean;

import java.io.Serializable;

public class Student extends User implements Serializable {
	/**
	 * 学生番号:String
	 */
	private String no;

	/**
	 * 学生名:String
	 */
	private String name;

	/**
	 * 年度:String
	 */
	private int ent_year;

	/**
	 * クラス番号:String
	 */
	private String class_num;

	/**
	 * 在籍チェック:String
	 */
	private boolean is_attend;

	/**
	 * 所属校:School
	 */
	private School school;

	/**
	 * ゲッター・セッター
	 */
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEntYear() {
		return ent_year;
	}

	public void setEntYear(int ent_year) {
		this.ent_year = ent_year;
	}

	public String getClassNum() {
		return class_num;
	}

	public void setClassNum(String class_num) {
		this.class_num = class_num;
	}

	public boolean getisAttend() {
		return is_attend;
	}

	public void setisAttend(boolean is_attend) {
		this.is_attend = is_attend;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}
