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
 *
 * 学生情報登録フォームに必要なデータ（クラス一覧・入学年度リスト）を準備し、
 * JSPに渡して登録画面に遷移させる処理。
 */
public class StudentCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        // セッションからログインユーザー（Teacher）を取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインまたは認証エラー時はログイン画面へリダイレクト
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // 所属学校情報を取得
        School school = teacher.getSchool();

        // ==============================
        // クラス一覧を取得（プルダウン表示用）
        // ==============================
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school);

        // ==============================
        // 入学年度リストの生成（2020年〜現在＋10年）
        // ==============================
        List<Integer> entYearList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int year = currentYear - 10; year <= currentYear + 10; year++) {
            entYearList.add(year);
        }

        // ==============================
        // JSPに渡すデータをリクエスト属性に設定
        // ==============================
        req.setAttribute("classNumList", classNumList); // クラス一覧
        req.setAttribute("entYearList", entYearList);   // 入学年度一覧

        // ==============================
        // 学生登録画面にフォワード（表示遷移）
        // ==============================
        req.getRequestDispatcher("student_create.jsp").forward(req, res);
    }
}
