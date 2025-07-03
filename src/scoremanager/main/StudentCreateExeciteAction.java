package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

/**
 * 学生情報登録実行アクション
 * 学生情報登録フォームから送信された内容を受け取り、データベースに登録する処理を行う
 */
public class StudentCreateExeciteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログイン中の教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 学生情報の入力値をリクエストパラメータから取得
        String entYearStr = req.getParameter("entYear");  // 入学年度
        String stuNum = req.getParameter("stuNum");       // 学生番号
        String name = req.getParameter("name");           // 氏名
        String classNum = req.getParameter("classNum");   // クラス

        // 入力チェック：未入力の項目がある場合はエラーメッセージを設定し、登録画面に戻す
        boolean hasError = false;

     // 入学年度チェック
     if (entYearStr == null || entYearStr.isEmpty()) {
         req.setAttribute("errorEntYear", "入学年度を選択してください");
         hasError = true;
     }

     // 学生番号チェック
     StudentDao dao = new StudentDao();
     Student existingStudent = dao.get(stuNum);

     if (existingStudent != null) {
         // 重複ありのためエラー表示
         req.setAttribute("errorStuNum", "この学生番号はすでに使われています");
         req.getRequestDispatcher("StudentCreate.action").forward(req, res);
         return;
     }


     if (hasError) {
         // 入力エラーがあれば元の入力画面に戻す
         req.getRequestDispatcher("StudentCreate.action").forward(req, res);
         return;
     }

        // 学生情報をセット
        Student student = new Student();
        student.setNo(stuNum);                              // 学生番号
        student.setName(name);                              // 氏名
        student.setEntYear(Integer.parseInt(entYearStr));   // 入学年度（数値に変換）
        student.setClassNum(classNum);                      // クラス
        student.setisAttend(true);                          // 在学中フラグをtrueで登録
        student.setSchool(teacher.getSchool());             // ログイン中教員の所属学校をセット

        // 学生情報をデータベースへ保存
        boolean result = dao.save(student);

        if (result) {
            // 登録成功時、完了メッセージと学生情報をリクエストにセットし、完了画面へ遷移
            req.setAttribute("student", student);
            req.setAttribute("message", "登録が完了しました。");
            req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
        } else {
            // 登録失敗時、エラーメッセージをセットし登録画面に戻す
            req.setAttribute("error", "登録に失敗しました。");
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
        }
    }
}