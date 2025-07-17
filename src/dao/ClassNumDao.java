package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

/**
 * クラス番号に関するデータアクセスオブジェクト
 */
public class ClassNumDao extends Dao {

    /**
     * 指定されたクラス番号と学校コードに一致するクラス情報を1件取得
     *
     * @param class_num クラス番号
     * @param school 学校情報
     * @return 該当するClassNumオブジェクト（存在しない場合はnull）
     */
    public ClassNum get(String class_num, School school) throws Exception {
        ClassNum result = null;
        Connection con = getConnection();

        try {
            String sql = "SELECT * FROM class_num WHERE class_num = ? AND school_cd = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, class_num);
            st.setString(2, school.getCd());

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = new ClassNum();
                result.setClass_num(rs.getString("class_num"));

                School s = new School();
                s.setCd(rs.getString("school_cd"));
                result.setSchool(s);
            }

            st.close();
        } finally {
            con.close();
        }

        return result;
    }

    /**
     * 指定された学校に属するすべてのクラス番号を取得
     *
     * @param school 学校情報
     * @return クラス番号のリスト
     */
    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();
        Connection con = getConnection();

        try {
            String sql = "SELECT class_num FROM class_num WHERE school_cd = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, school.getCd());

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("class_num"));
            }

            st.close();
        } finally {
            con.close();
        }

        return list;
    }

    /**
     * クラス番号を新規登録
     *
     * @param classNum 登録対象のClassNumオブジェクト
     * @return 登録成功の場合 true、失敗の場合 false
     */
    public boolean save(ClassNum classNum) throws Exception {
        Connection con = getConnection();

        try {
            String sql = "INSERT INTO class_num (class_num, school_cd) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, classNum.getClass_num());
            st.setString(2, classNum.getSchool().getCd());

            int count = st.executeUpdate();
            st.close();

            return count > 0;
        } finally {
            con.close();
        }
    }

    /**
     * クラス番号を更新（変更前のクラス番号と学校コードをもとに特定）
     *
     * @param classNum 元のクラス番号情報
     * @param newClassNum 更新後のクラス番号
     * @return 更新成功の場合 true、失敗の場合 false
     */
    public boolean save(ClassNum classNum, String newClassNum) throws Exception {
        Connection con = getConnection();

        try {
            String sql = "UPDATE class_num SET class_num = ? WHERE class_num = ? AND school_cd = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, newClassNum);
            st.setString(2, classNum.getClass_num());
            st.setString(3, classNum.getSchool().getCd());

            int count = st.executeUpdate();
            st.close();

            return count > 0;
        } finally {
            con.close();
        }
    }
}
