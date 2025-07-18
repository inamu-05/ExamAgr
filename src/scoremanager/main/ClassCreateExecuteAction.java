package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

/**
 * クラス情報登録実行アクション
 */
public class ClassCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	    HttpSession session = req.getSession();
	    Teacher teacher = (Teacher) session.getAttribute("user");

	    if (teacher == null || !teacher.isAuthenticated()) {
	        res.sendRedirect("../login.jsp");
	        return;
	    }

	    String classNumStr = req.getParameter("classNum");
	    int classNum = 0;
	    boolean hasError = false;

	    // 空チェック
	    if (classNumStr == null || classNumStr.trim().isEmpty()) {
	        req.setAttribute("errorClassNum", "クラス番号を入力してください。");
	        hasError = true;
	    } else {
	        try {
	            classNum = Integer.parseInt(classNumStr.trim());

	            // 1〜999 の範囲チェック（必要であれば）
	            if (classNum < 1 || classNum > 999) {
	                req.setAttribute("errorClassNum", "クラス番号は1〜999の範囲で入力してください。");
	                hasError = true;
	            }

	        } catch (NumberFormatException e) {
	            req.setAttribute("errorClassNum", "クラス番号は数字で入力してください。");
	            hasError = true;
	        }
	    }

	    School school = teacher.getSchool();
	    ClassNumDao dao = new ClassNumDao();

	    // 重複チェック
	    if (!hasError) {
	        ClassNum existing = dao.get(String.valueOf(classNum), school);
	        if (existing != null) {
	            req.setAttribute("errorClassNum", "このクラス番号はすでに登録されています。");
	            hasError = true;
	        }
	    }

	    if (hasError) {
	        req.setAttribute("classNum", classNum);  // 数値で渡してもJSP側で出力されます
	        req.getRequestDispatcher("class_create.jsp").forward(req, res);
	        return;
	    }

	    // 登録処理
	    ClassNum newClass = new ClassNum();
	    newClass.setClass_num(String.valueOf(classNum)); // DB上で文字列ならここはStringで
	    newClass.setSchool(school);

	    boolean result = dao.save(newClass);

	    if (result) {
	        req.setAttribute("classNum", classNum);
	        req.setAttribute("message", "クラスの登録が完了しました。");
	        req.getRequestDispatcher("class_create_done.jsp").forward(req, res);
	    } else {
	        req.setAttribute("error", "クラスの登録に失敗しました。");
	        req.getRequestDispatcher("class_create.jsp").forward(req, res);
	    }
	}

}
