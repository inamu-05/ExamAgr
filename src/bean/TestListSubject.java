package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestListSubject extends User implements Serializable {

    /**
     * 入学年度:int
     */
    private int entYear;

    /**
     * 学生番号:String
     */
    private String studentNo;

    /**
     * 学生名:String
     */
    private String studentName;

    /**
     * クラス番号:String
     */
    private String classNum;

    /**
     * 科目ごとの点数: Map<科目コード, 点数>
     */
    private Map<Integer, Integer> points = new HashMap<>();

    // ゲッター・セッター

    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Map<Integer, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }

    /**
     * 科目コードを指定して点数を取得
     */
    public Integer getPoint(int key) {
        return points.get(key);
    }

    /**
     * 科目コードと点数を指定して登録
     */
    public void setPoint(int key, int value) {
        this.points.put(key, value);
    }
}
