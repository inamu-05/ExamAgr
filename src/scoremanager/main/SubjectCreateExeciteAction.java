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
 * フォームから送信された科目コード・科目名を受け取り、
 * バリデーション後、問題がなければデータベースに登録する。
 */
public class SubjectCreateExeciteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログインユーザー取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // フォームから送信された科目コード・科目名を取得
        String sbCd = req.getParameter("sbcd");
        String sbName = req.getParameter("sbname");

        // デバッグ用ログ出力
        System.out.println("【デバッグ】入力された科目コード: " + sbCd);
        System.out.println("【デバッグ】入力された科目名: " + sbName);

        boolean hasError = false;

        // =============================
        // 入力チェック①: 科目コードの長さ
        // =============================
        if (sbCd == null || sbCd.length() != 3) {
            req.setAttribute("errorStuNum", "科目コードは3文字で入力してください");
            hasError = true;
        }

        // =============================
        // 入力チェック②: 重複確認（科目コード）
        // =============================
        if (!hasError) {
            SubjectDao dao = new SubjectDao();
            Subject existingSubject = dao.get(sbCd, teacher.getSchool());

            if (existingSubject != null) {
                req.setAttribute("errorDuplicate", "科目コードが重複しています");
                hasError = true;
            }
        }

        // =============================
        // エラーがある場合は再入力画面に戻す
        // =============================
        if (hasError) {
            req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
            return;
        }

        // =============================
        // 登録処理
        // =============================
        // 入力値をもとにSubjectインスタンスを生成
        Subject subject = new Subject();
        subject.setCd(sbCd);
        subject.setName(sbName);
        subject.setSchool(teacher.getSchool());

        // DAOを使って保存
        SubjectDao dao = new SubjectDao();
        boolean result = dao.save(subject);

        // デバッグ：登録結果表示
        System.out.println("【デバッグ】データベース保存結果: " + result);

        // =============================
        // 登録結果に応じた画面遷移
        // =============================
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
