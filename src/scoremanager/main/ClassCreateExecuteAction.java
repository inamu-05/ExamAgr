package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        String classNumStr = req.getParameter("classNum");
        boolean hasError = false;

        // 入力チェック（3桁の数字、先頭は1または2）
        if (classNumStr == null || !classNumStr.matches("^\\d+$")) {
            req.setAttribute("errorClassNum", "クラス番号は半角数字で入力してください。");
            hasError = true;
        } else if (classNumStr.length() != 3) {
            req.setAttribute("errorClassNum", "クラス番号は必ず3桁で入力してください。");
            hasError = true;
        } else {
            int classNum = Integer.parseInt(classNumStr);

            // 先頭が1または2（=100〜299）
            if (classNum < 100 || classNum > 299) {
                req.setAttribute("errorClassNum", "クラス番号の先頭は1または2にしてください（100〜299の範囲）。");
                hasError = true;
            }

            // 重複チェック
            if (!hasError) {
                School school = teacher.getSchool();
                ClassNumDao dao = new ClassNumDao();
                ClassNum existing = dao.get(classNumStr, school);
                if (existing != null) {
                    req.setAttribute("errorClassNum", "このクラス番号はすでに登録されています。");
                    hasError = true;
                }
            }
        }

        if (hasError) {
            req.setAttribute("classNum", classNumStr);
            req.getRequestDispatcher("class_create.jsp").forward(req, res);
            return;
        }

        // 登録処理
        ClassNum newClass = new ClassNum();
        newClass.setClass_num(classNumStr);
        newClass.setSchool(teacher.getSchool());

        boolean result = new ClassNumDao().save(newClass);

        if (result) {
            req.setAttribute("classNum", classNumStr);
            req.setAttribute("message", "クラスの登録が完了しました。");
            req.getRequestDispatcher("class_create_done.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "クラスの登録に失敗しました。");
            req.getRequestDispatcher("class_create.jsp").forward(req, res);
        }
    }
}
