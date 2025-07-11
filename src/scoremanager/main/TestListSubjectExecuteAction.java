package scoremanager.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

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

        String entY = req.getParameter("entYear");
        Integer entYear = null;
        if (entY != null && !entY.isEmpty()) {
            entYear = Integer.parseInt(entY);
        }

        String claN = req.getParameter("classNum");
        String Subj = req.getParameter("subject");

        System.out.println("検索する年度：" + entYear);
        System.out.println("検索するクラス番号：" + claN);
        System.out.println("検索する科目コード：" + Subj);


        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(Subj,school);


        TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
        List<TestListSubject> testListSubject = testListSubjectDao.filter(entYear,claN,subject,school);

        System.out.println("取得件数: " + testListSubject.size());
        for (TestListSubject t : testListSubject) {
            System.out.println("学年: " + t.getEntYear() +
                               ", クラス: " + t.getClassNum() +
                               ", 学籍番号: " + t.getStudentNo() +
                               ", 氏名: " + t.getStudentName());

            Map<Integer, Integer> points = t.getPoints();  // または getPoints()
            for (Map.Entry<Integer, Integer> entry : points.entrySet()) {
                System.out.println("  回数: " + entry.getKey() + ", 点数: " + entry.getValue());
            }
        }

        	req.setAttribute("isSubjectSearch", true);
            req.setAttribute("subject", subject);
            req.setAttribute("testListSubject", testListSubject);

        req.getRequestDispatcher("TestList.action").forward(req, res);
    }
}
