package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

/**
 * 科目一覧表示アクション
 * ログインユーザーの所属学校に紐づく科目の一覧を取得し、
 * 一覧画面（subject_list.jsp）に渡す。
 */
public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();

        // ログイン中の教員情報（Teacherインスタンス）を取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインまたは認証失敗時はログイン画面へリダイレクト
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // 教員の所属学校情報を取得
        School school = teacher.getSchool();

        // 科目DAOを使用して、該当学校の科目一覧を取得
        SubjectDao dao = new SubjectDao();
        List<Subject> subjects = dao.filter(school);

        // 科目一覧をリクエストスコープにセット
        req.setAttribute("subjects", subjects);

        // 一覧表示JSPにフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}
