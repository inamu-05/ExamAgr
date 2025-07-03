package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

/**
 * 成績情報（Test）を扱うDAOクラス。
 * データベースからの取得・保存・フィルタ処理を担当。
 */
public class TestDao extends Dao {

    /** ベースSQL（WHERE句を追加して利用） */
    private final String baseSql = "SELECT * FROM test WHERE 1=1";

    /**
     * 指定された学生・科目・学校・回数に一致する成績情報を1件取得する。
     *
     * @param student 学生情報
     * @param subject 科目情報
     * @param school 学校情報
     * @param no 試験回数
     * @return 該当するTestオブジェクト（存在しない場合はnull）
     * @throws Exception DBアクセス例外
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, student.getNo());
                stmt.setString(2, subject.getCd());
                stmt.setString(3, school.getCd());
                stmt.setInt(4, no);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    test = postFilter(rs, school).get(0); // 1件取得
                }
            }
        }

        return test;
    }

    /**
     * ResultSetからTestオブジェクトのリストを生成する。
     *
     * @param rSet SQL実行結果
     * @param school 学校情報（外部から渡される）
     * @return Testオブジェクトのリスト
     * @throws Exception DBアクセス例外
     */
    public List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();

        while (rSet.next()) {
            Test test = new Test();

            Student student = new Student();
            student.setNo(rSet.getString("student_no"));
            test.setStudent(student);

            Subject subject = new Subject();
            subject.setCd(rSet.getString("subject_cd"));
            test.setSubject(subject);

            test.setSchool(school);
            test.setNo(rSet.getInt("no"));
            test.setPoint(rSet.getInt("point"));
            test.setClassNum(rSet.getString("class_num"));

            list.add(test);
        }

        return list;
    }

    /**
     * 指定された条件に一致する成績情報を複数取得する。
     *
     * @param entYear 入学年度（※現状ではSQLに使用していない）
     * @param classNum クラス番号
     * @param subject 科目情報
     * @param num 試験回数
     * @param school 学校情報
     * @return 条件に一致するTestオブジェクトのリスト
     * @throws Exception DBアクセス例外
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = baseSql + " AND class_num = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, classNum);
                stmt.setString(2, subject.getCd());
                stmt.setString(3, school.getCd());
                stmt.setInt(4, num);

                ResultSet rs = stmt.executeQuery();
                list = postFilter(rs, school);
            }
        }

        return list;
    }

    /**
     * 成績情報のリストを一括保存する。
     *
     * @param list 保存対象のTestオブジェクトのリスト
     * @return 保存成功ならtrue、失敗ならfalse
     * @throws Exception DBアクセス例外
     */
    public boolean save(List<Test> list) throws Exception {
        try (Connection connection = getConnection()) {
            for (Test test : list) {
                if (!save(test, connection)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 単一の成績情報を保存する。
     *
     * @param test 保存対象のTestオブジェクト
     * @param connection DB接続
     * @return 保存成功ならtrue、失敗ならfalse
     * @throws Exception DBアクセス例外
     */
    public boolean save(Test test, Connection connection) throws Exception {
        String sql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, test.getStudent().getNo());
            stmt.setString(2, test.getSubject().getCd());
            stmt.setString(3, test.getSchool().getCd());
            stmt.setInt(4, test.getNo());
            stmt.setInt(5, test.getPoint());
            stmt.setString(6, test.getClassNum());

            int rows = stmt.executeUpdate();
            return rows > 0;
        }
    }
}
