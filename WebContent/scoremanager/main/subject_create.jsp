<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>

  <c:param name="scripts"> </c:param>

  <c:param name="content">

<style>
    select, input[type="text"], input[type="number"] {
        border: 1px solid #ccc;
        padding: 8px;
        border-radius: 4px;
        width: 100%;
        max-width: 800px;
        margin-bottom: 12px;
    }

    .submit-btn {
        background-color: #666;
        border: 1px solid #444;
        color: white;
        padding: 8px 24px;
        border-radius: 4px;
        font-weight: bold;
        width: 200px;
    }

    .submit-btn:hover {
        background-color: #555;
    }

    .form-label {
        display: block;
        margin: 16px 0 4px 0;
        font-weight: bold;
    }

    .error-message {
        color: orange;
        font-size: 0.9em;
        margin-top: 4px;
        margin-bottom: 8px;
    }

    .title-box {
        background-color: #f8f9fa;
        padding: 12px 16px;
        font-size: 1.2em;
        font-weight: bold;
        border-radius: 4px;
        margin-bottom: 24px;
        border-left: 5px solid #6c757d;
    }

    .back-link {
        display: inline-block;
        margin-top: 20px;
    }
</style>

<section class="me-4">
    <h2 class="h3 mb-3 fw-normal py-2 px-4" style="background-color: #ffd3a8;">科目情報登録</h2>

    <form action="SubjectCreateExecite.action" method="post">

        <label class="form-label">科目コード</label>
        <input type="text" name="sbcd" placeholder="科目コードを入力してください" required />

        <c:if test="${not empty errorStuNum}">
            <div class="error-message">${errorStuNum}</div>
        </c:if>
        <c:if test="${not empty errorDuplicate}">
            <div class="error-message">${errorDuplicate}</div>
        </c:if>

        <label class="form-label">科目名</label>
        <input type="text" name="sbname" placeholder="科目名を入力してください" required />

        <div style="margin-top: 24px;">
            <button type="submit" class="submit-btn">登録</button>
        </div>
    </form>

    <div class="back-link">
        <a href="StudentList.action">戻る</a>
    </div>
</section>

</c:param>
</c:import>
