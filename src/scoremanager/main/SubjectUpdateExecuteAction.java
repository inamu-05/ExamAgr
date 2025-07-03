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

    HttpSession session = req.getSession();

    Teacher teacher = (Teacher) session.getAttribute("user");

    String cd = req.getParameter("cd");
    String name = req.getParameter("name");
    String schoolCd = req.getParameter("school_cd");

    SubjectDao dao = new SubjectDao();

    // 科目が存在するか確認
    Subject subject = dao.get(cd, teacher.getSchool());

    if (subject == null) {
      req.setAttribute("message", "科目が存在していません");
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

    // 科目名のバリデーションチェック
    if (name == null || name.trim().isEmpty()) {
      req.setAttribute("message", "科目名を入力してください");
      subject.setName("");
      req.setAttribute("subject", subject);
      req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, res);
      return;
    }

    // 更新対象の科目名をセット
    subject.setName(name);

    // 保存・更新を行う
    boolean result = dao.save(subject);

    if (result) {
      // 更新成功時、科目情報とメッセージをセットし、完了画面へ遷移
      req.setAttribute("subject", subject);
      req.setAttribute("message", "更新が完了しました。");
      req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    } else {
      // 更新失敗時、エラーメッセージをセットし入力画面へ戻す
      req.setAttribute("message", "更新に失敗しました。");
      req.setAttribute("subject", subject);
      req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, res);
    }
  }

}
