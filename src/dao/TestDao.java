package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

/**
* 成績情報（Test）を扱うDAOクラス。
* データベースからの取得・保存・フィルタ処理を担当。
*/
public class TestDao extends Dao {

    /** ベースSQL（WHERE句を追加して利用） */
//    private final String baseSql = "SELECT * FROM test WHERE 1=1";

    /**
     * 指定された学生・科目・学校・回数に一致する成績情報を1件取得する。
     *
     * @param student 学生情報
     * @param subject 科目情報
     * @param school 学校情報
     * @param no 試験回数
     * @return 該当するTestオブジェクト（存在しない場合はnull）
     * @throws Exception DBアクセス例外
     */
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
	    try (Connection connection = getConnection()) {
	    	String sql = "SELECT t.*, s.name AS student_name" +
	    			"FROM test t" +
	    			"JOIN student s ON t.student_no = s.no" +
	    			"WHERE t.student_no = ? AND t.subject_cd = ? AND t.school_cd = ? AND t.no = ?";

	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setString(1, student.getNo());
	            stmt.setString(2, subject.getCd());
	            stmt.setString(3, school.getCd());
	            stmt.setInt(4, no);

	            ResultSet rs = stmt.executeQuery();
	            List<Test> list = postFilter(rs, school, subject, no);
	            if (!list.isEmpty()) {
	                return list.get(0);
	            }
	        }
	    }

	    return null; // データが存在しない場合は null を返す
	}


	//（Connectionを渡せる安全な内部用）
	private Test get(Student student, Subject subject, School school, int no, Connection connection) throws Exception {
	    String sql = "SELECT t.*, s.name AS student_name " +
	    	    "FROM test t " +
	    	    "JOIN student s ON t.student_no = s.no " +
	    	    "WHERE t.student_no = ? AND t.subject_cd = ? AND t.school_cd = ? AND t.no = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, student.getNo());
	        stmt.setString(2, subject.getCd());
	        stmt.setString(3, school.getCd());
	        stmt.setInt(4, no);

	        ResultSet rs = stmt.executeQuery();
	        List<Test> list = postFilter(rs, school, subject, no);
	        if (!list.isEmpty()) {
	            return list.get(0);
	        }
	    }
	    return null;
	}



    /**
     * ResultSetからTestオブジェクトのリストを生成する。
     *
     * @param rSet SQL実行結果
     * @param school 学校情報
     * @return Testオブジェクトのリスト
     * @throws Exception DBアクセス例外
     */

    public List<Test> postFilter(ResultSet rSet, School school, Subject subject, int num) throws Exception {
    	List<Test> list = new ArrayList<>();

    	while (rSet.next()) {
    		Test test = new Test();

    		Student student = new Student();
    		student.setNo(rSet.getString("student_no"));
    		student.setName(rSet.getString("student_name"));
    		test.setStudent(student);

    		test.setClassNum(rSet.getString("class_num"));
    		test.setSubject(subject);
    		test.setSchool(school);
    		test.setNo(num);

    		// 得点が未登録の場合は 0 にする
    		Object pointObj = rSet.getObject("point");
    		if (pointObj != null) {
    		    test.setPoint(rSet.getInt("point"));
    		} else {
    		    test.setPoint(null);
    		}
    		list.add(test);
    	}

    	return list;
    }


    /**
     * 指定された条件に一致する成績情報を複数取得する。
     *
     * @param entYear 入学年度
     * @param classNum クラス番号
     * @param subject 科目情報
     * @param num 試験回数
     * @param school 学校情報
     * @return 条件に一致するTestオブジェクトのリスト
     * @throws Exception DBアクセス例外
     */


    public List<Test> filter(Integer entYear, String classNum, Subject subject, Integer num, School school) throws Exception {
    	List<Test> list = new ArrayList<>();

    	try (Connection connection = getConnection()) {
    		String sql =
    				"SELECT s.no AS student_no, s.name AS student_name, s.class_num, " +
    				"t.point, t.subject_cd, t.school_cd, t.no " +
    				"FROM student s " +
    				"LEFT JOIN test t ON s.no = t.student_no AND t.subject_cd = ? AND t.school_cd = ? AND t.no = ? " +
    				"WHERE s.ent_year = ? AND s.class_num = ?";


    		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
    			stmt.setString(1, subject.getCd());
    			stmt.setString(2, school.getCd());
    			stmt.setInt(3, num);
    			stmt.setInt(4, entYear);
    			stmt.setString(5, classNum);

    			ResultSet rs = stmt.executeQuery();
    			list = postFilter(rs, school, subject, num);
    		}
    	}

    	return list;
    }



    /**
     * 成績情報のリストを一括保存する。
     *
     * @param list 保存対象のTestオブジェクトのリスト
     * @return 保存成功ならtrue、失敗ならfalse
     * @throws Exception DBアクセス例外
     */

    public boolean save(List<Test> list) throws Exception {
    	try (Connection connection = getConnection()) {
    		for (Test test : list) {
    			if (!save(test, connection)) {
    				return false;
    			}
    		}
    	}
    	return true;
    }

    /**
     * 単一の成績情報を保存または更新する。
     *
     * @param test 保存対象のTestオブジェクト
     * @param connection DB接続
     * @return 保存成功ならtrue、失敗ならfalse
     * @throws Exception DBアクセス例外
     */

    public boolean save(Test test, Connection connection) throws Exception {
    	Test existing = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo(), connection);
        String sql;
        if (existing != null) {
            // UPDATE
            sql = "UPDATE test SET point = ?, class_num = ? WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            	if (test.getPoint() == null) {
            	    stmt.setNull(1, java.sql.Types.INTEGER);
            	} else {
            	    stmt.setInt(1, test.getPoint());
            	}
                stmt.setString(2, test.getClassNum());
                stmt.setString(3, test.getStudent().getNo());
                stmt.setString(4, test.getSubject().getCd());
                stmt.setString(5, test.getSchool().getCd());
                stmt.setInt(6, test.getNo());

                int rows = stmt.executeUpdate();
                return rows > 0;
            }
        } else {
            // INSERT
            sql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, test.getStudent().getNo());
                stmt.setString(2, test.getSubject().getCd());
                stmt.setString(3, test.getSchool().getCd());
                stmt.setInt(4, test.getNo());
                if (test.getPoint() == null) {
                    stmt.setNull(5, java.sql.Types.INTEGER);
                } else {
                    stmt.setInt(5, test.getPoint());
                }
                stmt.setString(6, test.getClassNum());

                int rows = stmt.executeUpdate();
                return rows > 0;
            }
        }
//        System.out.println("Saving: " + test.getStudent().getNo() + ", " + test.getPoint());
    }

}