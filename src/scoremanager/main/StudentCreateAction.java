package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

/**
 * 学生情報登録画面表示アクション
 * 学生情報登録フォームに必要なデータ（クラス一覧・入学年度リスト）を準備し、登録画面に遷移する
 */
public class StudentCreateAction extends Action {

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
        List<String> classNumList = classNumDao.filter(school);

        // ==============================
        // 入学年度リスト生成（プルダウン用）
        // 2020年～現在の西暦までをリスト化
        // ==============================
        List<Integer> entYearList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);  // 現在の西暦を取得
        for (int year = 2020; year <= currentYear; year++) {
            entYearList.add(year);
        }

        // ==============================
        // JSPに渡すデータをリクエストにセット
        // ==============================
        req.setAttribute("classNumList", classNumList); // クラス一覧（プルダウン用）
        req.setAttribute("entYearList", entYearList);   // 入学年度一覧（プルダウン用）

        // 学生登録画面にフォワード
        req.getRequestDispatcher("student_create.jsp").forward(req, res);
    }
}
