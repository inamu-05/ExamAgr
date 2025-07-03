package scoremanager.main;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

/**
 * 成績登録画面の初期表示を担当するアクション。
 */
public class TestRegistAction extends Action{

    /**
     * 成績登録画面の表示処理。
     *
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     */
    public void execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            // 必要なデータを取得してリクエストにセット（例：科目一覧など）
            // req.setAttribute("subjectList", subjectDao.filter(school));

            // JSPへフォワード
            RequestDispatcher dispatcher = req.getRequestDispatcher("scoremanage_list.jsp");
            dispatcher.forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            // エラーページへ遷移などの処理も追加可能
        }
    }
}
