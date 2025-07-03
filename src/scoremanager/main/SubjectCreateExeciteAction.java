package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

/**
 * 科目情報登録実行アクション
 * 科目情報登録フォームから送信された内容を受け取り、バリデーションチェックを行い、
 * 問題がなければデータベースに登録する。
 */
public class SubjectCreateExeciteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログイン中の教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 入力フォームから送信された科目コード・科目名を取得
        String sbCd = req.getParameter("sbcd");
        String sbName = req.getParameter("sbname");

        boolean hasError = false;  // エラーフラグ

        // ① 科目コードの文字数チェック（3文字限定）
        if (sbCd == null || sbCd.length() != 3) {
            req.setAttribute("errorLength", "科目コードは3文字で入力してください");
            hasError = true;
        }

        // ② 重複チェック（文字数エラーがなければ実施）
        if (!hasError) {
            SubjectDao dao = new SubjectDao();
            Subject existingSubject = dao.get(sbCd, teacher.getSchool());
            if (existingSubject != null) {
                req.setAttribute("errorDuplicate", "科目コードが重複しています");
                hasError = true;
            }
        }

        // エラーがある場合は入力画面に戻す
        if (hasError) {
            req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
            return;
        }

        // 入力内容をもとにSubjectインスタンスを作成・セット
        Subject subject = new Subject();
        subject.setCd(sbCd);
        subject.setName(sbName);
        subject.setSchool(teacher.getSchool());

        // 科目情報をデータベースへ保存
        SubjectDao dao = new SubjectDao();
        boolean result = dao.save(subject);

        if (result) {
            // 登録成功時、科目情報とメッセージをセットし、完了画面へ遷移
            req.setAttribute("subject", subject);
            req.setAttribute("message", "登録が完了しました。");
            req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
        } else {
            // 登録失敗時、エラーメッセージをセットし入力画面へ戻す
            req.setAttribute("error", "登録に失敗しました。");
            req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
        }
    }
}
