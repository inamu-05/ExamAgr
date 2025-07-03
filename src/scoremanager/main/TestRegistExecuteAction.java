package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 成績登録の実行処理を担当するアクション。
 */
public class TestRegistExecuteAction {

    /**
     * 成績登録の実行処理。
     *
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     */
    public void execute(HttpServletRequest req, HttpServletResponse res) {
        // フォームから送信されたデータを取得し、Testオブジェクトを生成
        // TestDaoを使って保存処理を実行
        // 例: testDao.save(test);
    }
}
