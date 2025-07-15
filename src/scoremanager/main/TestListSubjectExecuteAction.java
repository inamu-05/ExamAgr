package scoremanager.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

/**
 * 科目別成績検索実行アクション
 * 必要な入力が揃っているかをチェックし、成績一覧を取得・表示する。
 */
public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        // セッションからログインユーザー（Teacher）を取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインまたは認証失敗時はログイン画面にリダイレクト
        if (teacher == null || !teacher.isAuthenticated()) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // 所属学校を取得
        School school = teacher.getSchool();

        // パラメータを取得
        String entY = req.getParameter("entYear");
        String classNum = req.getParameter("classNum");
        String subjectCd = req.getParameter("subject");

        // ========================
        // 入力チェック（バリデーション）
        // ========================
        if (entY == null || entY.isEmpty() ||
            classNum == null || classNum.isEmpty() ||
            subjectCd == null || subjectCd.isEmpty()) {

            // 入力不備時：エラーメッセージをセットして画面に戻る
            req.setAttribute("errorSubjectSearch", "入学年度・クラス・科目のすべてを選択してください。");
            req.getRequestDispatcher("TestList.action").forward(req, res);
            return;
        }

        // ========================
        // データ取得処理
        // ========================
        Integer entYear = Integer.parseInt(entY);

        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subjectCd, school);

        TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
        List<TestListSubject> testListSubject = testListSubjectDao.filter(entYear, classNum, subject, school);

        // ========================
        // デバッグ出力（任意）
        // ========================
        System.out.println("検索する年度：" + entYear);
        System.out.println("検索するクラス番号：" + classNum);
        System.out.println("検索する科目コード：" + subjectCd);
        System.out.println("取得件数: " + testListSubject.size());

        for (TestListSubject t : testListSubject) {
            System.out.println("学年: " + t.getEntYear() +
                               ", クラス: " + t.getClassNum() +
                               ", 学籍番号: " + t.getStudentNo() +
                               ", 氏名: " + t.getStudentName());

            Map<Integer, Integer> points = t.getPoints();
            for (Map.Entry<Integer, Integer> entry : points.entrySet()) {
                System.out.println("  回数: " + entry.getKey() + ", 点数: " + entry.getValue());
            }
        }

        // ========================
        // JSPに渡す属性セット
        // ========================
        req.setAttribute("isSubjectSearch", true);
        req.setAttribute("subject", subject);
        req.setAttribute("testListSubject", testListSubject);

        // 検索結果表示用の画面にフォワード
        req.getRequestDispatcher("TestList.action").forward(req, res);
    }
}
