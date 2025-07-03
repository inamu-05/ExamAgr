package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    HttpSession session = req.getSession();

    Teacher teacher = (Teacher) session.getAttribute("user");

    String cd = req.getParameter("cd");

    SubjectDao dao = new SubjectDao();

    Subject subject = dao.get(cd, teacher.getSchool());

    if (subject == null) {

      req.setAttribute("message", "科目が存在していません");

    }

    req.setAttribute("subject", subject);

    req.getRequestDispatcher("subject_update.jsp").forward(req, res);

  }

}

