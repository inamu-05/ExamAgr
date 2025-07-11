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

        String no = req.getParameter("stuNum");
        System.out.println("検索する学生番号：" + no);

        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(no);


        TestListStudentDao testListStudentDao = new TestListStudentDao();
        List<TestListStudent> testListStudent = testListStudentDao.filter(student);


        	req.setAttribute("isStudentSearch", true);
            req.setAttribute("student", student);
            req.setAttribute("testListStudent", testListStudent);

        req.getRequestDispatcher("TestList.action").forward(req, res);
    }
}
