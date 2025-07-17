package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import tool.Action;

/**
 * 科目情報登録画面表示アクション
 * 科目登録画面に遷移する際の認証チェックと学校情報取得を行う
 */
public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションを取得
        HttpSession session = req.getSession();

        // セッションからログイン中のユーザー情報(Teacherインスタンス)を取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインまたは認証失敗時はログイン画面にリダイレクト
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // ログインユーザーの所属学校情報を取得（現時点では未使用だが、将来的に利用可能性あり）
        School school = teacher.getSchool();

        // 科目登録画面にフォワード（画面遷移）
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}
