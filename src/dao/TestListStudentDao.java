package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    // ResultSet を Bean に詰め替える
    public List<TestListStudent> postFilter(ResultSet resultSet) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        while (resultSet.next()) {
            TestListStudent tls = new TestListStudent();
            tls.setSubjectName(resultSet.getString("subject_name"));
            tls.setSubjectCd(resultSet.getString("subject_cd"));
            tls.setNum(resultSet.getInt("no"));
            tls.setPoint(resultSet.getInt("point"));
            list.add(tls);
        }

        return list;
    }

    // 学生番号で検索して成績一覧を取得する
    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        // SQLクエリ
        String sql =
        	    "SELECT s.name AS subject_name, s.cd AS subject_cd, t.no, t.point " +
        	    "FROM test t " +
        	    "JOIN subject s ON t.subject_cd = s.cd " +
        	    "WHERE t.student_no = ? " +
        	    "ORDER BY s.name, t.no";

        // try-with-resources で close 自動化
        try (
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, student.getNo()); // パラメータ設定

            try (ResultSet resultSet = statement.executeQuery()) {
                list = postFilter(resultSet); // ResultSet をパース
            }
        }

        return list;
    }
}
