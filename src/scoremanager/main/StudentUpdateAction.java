package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

/**
 * 学生情報更新画面表示アクション
 * 指定された学生番号の情報を取得し、更新画面に渡す
 */
public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        // セッションからログイン中のユーザー情報(Teacherインスタンス)を取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインまたは認証失敗時は、ログイン画面にリダイレクト
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // ログインユーザーの所属学校情報を取得
        School school = teacher.getSchool();

        // ==============================
        // クラス一覧を取得（プルダウン用）
        // ==============================
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school); // 学校に紐づくクラス一覧

        // リクエストパラメータから学生番号を取得
        String no = req.getParameter("no");

        // 学生情報をデータベースから取得
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(no);

        // 学生情報が取得できなかった場合、エラー処理
        if (student == null) {
            req.setAttribute("message", "指定された学生情報が見つかりませんでした。");
            req.getRequestDispatcher("StudentCreate.action").forward(req, res);
            return;
        }

        // 取得した学生情報・クラス一覧をJSPに渡す
        req.setAttribute("student", student);
        req.setAttribute("classNumList", classNumList); // クラス一覧（プルダウン用）

        // 学生更新画面へ遷移
        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}
