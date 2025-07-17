package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

/**
 * 科目削除確認画面表示アクション
 * パラメータから科目コードを受け取り、
 * 科目情報を取得して削除確認画面に渡す。
 */
public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        // セッションからログイン中の教員情報を取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログイン、または認証されていない場合はログイン画面へリダイレクト
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // 教員が所属する学校を取得
        School school = teacher.getSchool();

        // リクエストパラメータから科目コードを取得
        String cd = req.getParameter("cd");

        // 指定された科目コードに対応する科目情報を取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(cd, school);

        // 該当する科目が存在しない場合はエラーメッセージをセットして入力画面へ戻す
        if (subject == null) {
            req.setAttribute("message", "指定された科目情報が見つかりませんでした。");
            req.getRequestDispatcher("subjectCreate.action").forward(req, res);
            return;
        }

        // 科目情報をリクエストにセットしてJSPへ渡す
        req.setAttribute("subject", subject);

        // 削除確認画面へ遷移
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
