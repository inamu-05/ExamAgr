package bean;

import java.io.Serializable;

public class TestListStudent extends User implements Serializable {
	/**
	 * 学生番号:String
	 */
	private String subjectName;

	/**
	 * 学生名:String
	 */
	private String subjectCd;

	/**
	 * 年度:String
	 */
	private int num;

	/**
	 * クラス番号:String
	 */
	private int point;



	/**
	 * ゲッター・セッター
	 */
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}