package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

    /**
     * getメソッド 学生IDを指定して学生インスタンスを1件取得する
     */
    public Student get(String no) throws Exception {
        Student student = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM student WHERE no = ?");
            statement.setString(1, no);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                student = new Student();
                student.setNo(resultSet.getString("no"));
                student.setName(resultSet.getString("name"));
                student.setEntYear(resultSet.getInt("ent_year"));
                student.setClassNum(resultSet.getString("class_num"));
                student.setisAttend(resultSet.getBoolean("is_attend"));
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return student;
    }

    /**
     * 検索結果をStudentリストに詰め替える
     */
    public List<Student> postFilter(ResultSet resultSet, School school) throws Exception {
        List<Student> list = new ArrayList<>();

        while (resultSet.next()) {
            Student student = new Student();
            student.setNo(resultSet.getString("no"));
            student.setName(resultSet.getString("name"));
            student.setEntYear(resultSet.getInt("ent_year"));
            student.setClassNum(resultSet.getString("class_num"));
            student.setisAttend(resultSet.getBoolean("is_attend"));
            student.setSchool(school);

            list.add(student);
        }
        return list;
    }

    /**
     * 学校・入学年度・クラス・在学フラグで絞り込み
     */
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        Connection con = getConnection();

        try {
            PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM student WHERE school_cd = ? AND ent_year = ? AND class_num = ? AND is_attend = ? ORDER BY no"
            );
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setBoolean(4, isAttend);

            ResultSet resultSet = statement.executeQuery();
            list = postFilter(resultSet, school);
            statement.close();
        } finally {
            con.close();
        }

        return list;
    }

    /**
     * 学校・入学年度・在学フラグで絞り込み
     */
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        Connection con = getConnection();

        try {
            PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM student WHERE school_cd = ? AND ent_year = ? AND is_attend = ? ORDER BY no"
            );
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setBoolean(3, isAttend);

            ResultSet resultSet = statement.executeQuery();
            list = postFilter(resultSet, school);
            statement.close();
        } finally {
            con.close();
        }

        return list;
    }

    /**
     * 学校・在学フラグで絞り込み
     */
    public List<Student> filter(School school, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        Connection con = getConnection();

        try {
            PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM student WHERE school_cd = ? AND is_attend = ? ORDER BY no"
            );
            statement.setString(1, school.getCd());
            statement.setBoolean(2, isAttend);

            ResultSet resultSet = statement.executeQuery();
            list = postFilter(resultSet, school);
            statement.close();
        } finally {
            con.close();
        }

        return list;
    }

    /**
     * 学生情報をINSERTまたはUPDATE
     */
    public boolean save(Student student) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result = false;

        try {
            connection = getConnection();

            // 学生が既に存在するか確認
            String checkSql = "SELECT COUNT(*) FROM student WHERE no = ?";
            statement = connection.prepareStatement(checkSql);
            statement.setString(1, student.getNo());
            ResultSet rs = statement.executeQuery();
            boolean exists = false;
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
            rs.close();
            statement.close();

            if (exists) {
                // UPDATE処理
                String updateSql = "UPDATE student SET name = ?, ent_year = ?, class_num = ?, is_attend = ?, school_cd = ? WHERE no = ?";
                statement = connection.prepareStatement(updateSql);
                statement.setString(1, student.getName());
                statement.setInt(2, student.getEntYear());
                statement.setString(3, student.getClassNum());
                statement.setBoolean(4, student.getisAttend());
                statement.setString(5, student.getSchool().getCd());
                statement.setString(6, student.getNo());
            } else {
                // INSERT処理
                String insertSql = "INSERT INTO student (no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(insertSql);
                statement.setString(1, student.getNo());
                statement.setString(2, student.getName());
                statement.setInt(3, student.getEntYear());
                statement.setString(4, student.getClassNum());
                statement.setBoolean(5, student.getisAttend());
                statement.setString(6, student.getSchool().getCd());
            }

            int rowsAffected = statement.executeUpdate();
            result = rowsAffected > 0;

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return result;
    }
}
