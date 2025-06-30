package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    // 科目一覧取得（学校単位）
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(
                 "SELECT cd, name FROM subject WHERE school_cd = ?")) {
            stmt.setString(1, school.getCd());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Subject s = new Subject();
                    s.setCd(rs.getString("cd"));
                    s.setName(rs.getString("name"));
                    s.setSchool(school);
                    list.add(s);
                }
            }
        }
        return list;
    }

    // 単一科目取得
    public Subject get(String cd, School school) throws Exception {
        Subject s = null;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(
                 "SELECT name FROM subject WHERE cd = ? AND school_cd = ?")) {
            stmt.setString(1, cd);
            stmt.setString(2, school.getCd());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    s = new Subject();
                    s.setCd(cd);
                    s.setName(rs.getString("name"));
                    s.setSchool(school);
                }
            }
        }
        return s;
    }


    // 保存・更新
    public boolean save(Subject subject) throws Exception {
        try (Connection con = getConnection()) {
            // 存在チェック
            PreparedStatement chk = con.prepareStatement(
                "SELECT COUNT(*) FROM subject WHERE cd = ? AND school_cd = ?");
            chk.setString(1, subject.getCd());
            chk.setString(2, subject.getSchool().getCd());
            ResultSet rs = chk.executeQuery();
            rs.next();
            boolean exists = rs.getInt(1) > 0;
            chk.close();

            String sql = exists
                ? "UPDATE subject SET name = ? WHERE cd = ? AND school_cd = ?"
                : "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            if (exists) {
                stmt.setString(1, subject.getName());
                stmt.setString(2, subject.getCd());
                stmt.setString(3, subject.getSchool().getCd());
            } else {
                stmt.setString(1, subject.getCd());
                stmt.setString(2, subject.getName());
                stmt.setString(3, subject.getSchool().getCd());
            }
            int count = stmt.executeUpdate();
            stmt.close();
            return count > 0;
        }
    }

    // 削除
    public boolean delete(Subject subject) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(
                "DELETE FROM subject WHERE cd = ? AND school_cd = ?")) {
            stmt.setString(1, subject.getCd());
            stmt.setString(2, subject.getSchool().getCd());
            return stmt.executeUpdate() > 0;
        }
    }

}


