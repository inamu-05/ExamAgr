package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import tool.Action;

/**
 * 学生情報登録画面表示アクション
 *
 * 学生情報登録フォームに必要なデータ（クラス一覧・入学年度リスト）を準備し、
 * JSPに渡して登録画面に遷移させる処理。
 */
public class ClassCreateAction extends Action {

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
        // 学生登録画面にフォワード（表示遷移）
        // ==============================
        req.getRequestDispatcher("class_create.jsp").forward(req, res);
    }
}
