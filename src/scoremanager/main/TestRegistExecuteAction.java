package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

/**
 * 成績登録処理を行うアクション。
 */
public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");

            if (teacher == null || !teacher.isAuthenticated()) {
                res.sendRedirect("../login.jsp");
                return;
            }

            School school = teacher.getSchool();

            // パラメータ取得
            String[] studentNos = req.getParameterValues("studentNo");
            String[] classNums = req.getParameterValues("classNum");
            String subjectCd = req.getParameter("subject");
            String numStr = req.getParameter("num");
            String[] points = req.getParameterValues("point");

            if (subjectCd == null || subjectCd.isEmpty() || numStr == null || numStr.isEmpty()) {
                throw new IllegalArgumentException("科目または回数の情報が送信されていません。");
            }

            int num = Integer.parseInt(numStr);

            Subject subject = new Subject();
            subject.setCd(subjectCd);

            List<Test> testList = new ArrayList<>();

            for (int i = 0; i < studentNos.length; i++) {
                Test test = new Test();

                Student student = new Student();
                student.setNo(studentNos[i]);
                test.setStudent(student);

                test.setClassNum(classNums[i]);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(num);
                test.setPoint(Integer.parseInt(points[i]));

                testList.add(test);
            }

            TestDao testDao = new TestDao();
            boolean success = testDao.save(testList);

            req.setAttribute("success", success);
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("success", false);
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
        }
    }
}
