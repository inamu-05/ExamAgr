package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

/**
 * 科目更新画面表示アクション
 * 指定された科目コードに該当する科目情報を取得し、
 * 更新画面（subject_update.jsp）に渡す。
 */
public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータから科目コードを取得
        String cd = req.getParameter("cd");

        // 科目DAOから該当する科目情報を取得（ログイン教員の学校コードも使用）
        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(cd, teacher.getSchool());

        // 科目が見つからなかった場合、エラーメッセージをセット
        if (subject == null) {
            req.setAttribute("message", "指定された科目は存在しません。");
        }

        // 科目情報をリクエストスコープに保存
        req.setAttribute("subject", subject);

        // 更新画面にフォワード
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}
