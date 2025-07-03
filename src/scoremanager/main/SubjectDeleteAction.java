package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

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

        // パラメータから学生番号を取得
        String cd = req.getParameter("cd");

        // 学生情報取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(cd,school);

        // 取得できなかった場合のエラーハンドリング
        if (subject == null) {
            req.setAttribute("message", "指定された科目情報が見つかりませんでした。");
            req.getRequestDispatcher("subjectCreate.action").forward(req, res);
            return;
        }

        // JSPへ学生情報を渡す
        req.setAttribute("subject", subject);
        // 画面遷移
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
