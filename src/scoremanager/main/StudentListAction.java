package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        // セッションからユーザー情報(Teacherインスタンス)を取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインまたは認証失敗時、ログイン画面にリダイレクト
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // ログインユーザーの所属学校情報を取得
        School school = teacher.getSchool();

        // ==============================
        // 検索条件パラメータを取得
        // ==============================
        String entYearParam = req.getParameter("entYear");   // 入学年度
        String classNumParam = req.getParameter("classNum"); // クラス番号
        String isAttendParam = req.getParameter("isAttend"); // 在学中フラグ（"true"なら在学中）

        // 入学年度（整数型に変換）、空ならnull
        Integer entYear = null;
        if (entYearParam != null && !entYearParam.isEmpty()) {
            entYear = Integer.parseInt(entYearParam);
        }

        // 在学中フラグ（true または false）
        boolean isAttend = "true".equals(isAttendParam);

        // ==============================
        // 学生リストを検索条件に応じて取得
        // ==============================
        StudentDao studentDao = new StudentDao();
        List<Student> studentList;

        if (entYear != null && classNumParam != null && !classNumParam.isEmpty()) {
            // 入学年度・クラスともに指定された場合
            studentList = studentDao.filter(school, entYear, classNumParam, isAttend);
        } else if (entYear != null) {
            // 入学年度だけ指定された場合
            studentList = studentDao.filter(school, entYear, isAttend);
        } else {
            // どちらも指定されていない場合（学校ごとの在学中 or 非在学中全件表示）
            studentList = studentDao.filter(school, isAttend);
        }

        // ==============================
        // クラス一覧を取得（プルダウン用）
        // ==============================
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school);

        // ==============================
        // 入学年度リスト生成（プルダウン用）
        // 2020年～現在の西暦までをリスト化
        // ==============================
        List<Integer> entYearList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = 2020; year <= currentYear; year++) {
            entYearList.add(year);
        }

        // ==============================
        // JSPに渡すデータをリクエストにセット
        // ==============================
        req.setAttribute("students", studentList);     // 検索結果の学生リスト
        req.setAttribute("classNumList", classNumList); // クラスのプルダウン用リスト
        req.setAttribute("entYearList", entYearList);   // 入学年度のプルダウン用リストお

        // 学生一覧画面にフォワード
        req.getRequestDispatcher("student_list.jsp").forward(req, res);
    }
}
