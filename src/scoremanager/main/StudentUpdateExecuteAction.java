package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

/**
 * 学生情報更新実行アクション
 * フォームから送信された学生情報をもとにDBを更新する処理
 */
public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログイン中の教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータから学生情報を取得
        String entYearStr = req.getParameter("entYear");     // 入学年度
        String stuNum     = req.getParameter("stuNum");      // 学生番号
        String name       = req.getParameter("name");        // 氏名
        String classNum   = req.getParameter("classNum");    // クラス
        String isAttendStr = req.getParameter("isAttend");   // 在学中フラグ（"true" or null）

        // "true" のとき在学中フラグを true に、それ以外は false に設定
        boolean isAttend = "true".equals(isAttendStr);

        // 入力チェック：未入力項目がある場合はエラーとして元の画面に戻す
        if (entYearStr == null || stuNum == null || name == null || classNum == null ||
            entYearStr.isEmpty() || stuNum.isEmpty() || name.isEmpty() || classNum.isEmpty()) {

            req.setAttribute("error", "すべての項目を入力してください。");
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        // 学生情報をセット
        Student student = new Student();
        student.setNo(stuNum);                                 // 学生番号
        student.setName(name);                                 // 氏名
        student.setEntYear(Integer.parseInt(entYearStr));      // 入学年度（数値変換）
        student.setClassNum(classNum);                         // クラス
        student.setisAttend(isAttend);                         // 在学中フラグ
        student.setSchool(teacher.getSchool());                // 所属学校

        // 学生情報をDBに保存（更新）
        StudentDao dao = new StudentDao();
        boolean result = dao.save(student);

        if (result) {
            // 更新成功時
            req.setAttribute("message", "更新が完了しました。");
            req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
        } else {
            // 更新失敗時
            req.setAttribute("message", "更新に失敗しました。");
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
        }
    }
}
