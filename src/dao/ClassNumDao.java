package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

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
