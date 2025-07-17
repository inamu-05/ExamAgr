package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

/**
 * 成績登録処理を行うアクション。
 */
public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();
            // セッションからログインユーザー情報取得
            Teacher teacher = (Teacher) session.getAttribute("user");

            // 未ログインまたは認証失敗時はログイン画面にリダイレクト
            if (teacher == null || !teacher.isAuthenticated()) {
                res.sendRedirect("../login.jsp");
                return;
            }

            // 所属学校情報を取得
            School school = teacher.getSchool();

            // フォームからパラメータを取得
            String[] studentNos = req.getParameterValues("studentNo");
            String[] classNums = req.getParameterValues("classNum");
            String subjectCd = req.getParameter("subject");
            String numStr = req.getParameter("num");
            String[] points = req.getParameterValues("point");

            // 科目コードと回数の必須チェック
            if (subjectCd == null || subjectCd.isEmpty() || numStr == null || numStr.isEmpty()) {
                throw new IllegalArgumentException("科目または回数の情報が送信されていません。");
            }

            // 回数の文字列を整数に変換
            int num = Integer.parseInt(numStr);

            // Subjectオブジェクトに科目コードをセット
            Subject subject = new Subject();
            subject.setCd(subjectCd);

            // 登録用Testリストの作成
            List<Test> testList = new ArrayList<>();

            // 学生分の成績情報をTestオブジェクトに格納
            for (int i = 0; i < studentNos.length; i++) {
                Test test = new Test();

                // Studentオブジェクト作成し学生番号セット
                Student student = new Student();
                student.setNo(studentNos[i]);
                test.setStudent(student);

                // その他情報セット
                test.setClassNum(classNums[i]);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(num);

                // 点数のnullチェックと設定（未入力はnullに）
                if (points[i] != null && !points[i].isEmpty()) {
                    String rawPoint = points[i];

                    if (rawPoint == null || rawPoint.trim().isEmpty()) {
                        // 空欄 → 未受験としてnullを設定
                        test.setPoint(null);
                    } else {
                        // 数値としてパース（バリデーション済み前提）
                        test.setPoint(Integer.parseInt(rawPoint.trim()));
                    }

                } else {
                    test.setPoint(null);
                }

                // リストに追加
                testList.add(test);
            }

            // DAOで保存処理を実行
            TestDao testDao = new TestDao();
            boolean success = testDao.save(testList);

            // 処理結果をリクエスト属性にセット
            req.setAttribute("success", success);

            // 完了画面へフォワード
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);

        } catch (Exception e) {
            // エラー時はスタックトレースを表示し、失敗フラグをセット
            e.printStackTrace();
            req.setAttribute("success", false);

            // 完了画面へフォワード（失敗メッセージ表示想定）
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
        }
    }
}
