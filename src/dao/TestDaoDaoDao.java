package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDaoDaoDao extends Dao {
	/**
	 * getメソッド 学生IDを指定して学生インスタンスを1件取得する
	 *
	 * @param id:String
	 *            学生ID
	 * @return 学生クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */

	public Test get(Student student, Subject subject, School school, Integer no) throws Exception {

	    // 結果として返すTestインスタンス、最初はnull
	    Test test = null;

	    // データベース接続用
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        // SQL文の組み立て（studentテーブルとtestテーブルを内部結合）
	        String sql = "SELECT test.point, " +                      // 成績表の得点
	                     "student.ent_year, student.class_num, " +    // 学生表の入学年度とクラス
	                     "student.no AS student_no, student.name " +  // 学生番号と名前
	                     "FROM test " +
	                     "INNER JOIN student ON student.no = test.student_no " +  // studentテーブルとtestテーブルをstudent_noで結合
	                     "WHERE test.student_no = ? AND test.subject_cd = ?";     // 学生番号と科目コードで絞り込み

	        // SQL文をプリコンパイルして実行準備
	        statement = connection.prepareStatement(sql);

	        // プレースホルダーに値をバインド
	        statement.setString(1, student.getNo());   // 学生IDをセット
	        statement.setString(2, subject.getCd());   // 科目コードをセット（bean.SubjectにgetCd()メソッドがある想定）

	        // SQLを実行して結果を取得
	        ResultSet resultSet = statement.executeQuery();

	        // データが存在する場合
	        if (resultSet.next()) {
	            // Testインスタンスを生成
	            test = new Test();

	            // 学生情報を結果セットから取得し、studentオブジェクトに反映
	            student.setEntYear(resultSet.getInt("ent_year"));    // 入学年度
	            student.setClassNum(resultSet.getString("class_num"));// クラス番号
	            student.setNo(resultSet.getString("student_no"));    // 学生番号
	            student.setName(resultSet.getString("name"));        // 名前

	            // Testインスタンスに学生情報・科目情報・学校情報をセット
	            test.setStudent(student);
	            test.setSubject(subject);
	            test.setSchool(school);

	            // 得点をセット
	            test.setpoint(resultSet.getInt("point"));
	        }

	    } catch (Exception e) {
	        // 例外発生時はそのまま呼び出し元へ投げる
	        throw e;
	    } finally {
	        // ステートメントのクローズ処理（リソース解放）
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }

	        // コネクションのクローズ処理（リソース解放）
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }
	    // 最終的にTestインスタンスを返却（該当データがなければnull）
	    return test;
	}


	 public List<Test> postFilter(ResultSet rSet, School school) throws Exception {
	        List<Test> list = new ArrayList<>();

	        while (rSet.next()) {
	            Test test = new Test();
	            test.setStudent(rSet.getString(student.getno()));
	            test.setName(rSet.getString("name"));
	            test.setEntYear(rSet.getInt("ent_year"));
	            test.setClassNum(rSet.getString("class_num"));
	            test.setisAttend(rSet.getBoolean("is_attend"));
	            test.setSchool(school);

	            list.add(test);
	        }
	        return list;
	 }


	 public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
	        List<Test> list = new ArrayList<>();
	        Connection con = getConnection();
	        try{
	        	 PreparedStatement statement = con.prepareStatement(
	        			"SELECT * FROM test" +
						"INNER JOIN student ON student.no = test.student_no " +  // studentテーブルとtestテーブルをstudent_noで結合
						"WHERE ent_year = ? AND class_num = ? AND subject_cd = ? AND no = ? AND school_cd = ?"
	        			 );
	    		statement.setInt(1,entYear);
	    		statement.setString(2,classNum);
	    		statement.setSubject(3,subject);
	    		statement.setInt(4,num);
	    		statement.setSchool(5,school);

		        ResultSet resultSet = statement.executeQuery();
	            list = postFilter(resultSet, school);
	            statement.close();
	            }
	        finally {
				con.close();
	        }
	        return list;
	 }

	 public boolean save(List test) throws Exception {
		    Connection connection = null;
		    PreparedStatement statement = null;
		    boolean result = false;

		    try {
		        connection = getConnection();

		        // 学生が既に存在するか確認
		        String checkSql = "SELECT COUNT(*) FROM test WHERE no = ?";
		        statement = connection.prepareStatement(checkSql);
		        statement.setString(1, test.getNo());
		        ResultSet rs = statement.executeQuery();
		        boolean exists = false;
		        if (rs.next()) {
		            exists = rs.getInt(1) > 0;
		        }
		        rs.close();
		        statement.close();

		        if (exists) {
		            // UPDATE 処理
		            String updateSql = "UPDATE test "
		            		+ "SET name = ?, ent_year = ?, class_num = ?, is_attend = ? WHERE no = ?";
		            statement = connection.prepareStatement(updateSql);
		            statement.setString(1, test.getName());
		            statement.setInt(2, test.getEntYear());
		            statement.setString(3, test.getClassNum());
		            statement.setString(4, test.getNo());
		        } else {
		            // INSERT 処理
		            String insertSql = "INSERT INTO test "
		            		+ "(no, name, ent_year, class_num) VALUES (?, ?, ?, ?)";
		            statement = connection.prepareStatement(insertSql);
		            statement.setString(1, test.getNo());
		            statement.setString(2, test.getName());
		            statement.setInt(3, test.getEntYear());
		            statement.setString(4, test.getClassNum());
		        }

		        int rowsAffected = statement.executeUpdate();
		        result = rowsAffected > 0;

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