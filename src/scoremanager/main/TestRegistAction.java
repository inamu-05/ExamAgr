package scoremanager.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

/**
 * 成績登録画面の初期表示を担当するアクション。
 */
public class TestRegistAction extends Action {

    /**
     * 成績登録画面の表示処理。
     *
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();

            // セッションからログインユーザー情報を取得
            Teacher teacher = (Teacher) session.getAttribute("user");

            // 未ログインまたは認証失敗時、ログイン画面へリダイレクト
            if (teacher == null || !teacher.isAuthenticated()) {
                res.sendRedirect("../login.jsp");
                return;
            }

            // 所属学校情報を取得
            School school = teacher.getSchool();

            // ==============================
            // 検索条件パラメータの取得
            // ==============================
            String entYearParam = req.getParameter("entYear");
            String classNumParam = req.getParameter("classNum");
            String subjectParam = req.getParameter("subject");
            String numParam = req.getParameter("num");

            // ========================
            // 入力チェック（バリデーション）
            // ========================
            // 検索が実行されたかどうか
            boolean searchAttempted = entYearParam != null || classNumParam != null ||
                                      subjectParam != null || numParam != null;

            // 入力チェック（バリデーション）
            if (searchAttempted && (
                entYearParam == null || entYearParam.isEmpty() ||
                classNumParam == null || classNumParam.isEmpty() ||
                subjectParam == null || subjectParam.isEmpty() ||
                numParam == null || numParam.isEmpty())) {

                // エラーメッセージをセット
                req.setAttribute("errorTestSearch", "入学年度・クラス・科目・回数のすべてを選択してください。");

                // プルダウンデータ再取得
                ClassNumDao classNumDao = new ClassNumDao();
                List<String> classNumList = classNumDao.filter(school);

                List<Integer> entYearList = new ArrayList<>();
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                for (int year = 2020; year <= currentYear; year++) {
                    entYearList.add(year);
                }

                SubjectDao subjectDao = new SubjectDao();
                List<Subject> subjectList = subjectDao.filter(school);

                List<Integer> numList = Arrays.asList(1, 2);

                // リクエスト属性にセット
                req.setAttribute("classNumList", classNumList);
                req.setAttribute("entYearList", entYearList);
                req.setAttribute("subjectList", subjectList);
                req.setAttribute("numList", numList);

                // 入力画面に戻す
                req.getRequestDispatcher("test_regist.jsp").forward(req, res);
                return;
            }

            // ========================
            // 入力値のパース処理
            // ========================

            // 入学年度（String → Integer）
            Integer entYear = null;
            if (entYearParam != null && !entYearParam.isEmpty()) {
                entYear = Integer.parseInt(entYearParam);
            }

            // 科目コード → Subjectオブジェクトに変換
            Subject subject = null;
            if (subjectParam != null && !subjectParam.isEmpty()) {
                SubjectDao subjectDao = new SubjectDao();
                subject = subjectDao.get(subjectParam, school);
            }

            // 回数（String → Integer）
            Integer num = null;
            if (numParam != null && !numParam.isEmpty()) {
                num = Integer.parseInt(numParam);
            }

            // ==============================
            // 成績リストの取得（条件付き）
            // ==============================
            List<Test> testList = new ArrayList<>();
            if (entYear != null && classNumParam != null && !classNumParam.isEmpty()
                    && subject != null && num != null) {
                TestDao testDao = new TestDao();
                testList = testDao.filter(entYear, classNumParam, subject, num, school);
            }

            // ==============================
            // プルダウン用データの取得
            // ==============================
            ClassNumDao classNumDao = new ClassNumDao();
            List<String> classNumList = classNumDao.filter(school);

            List<Integer> entYearList = new ArrayList<>();
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int year = 2020; year <= currentYear; year++) {
                entYearList.add(year);
            }

            SubjectDao subjectDao = new SubjectDao();
            List<Subject> subjectList = subjectDao.filter(school);

            List<Integer> numList = Arrays.asList(1, 2);

            // ==============================
            // JSPに渡すデータをセット
            // ==============================
            req.setAttribute("tests", testList);
            req.setAttribute("classNumList", classNumList);
            req.setAttribute("entYearList", entYearList);
            req.setAttribute("subjectList", subjectList);
            req.setAttribute("numList", numList);

            // 成績管理画面へフォワード
            RequestDispatcher dispatcher = req.getRequestDispatcher("test_regist.jsp");
            dispatcher.forward(req, res);

        } catch (Exception e) {
            // エラー発生時はスタックトレースを出力し、エラーページへ遷移
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
