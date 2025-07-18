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

        // デバッグ用出力：入力値確認
        System.out.println("[DEBUG] 受信科目コード: " + cd);
        System.out.println("[DEBUG] 受信科目名: " + name);
        System.out.println("[DEBUG] 受信学校コード: " + schoolCd);

        SubjectDao dao = new SubjectDao();

        // 指定された科目コードで科目情報を取得（ログインユーザーの学校に限定）
        Subject subject = dao.get(cd, teacher.getSchool());

        // デバッグ用出力：取得結果確認
        if (subject != null) {
            System.out.println("[DEBUG] 対象科目が存在: " + subject.getCd() + " / " + subject.getName());
        } else {
            System.out.println("[DEBUG] 指定された科目が存在しません。");
        }

        // 科目が存在しない場合はエラーメッセージをセットして更新画面へ戻す
        if (subject == null) {
            req.setAttribute("message", "指定された科目は存在しません。");

            // 新規作成用に入力内容を保持したSubjectを作成
            subject = new Subject();
            subject.setCd(cd);
            subject.setName(name);

            School school = new School();
            school.setCd(schoolCd);
            subject.setSchool(school);

            req.setAttribute("subject", subject);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        // 科目名の入力チェック（空白のみもNG）
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("message", "科目名を入力してください");

            // 入力を戻すためにセット
            subject.setName("");
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        // 科目名を更新して保存
        subject.setName(name);
        boolean result = dao.save(subject);

        if (result) {
            // 成功メッセージとともに完了画面へ
            req.setAttribute("subject", subject);
            req.setAttribute("message", "更新が完了しました。");
            req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
        } else {
            // 更新失敗時
            req.setAttribute("message", "更新に失敗しました。");
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
        }
    }
}
