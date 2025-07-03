package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import tool.Action;

/**
 * 学生情報登録画面表示アクション
 * 学生情報登録フォームに必要なデータ（クラス一覧・入学年度リスト）を準備し、登録画面に遷移する
 */
public class SubjectCreateAction extends Action {

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

        // 学生登録画面にフォワード
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}
