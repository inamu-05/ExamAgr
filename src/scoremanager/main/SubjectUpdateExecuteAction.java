package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

/**
 * 科目情報更新実行アクション
 * 科目情報更新フォームから送信された内容を受け取り、バリデーションチェックを行い、
 * 問題がなければデータベースを更新し、成功メッセージをセットして完了画面へ遷移する。
 */
public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログインユーザー（Teacher）を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータから科目コード・科目名・学校コードを取得
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        String schoolCd = req.getParameter("school_cd");

        SubjectDao dao = new SubjectDao();

        // 指定された科目コードで科目情報が存在するか確認（ログインユーザーの所属学校で絞り込み）
        Subject subject = dao.get(cd, teacher.getSchool());

        // 科目が存在しない場合はエラーメッセージをセットして更新画面へ戻す
        if (subject == null) {
            req.setAttribute("message", "指定された科目は存在しません。");

            // 新規作成のためフォーム入力値を保持
            subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);
            School school = new School();
            school.setCd(schoolCd);
            subject.setSchool(school);

            req.setAttribute("subject", subject);
            req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, res);
            return;
        }

        // 科目名の入力チェック（空白のみもNG）
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("message", "科目名を入力してください");

            // 空文字をセットし直し、入力画面へ戻す
            subject.setName("");
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, res);
            return;
        }

        // 科目名を更新対象にセット
        subject.setName(name);

        // DAOのsaveメソッドで更新処理を実行
        boolean result = dao.save(subject);

        if (result) {
            // 更新成功時は完了画面へ遷移
            req.setAttribute("subject", subject);
            req.setAttribute("message", "更新が完了しました。");
            req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
        } else {
            // 更新失敗時はエラーメッセージをセットして更新画面へ戻す
            req.setAttribute("message", "更新に失敗しました。");
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, res);
        }
    }
}
