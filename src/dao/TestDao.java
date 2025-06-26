package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    /**
     * getメソッド 学生・科目・学校・番号を指定してテスト情報を1件取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = new Test();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "SELECT * FROM test WHERE student_id=? AND subject_cd=? AND school_cd=? AND no=?"
            );
            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setno(resultSet.getInt("no"));
                test.setpoint(resultSet.getInt("score"));
                // 他のフィールドも必要に応じてセット
            } else {
                test = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return test;
    }

    /**
     * saveメソッド テスト情報を保存する
     */
    public boolean save(Test test) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        boolean result = false;

        try {
            statement = connection.prepareStatement(
                "INSERT INTO test (student_id, subject_cd, school_cd, no, score) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, test.getStudent().getNo());
            statement.setString(2, test.getSubject().getCd());
            statement.setString(3, test.getSchool().getCd());
            statement.setInt(4, test.getno());
            statement.setInt(5, test.getpoint());

            result = statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return result;
    }
}
