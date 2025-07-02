package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // フォームから科目コード取得
        String cd = req.getParameter("cd");

        // Subjectインスタンスに情報セット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(teacher.getSchool());  // ログインユーザーの学校情報セット

        System.out.println("削除対象cd = " + subject.getCd());
        System.out.println("削除対象school_cd = " + subject.getSchool().getCd());

        SubjectDao dao = new SubjectDao();
        boolean result = dao.delete(subject);

        if (result) {
            req.setAttribute("message", "削除が完了しました。");
        } else {
            req.setAttribute("message", "削除に失敗しました。");
        }

        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}
