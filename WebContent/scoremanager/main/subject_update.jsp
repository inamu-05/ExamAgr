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
        background-color:blue;
        border: 1px solid #444;
        color: white;
        padding: 8px 16px;
        border-radius: 4px;
        font-weight: bold;
        width: 80px;
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
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

    <c:if test="${not empty message}">
        <div class="error-message">${message}</div>
    </c:if>

    <form action="SubjectUpdateExecute.action" method="post">

        <!-- 科目コード 表示のみ（枠なし） -->
        <label class="form-label">科目コード</label>
        <div style="margin-bottom: 12px;">${subject.cd}</div>
        <input type="hidden" name="cd" value="${subject.cd}" />

        <!-- 科目名編集 -->
        <label class="form-label">科目名</label>
        <input type="text" name="name" value="${subject.name}" required />

        <input type="hidden" name="school_cd" value="${subject.school.cd}" />

        <div style="margin-top: 12px;">
            <button type="submit" class="submit-btn">変更</button>
        </div>
    </form>

    <div class="back-link">
        <a href="SubjectList.action">戻る</a>
    </div>
</section>

</c:param>
</c:import>
