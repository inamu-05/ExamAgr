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

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String sbCd = req.getParameter("sbcd");
        String sbName = req.getParameter("sbname");

        System.out.println("【デバッグ】入力された科目コード: " + sbCd);
        System.out.println("【デバッグ】入力された科目名: " + sbName);

        boolean hasError = false;

        // 科目コードの文字数チェック
        if (sbCd == null || sbCd.length() != 3) {
            req.setAttribute("errorStuNum", "科目コードは3文字で入力してください");
            hasError = true;
        }

        // 重複チェック（文字数エラーがなければ）
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

        // 入力内容をもとにSubjectインスタンスを作成
        Subject subject = new Subject();
        subject.setCd(sbCd);
        subject.setName(sbName);
        subject.setSchool(teacher.getSchool());

        SubjectDao dao = new SubjectDao();
        boolean result = dao.save(subject);

        System.out.println("【デバッグ】データベース保存結果: " + result);

        if (result) {
            req.setAttribute("subject", subject);
            req.setAttribute("message", "登録が完了しました。");
            req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "登録に失敗しました。");
            req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
        }
    }
}
