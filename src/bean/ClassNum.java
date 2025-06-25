package bean;

import java.io.Serializable;

public class ClassNum extends User implements Serializable {
	/**
	 * クラス番号:String
	 */
	private String class_num;

	/**
	 * 所属校:School
	 */
	private School school;

	/**
	 * ゲッター・セッター
	 */
	public String getClass_num() {
		return class_num;
	}

	public void setClass_num(String class_num) {
		this.class_num = class_num;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}
