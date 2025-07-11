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

public class TestListSubjectDao extends Dao {

    /**
     * 検索結果をtestlitSubリストに詰め替える（複数回の点数を統合）
     */
    public List<TestListSubject> postFilter(ResultSet rSet, School school) throws Exception {
        Map<String, TestListSubject> map = new LinkedHashMap<>();

        while (rSet.next()) {
            String studentNo = rSet.getString("no");
            int times = rSet.getInt("times");
            int point = rSet.getInt("point");

            TestListSubject testlitSub = map.get(studentNo);

            if (testlitSub == null) {
                testlitSub = new TestListSubject();
                testlitSub.setEntYear(rSet.getInt("ent_year"));
                testlitSub.setStudentNo(studentNo);
                testlitSub.setStudentName(rSet.getString("student_name"));
                testlitSub.setClassNum(rSet.getString("class_num"));
                map.put(studentNo, testlitSub);
            }

            // 回数ごとの点数をセット
            testlitSub.setPoint(times, point);
        }

        return new ArrayList<>(map.values());
    }

    /**
     * 学生・クラス・科目でテスト情報を取得
     */
    public List<TestListSubject> filter(int entYear, String claN, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        Connection con = getConnection();

        try {
            PreparedStatement statement = con.prepareStatement(
                "SELECT student.ent_year, test.class_num, student.no, student.name AS student_name, " +
                "test.no AS times, test.point " +
                "FROM test " +
                "JOIN student ON test.student_no = student.no " +
                "JOIN subject ON test.subject_cd = subject.cd " +
                "WHERE student.ent_year = ? AND test.class_num = ? AND test.subject_cd = ? AND test.school_cd = ? " +
                "ORDER BY student.no, test.no"
            );

            statement.setInt(1, entYear);
            statement.setString(2, claN);
            statement.setString(3, subject.getCd());
            statement.setString(4, school.getCd());

            ResultSet resultSet = statement.executeQuery();
            list = postFilter(resultSet, school); // ← postFilterをそのまま活用
            statement.close();
        } finally {
            con.close();
        }

        return list;
    }
}
