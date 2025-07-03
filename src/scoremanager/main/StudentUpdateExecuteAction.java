package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String entYearStr = req.getParameter("entYear");
        String stuNum = req.getParameter("stuNum");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");
        String isAttendStr = req.getParameter("isAttend");
        boolean isAttend = "true".equals(isAttendStr);

        if (entYearStr == null || stuNum == null || name == null || classNum == null ||
            entYearStr.isEmpty() || stuNum.isEmpty() || name.isEmpty() || classNum.isEmpty()) {

            req.setAttribute("error", "すべての項目を入力してください。");
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        Student student = new Student();
        student.setNo(stuNum);
        student.setName(name);
        student.setEntYear(Integer.parseInt(entYearStr));
        student.setClassNum(classNum);
        student.setisAttend(isAttend);
        student.setSchool(teacher.getSchool());

        StudentDao dao = new StudentDao();
        boolean result = dao.save(student);

        if (result) {
            req.setAttribute("message", "更新が完了しました。");
            req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
        } else {
            req.setAttribute("message", "更新に失敗しました。");
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
        }

    }
}
