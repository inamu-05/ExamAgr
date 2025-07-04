package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからログインユーザー（教師）情報取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインチェック
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // 教師所属の学校情報
        School school = teacher.getSchool();

        // フロントからの検索条件取得
        String entYearParam = req.getParameter("entYear");
        String classNumParam = req.getParameter("classNum");
        String subjectCdParam = req.getParameter("subject"); // 科目コード

        System.out.println("entYearParam = " + entYearParam);
        System.out.println("classNumParam = " + classNumParam);
        System.out.println("subjectCdParam = " + subjectCdParam);

        // 入学年度のパース
        Integer entYear = null;
        if (entYearParam != null && !entYearParam.isEmpty()) {
            entYear = Integer.parseInt(entYearParam);
        }

        // プルダウン用データの取得
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filter(school);

        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school);

        List<Integer> entYearList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = 2020; year <= currentYear; year++) {
            entYearList.add(year);
        }

        // 検索条件が揃っていれば成績情報を検索
        List<Test> testList = new ArrayList<>();
        if (entYear != null
                && classNumParam != null && !classNumParam.isEmpty()
                && subjectCdParam != null && !subjectCdParam.isEmpty()) {

            Subject subject = new Subject();
            subject.setCd(subjectCdParam);

            TestDao testDao = new TestDao();

            // 試験回数は例として固定値1に設定
            int num = 1;

            testList = testDao.filter(entYear, classNumParam, subject, num, school);
        }

        // 画面表示用のリクエスト属性にセット
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("entYearList", entYearList);
        req.setAttribute("testList", testList);

        // JSPへフォワード
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
