<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>

<c:param name="scripts"></c:param>

<c:param name="content">
<style>
    select, input[type="checkbox"] {
        border: 1px solid #ccc;
        padding: 4px 8px;
        border-radius: 4px;
    }

    .filter-btn {
        background-color: #555555; /* グレー調に */
        border: 1px solid #aaa;
        padding: 4px 16px;
        border-radius: 4px;
        font-weight: bold;
        color: #fff;
    }

    .filter-btn:hover {
        background-color: #343434;
    }

    .table th,
    .table td {
        border-top: 1px solid #ccc !important;
        border-bottom: none !important;
    }

    .table thead th {
        font-weight: bold;
    }

    .table td, .table th {
        vertical-align: middle;
    }

    #error-message {
        color: orange;
        margin-top: 8px;
    }
</style>

<section class="me-4">
<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>
<div style="text-align: right;">
    <a href="StudentCreate.action">新規登録</a>
</div>

<!-- フィルター -->
<div style="background: #fff; border: 1px solid #ccc; padding: 16px; border-radius: 8px; margin: 0 16px 16px 16px;">
<form action="StudentList.action" method="post" class="px-4 mb-3" onsubmit="return validateForm();">
    <div class="d-inline-block me-4">
        <label class="d-block mb-1">入学年度</label>
        <select name="entYear"style="width: 160px;">
            <option value="">----</option>
            <c:forEach var="year" items="${entYearList}">
                <option value="${year}" <c:if test="${param.entYear == year}">selected</c:if>>${year}</option>
            </c:forEach>
        </select>
    </div>

    <div class="d-inline-block me-4">
        <label class="d-block mb-1">クラス</label>
        <select name="classNum">
            <option value="">----</option>
            <c:forEach var="c" items="${classNumList}">
                <option value="${c}" <c:if test="${param.classNum == c}">selected</c:if>>${c}</option>
            </c:forEach>
        </select>
    </div>

    <!-- 在学チェックボックスはそのまま -->
    <label class="me-3">
        <input type="checkbox" name="isAttend" value="true" <c:if test="${param.isAttend == 'true'}">checked</c:if>> 在学中
    </label>

    <button type="submit" class="filter-btn me-3">絞込み</button>

    <!-- エラーメッセージ表示エリア -->
    <div id="error-message"></div>
</form>
</div>


<!-- 結果件数 -->
<c:if test="${fn:length(students) > 0}">
    <div class="px-4 mb-2">検索結果：${fn:length(students)}件</div>
</c:if>

<!-- データ0件時のメッセージ -->
<c:if test="${fn:length(students) == 0}">
    <div class="px-4 mb-2 text-danger">学生情報が存在しませんでした。</div>
</c:if>

<!-- 一覧：件数が1件以上の場合のみ表示 -->
<c:if test="${fn:length(students) > 0}">
<table class="table mb-5" style="border-collapse: collapse;">
    <thead>
        <tr>
            <th class="ps-4">入学年度</th>
            <th>学生番号</th>
            <th>氏名</th>
            <th>クラス</th>
            <th>在学中</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="student" items="${students}">
        <tr>
            <td class="ps-4">${student.entYear}</td>
            <td>${student.no}</td>
            <td>${student.name}</td>
            <td>${student.classNum}</td>
            <td>
                <c:choose>
                    <c:when test="${student.isAttend}">　○</c:when>
                    <c:otherwise>×</c:otherwise>
                </c:choose>
            </td>
            <td><a href="StudentUpdate.action?no=${student.no}">変更</a></td>
        </tr>
        </c:forEach>
    </tbody>
</table>
</c:if>
</section>

<script>
function validateForm() {
    const entYear = document.querySelector('select[name="entYear"]').value;
    const classNum = document.querySelector('select[name="classNum"]').value;
    const errorMessage = document.getElementById('error-message');

    if (entYear === "" && classNum !== "") {
        errorMessage.textContent = "クラスを選択する場合は入学年度も選択してください。";
        return false;
    }

    // エラーなしならメッセージを消す
    errorMessage.textContent = "";
    return true;
}
</script>

</c:param>
</c:import>
