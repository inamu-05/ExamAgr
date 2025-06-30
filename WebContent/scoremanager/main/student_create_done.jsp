<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理 システム</c:param>

  <c:param name="scripts"></c:param>
  <c:param name="content">

<style>
    select, input[type="text"], input[type="number"] {
        border: 1px solid #ccc;
        padding: 4px 8px;
        border-radius: 4px;
        width: 100%;
        max-width: 400px;
    }

    .submit-btn {
        background-color: #666;
        border: 1px solid #444;
        color: white;
        padding: 6px 24px;
        border-radius: 4px;
        font-weight: bold;
    }

    .submit-btn:hover {
        background-color: #555;
    }

    .form-box {
        background: #fff;
        border: 1px solid #ccc;
        padding: 16px;
        border-radius: 8px;
        margin: 0 16px 16px 16px;
        max-width: 500px;
    }

    .form-label {
        display: block;
        margin: 8px 0 4px 0;
    }

    .message-box {
        background-color: #e0f7fa;
        border: 1px solid #4dd0e1;
        padding: 10px;
        margin: 16px 0;
        border-radius: 4px;
        color: #00796b;
        max-width: 500px;
    }
</style>

<section class="me-4">
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>

	     <!-- メッセージ表示枠 -->

	        <p class="text-center" style="background-color:#66CC99">
	            ${message}
	        </p>


        <div class="mt-2">
            <a href="StudentCreate.action">戻る</a>

	        <div style="text-align: right;">
	            <a href="StudentList.action">学生一覧</a>
	        </div>
	    </div>
</section>

</c:param>
</c:import>
