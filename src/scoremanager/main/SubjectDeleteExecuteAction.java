package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

/**
 * 科目削除実行アクション
 * 確認画面から送信された科目コードをもとに、
 * 科目情報をデータベースから削除する処理を行う。
 */
public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        // ログイン中の教員情報をセッションから取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータから削除対象の科目コードを取得
        String cd = req.getParameter("cd");

        // 削除対象の科目情報をSubjectインスタンスにセット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(teacher.getSchool()); // 所属学校の情報をセット

        // デバッグ用ログ出力
        System.out.println("削除対象cd = " + subject.getCd());
        System.out.println("削除対象school_cd = " + subject.getSchool().getCd());

        // データベースから科目を削除
        SubjectDao dao = new SubjectDao();
        boolean result = dao.delete(subject);

        // 削除結果に応じてメッセージを設定し、完了画面へ遷移
        if (result) {
            req.setAttribute("message", "削除が完了しました。");
        } else {
            req.setAttribute("message", "削除に失敗しました。");
        }

        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}
