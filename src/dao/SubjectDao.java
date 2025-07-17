package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

/**
 * 科目情報を扱うDAOクラス
 */
public class SubjectDao extends Dao {

    /**
     * 指定された学校の全科目一覧を取得する
     *
     * @param school 学校情報
     * @return 科目リスト
     */
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

    /**
     * 指定された科目コードと学校に一致する科目1件を取得
     *
     * @param cd 科目コード
     * @param school 学校情報
     * @return 科目情報（存在しない場合はnull）
     */
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

    /**
     * 科目情報を登録または更新する
     *
     * @param subject 科目情報
     * @return 処理成功時 true、失敗時 false
     */
    public boolean save(Subject subject) throws Exception {
        try (Connection con = getConnection()) {

            // 登録済みかどうかをチェック
            PreparedStatement chk = con.prepareStatement(
                "SELECT COUNT(*) FROM subject WHERE cd = ? AND school_cd = ?");
            chk.setString(1, subject.getCd());
            chk.setString(2, subject.getSchool().getCd());

            ResultSet rs = chk.executeQuery();
            rs.next();
            boolean exists = rs.getInt(1) > 0;
            chk.close();

            // INSERT or UPDATE のSQL文を用意
            String sql = exists
                ? "UPDATE subject SET name = ? WHERE cd = ? AND school_cd = ?"
                : "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            if (exists) {
                // UPDATE 用のパラメータ設定
                stmt.setString(1, subject.getName());
                stmt.setString(2, subject.getCd());
                stmt.setString(3, subject.getSchool().getCd());
            } else {
                // INSERT 用のパラメータ設定
                stmt.setString(1, subject.getCd());
                stmt.setString(2, subject.getName());
                stmt.setString(3, subject.getSchool().getCd());
            }

            int count = stmt.executeUpdate();
            stmt.close();

            return count > 0;
        }
    }

    /**
     * 指定された科目を削除する
     *
     * @param subject 削除対象の科目
     * @return 削除成功時 true、失敗時 false
     */
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
