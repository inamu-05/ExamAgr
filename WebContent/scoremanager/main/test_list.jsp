<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
<c:param name="title">成績参照</c:param>

<c:param name="scripts"></c:param>

<c:param name="content">
<style>
/* 略：スタイルは今のままでOK */
</style>

<section class="me-4">
<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

<!-- 検索フォーム -->
<div style="background: #fff; border: 1px solid #ccc; padding: 16px; border-radius: 8px; margin: 0 16px 16px 16px;">

    <!-- 科目情報 -->
    <p class="fw-bold mb-2">科目情報</p>

    <form action="TestList.action" method="post" onsubmit="return validateForm();" class="mb-3">
        <!-- ラベルと入力欄横並び -->
        <div style="display: flex; align-items: center; gap: 16px; margin-bottom: 8px;">
            <label>入学年度</label>
            <select name="entYear">
                <option value="">----</option>
                <c:forEach var="year" items="${entYearList}">
                    <option value="${year}" <c:if test="${param.entYear == year}">selected</c:if>>${year}</option>
                </c:forEach>
            </select>

            <label>クラス</label>
            <select name="classNum">
                <option value="">----</option>
                <c:forEach var="c" items="${classNumList}">
                    <option value="${c}" <c:if test="${param.classNum == c}">selected</c:if>>${c}</option>
                </c:forEach>
            </select>

            <label>科目</label>
            <select name="subject">
                <option value="">----</option>
                <c:forEach var="c" items="${subjectList}">
                    <option value="${c.cd}" <c:if test="${param.subject == c.cd}">selected</c:if>>${c.name}</option>
                </c:forEach>
            </select>

            <button type="submit" class="filter-btn">検索</button>
        </div>
        <div id="error-message" class="text-danger mt-2"></div>
    </form>

    <!-- 区切り線 -->
    <hr>

    <!-- 学生情報 -->
    <p class="fw-bold mb-2">学生情報</p>

    <form action="TestList.action" method="post" onsubmit="return validateForm();">
        <div style="display: flex; align-items: center; gap: 16px;">
            <label>学生番号</label>
            <input type="text" name="stuNum" placeholder="学生番号を入力してください" value="${param.stuNum != null ? param.stuNum : stuNum}" required />
            <button type="submit" class="filter-btn">検索</button>
        </div>

        <c:if test="${not empty errorStuNum}">
            <div class="error-message text-danger mt-2">${errorStuNum}</div>
        </c:if>
    </form>

</div>


<!-- 検索結果表示 -->
<c:if test="${fn:length(studentList) > 0}">
    <div class="px-4 mb-2">検索科目：${searchedSubjectName}</div>

    <table class="table mb-5" style="border-collapse: collapse;">
        <thead>
            <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>1回</th>
                <th>2回</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${studentList}">
                <tr>
                    <td>${student.entYear}</td>
                    <td>${student.classNum}</td>
                    <td>${student.no}</td>
                    <td>${student.name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${testMap[student.no] != null && testMap[student.no]['1'] != null}">
                                ${testMap[student.no]['1']}
                            </c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${testMap[student.no] != null && testMap[student.no]['2'] != null}">
                                ${testMap[student.no]['2']}
                            </c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
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

    errorMessage.textContent = "";
    return true;
}
</script>

</c:param>
</c:import>
