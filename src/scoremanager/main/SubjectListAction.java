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

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();

        // ログインユーザー取得（Teacher）
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // 所属学校を取得
        School school = teacher.getSchool();

        // DAOを使って科目リスト取得
        SubjectDao dao = new SubjectDao();
        List<Subject> subjects = dao.filter(school);  // ← これが大事！

        // リクエストに渡す
        req.setAttribute("subjects", subjects);

        // JSPへフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}


