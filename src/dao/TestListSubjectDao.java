package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

/**
 * 科目ごとの成績一覧を取得するDAOクラス。
 * 複数回のテスト結果を統合し、学生単位で集約する。
 */
public class TestListSubjectDao extends Dao {

    /**
     * ResultSetの内容をTestListSubjectのリストに変換する。
     * 同一学生の複数回テストの得点を1オブジェクトに統合。
     *
     * @param rSet   SQL実行結果
     * @param school 学校情報（現状では使用されていないが将来用途を想定）
     * @return TestListSubjectのリスト
     * @throws Exception パース中のエラー
     */
    public List<TestListSubject> postFilter(ResultSet rSet, School school) throws Exception {
        // 学生番号をキーにして統合（順序保持のため LinkedHashMap）
        Map<String, TestListSubject> map = new LinkedHashMap<>();

        while (rSet.next()) {
            String studentNo = rSet.getString("no");   // 学生番号
            int times = rSet.getInt("times");          // テスト回数
            int point = rSet.getInt("point");          // 点数

            // すでにマップに存在するか確認
            TestListSubject testlitSub = map.get(studentNo);

            if (testlitSub == null) {
                // 新しい学生情報として初期化
                testlitSub = new TestListSubject();
                testlitSub.setEntYear(rSet.getInt("ent_year"));         // 入学年度
                testlitSub.setStudentNo(studentNo);                     // 学生番号
                testlitSub.setStudentName(rSet.getString("student_name")); // 氏名
                testlitSub.setClassNum(rSet.getString("class_num"));    // クラス番号
                map.put(studentNo, testlitSub);
            }

            // 指定された回数に対応する点数をセット
            testlitSub.setPoint(times, point);
        }

        // Map → List に変換して返却
        return new ArrayList<>(map.values());
    }

    /**
     * 指定された条件（年度・クラス・科目）に該当する成績一覧を取得する。
     *
     * @param entYear 入学年度
     * @param claN    クラス番号
     * @param subject 対象科目
     * @param school  学校情報
     * @return TestListSubjectのリスト
     * @throws Exception DB操作エラー
     */
    public List<TestListSubject> filter(int entYear, String claN, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();

        // データベース接続の取得
        Connection con = getConnection();

        try {
            // 対象学生と科目の成績情報を取得するSQL
            PreparedStatement statement = con.prepareStatement(
                "SELECT student.ent_year, test.class_num, student.no, student.name AS student_name, " +
                "test.no AS times, test.point " +
                "FROM test " +
                "JOIN student ON test.student_no = student.no " +
                "JOIN subject ON test.subject_cd = subject.cd " +
                "WHERE student.ent_year = ? AND test.class_num = ? AND test.subject_cd = ? AND test.school_cd = ? " +
                "ORDER BY student.no, test.no"
            );

            // プレースホルダにパラメータをバインド
            statement.setInt(1, entYear);
            statement.setString(2, claN);
            statement.setString(3, subject.getCd());
            statement.setString(4, school.getCd());

            // 実行 → 結果を整形
            ResultSet resultSet = statement.executeQuery();
            list = postFilter(resultSet, school); // ResultSetを加工してリスト化

            // リソース解放
            statement.close();
        } finally {
            // コネクションを確実に閉じる
            con.close();
        }

        return list;
    }
}
