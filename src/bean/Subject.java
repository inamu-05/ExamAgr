package bean;

import java.io.Serializable;

public class Subject implements Serializable {

	/**
	 * 学校コード:String
	 */
	private School school;

	/**
	 * 科目コード:String
	 */
	private String cd;

	/**
	 * 科目名:String
	 */
	private String name;

	/**
	 * ゲッター・セッター
	 */
	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
