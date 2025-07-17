package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインチェック（Teacherオブジェクトがnullか認証されていなければログイン画面へ）
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // リクエストパラメータから学生番号を取得
        String no = req.getParameter("stuNum");
        System.out.println("検索する学生番号：" + no);

        if (no == null || no.isEmpty()) {
            // 学生番号が未指定ならエラー扱いにするか、リダイレクト等適切な処理を
            req.setAttribute("message", "学生番号が指定されていません。");
            req.getRequestDispatcher("TestList.action").forward(req, res);
            return;
        }

        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(no);

        if (student == null) {
            // 学生情報が見つからなければメッセージセットして戻す
            req.setAttribute("message", "指定された学生情報が見つかりませんでした。");
            req.getRequestDispatcher("TestList.action").forward(req, res);
            return;
        }

        TestListStudentDao testListStudentDao = new TestListStudentDao();
        List<TestListStudent> testListStudent = testListStudentDao.filter(student);

        // フラグやデータをリクエストにセット
        req.setAttribute("isStudentSearch", true);
        req.setAttribute("student", student);
        req.setAttribute("testListStudent", testListStudent);

        // 元のテスト一覧画面（TestList.action）へフォワード
        req.getRequestDispatcher("TestList.action").forward(req, res);
    }
}
