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

        // セッションからユーザー情報（Teacherインスタンス）を取得
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
        String entYearParam   = req.getParameter("entYear");   // 入学年度
        String classNumParam  = req.getParameter("classNum");  // クラス番号
        String isAttendParam  = req.getParameter("isAttend");  // 在学中フラグ

        // デバッグ出力
        System.out.println("検索する年度：" + entYearParam);
        System.out.println("検索する番号：" + classNumParam);
        System.out.println("検索するフラグ：" + isAttendParam);

        // ==============================
        // パラメータの整形・変換
        // ==============================
        // 入学年度（nullまたはInteger）
        Integer entYear = null;
        if (entYearParam != null && !entYearParam.isEmpty()) {
            entYear = Integer.parseInt(entYearParam);
        }

        // 在学中フラグ：文字列 "true" を boolean に変換
        boolean isAttend = "true".equals(isAttendParam);

        // ==============================
        // 学生情報の検索（条件によって分岐）
        // ==============================
        StudentDao studentDao = new StudentDao();
        List<Student> studentList;

        if (entYear != null && classNumParam != null && !classNumParam.isEmpty()) {
            // 入学年度・クラス番号の両方が指定された場合
            studentList = studentDao.filter(school, entYear, classNumParam, isAttend);
        } else if (entYear != null) {
            // 入学年度のみ指定された場合
            studentList = studentDao.filter(school, entYear, isAttend);
        } else {
            // 両方未指定（学校全体の在学/非在学者を取得）
            studentList = studentDao.filter(school, isAttend);
        }

        // ==============================
        // クラス一覧の取得（プルダウン表示用）
        // ==============================
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classNumList = classNumDao.filter(school);

        // ==============================
        // 入学年度リストの生成（プルダウン用）
        // 例：2020～今年（西暦）のリスト
        // ==============================
        List<Integer> entYearList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = 2020; year <= currentYear; year++) {
            entYearList.add(year);
        }

        // ==============================
        // JSP にデータをセットして画面遷移
        // ==============================
        req.setAttribute("students", studentList);         // 学生一覧
        req.setAttribute("classNumList", classNumList);    // クラス一覧（プルダウン）
        req.setAttribute("entYearList", entYearList);      // 入学年度一覧（プルダウン）

        // 学生一覧画面に遷移
        req.getRequestDispatcher("student_list.jsp").forward(req, res);
    }
}
