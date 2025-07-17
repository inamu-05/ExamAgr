package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

/**
 * 学生ごとの成績情報を取得するDAOクラス。
 * 成績一覧の取得・整形（ResultSet → Bean）を担当。
 */
public class TestListStudentDao extends Dao {

    /**
     * ResultSetの内容をTestListStudentのリストに変換する。
     *
     * @param resultSet SQL実行結果
     * @return 成績情報リスト
     * @throws Exception 変換時のエラー
     */
    public List<TestListStudent> postFilter(ResultSet resultSet) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        // 各行をBeanに詰め替え
        while (resultSet.next()) {
            TestListStudent tls = new TestListStudent();
            tls.setSubjectName(resultSet.getString("subject_name"));  // 科目名
            tls.setSubjectCd(resultSet.getString("subject_cd"));      // 科目コード
            tls.setNum(resultSet.getInt("no"));                       // テスト回数
            tls.setPoint(resultSet.getInt("point"));                  // 得点
            list.add(tls);
        }

        return list;
    }

    /**
     * 指定された学生の成績情報を取得する。
     *
     * @param student 対象学生オブジェクト
     * @return 成績情報リスト
     * @throws Exception DB操作エラー
     */
    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        // 学生の成績を取得するSQL
        String sql =
            "SELECT s.name AS subject_name, s.cd AS subject_cd, t.no, t.point " +
            "FROM test t " +
            "JOIN subject s ON t.subject_cd = s.cd " +
            "WHERE t.student_no = ? " +
            "ORDER BY s.name, t.no";

        // DB接続とステートメントの準備（自動クローズ）
        try (
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
        ) {
            // 学生番号をバインド
            statement.setString(1, student.getNo());

            // SQL実行 → 結果をBeanリストに変換
            try (ResultSet resultSet = statement.executeQuery()) {
                list = postFilter(resultSet);
            }
        }

        return list;
    }
}
