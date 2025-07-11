<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>

  	<c:param name="scripts"></c:param>

  	<c:param name="content">
	<style>
	  .filter-form {
	    display: flex;
	    flex-wrap: wrap;
	    gap: 24px;
	    margin: 16px;
	    align-items: flex-end;
	  }

	  .filter-block {
	    display: flex;
	    flex-direction: column;
	    min-width: 120px;
	  }

	  .filter-block label {
	    font-weight: bold;
	    margin-bottom: 4px;
	  }

	  .filter-block select {
	    padding: 4px 8px;
	    border: 1px solid #ccc;
	    border-radius: 4px;
	  }

	.filter-button {
	    display: flex;
	    align-items: flex-end;
	    height: 100%;
	  }

	  .filter-btn {
	    padding: 4px 12px;
	    font-size: 0.9rem;
	    height: 32px;
	    background-color: #e0e0e0;
	    border: 1px solid #aaa;
	    border-radius: 4px;
	    font-weight: bold;
	    cursor: pointer;
	  }

	  .filter-btn:hover {
	    background-color: #d0d0d0;
	  }
	</style>

	    <section class="me-4">
	<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

	<!-- フィルター -->
	<div style="background: #fff; border: 1px solid #ccc; padding: 16px; border-radius: 8px; margin: 0 16px 16px 16px;">
	<form action="TestRegist.action" method="get" class="filter-form">
	  <div class="filter-block">
	    <label for="entYear">入学年度</label>
	    <select name="entYear" id="entYear">
	      <option value="">----</option>
	      <c:forEach var="year" items="${entYearList}">
	        <option value="${year}" <c:if test="${param.entYear == year}">selected</c:if>>${year}</option>
	      </c:forEach>
	    </select>
	  </div>

	  <div class="filter-block">
	    <label for="classNum">クラス</label>
	    <select name="classNum" id="classNum">
	      <option value="">----</option>
	      <c:forEach var="cls" items="${classNumList}">
	        <option value="${cls}" <c:if test="${param.classNum == cls}">selected</c:if>>${cls}</option>
	      </c:forEach>
	    </select>
	  </div>

	  <div class="filter-block">
	    <label for="subject">科目</label>
	    <select name="subject" id="subject">
	      <option value="">----</option>
	      <c:forEach var="subject" items="${subjectList}">
	        <option value="${subject.cd}" <c:if test="${param.subject == subject.cd}">selected</c:if>>${subject.name}</option>
	      </c:forEach>
	    </select>
	  </div>

	  <!-- 回数と検索ボタンを横並びに配置 -->
<div style="display: flex; align-items: flex-end; gap: 8px;">
  <!-- 回数ブロック（ラベル＋セレクト） -->
  <div class="filter-block">
    <label for="num">回数</label>
    <select name="num" id="num">
      <option value="">----</option>
      <c:forEach var="number" items="${numList}">
        <option value="${number}" <c:if test="${param.num == number}">selected</c:if>>${number}</option>
      </c:forEach>
    </select>
  </div>

  <!-- 検索ボタン -->
  <div class="filter-button">
    <button type="submit" class="filter-btn">検索</button>
  </div>
</div>

	</form>
	</div>

	<c:if test="${not empty tests}">
	  <form action="TestRegistExecute.action" method="post">
	    <table class="table table-bordered mt-4">
	      <thead>
	        <tr>
	          <th>入学年度</th>
	          <th>クラス</th>
	          <th>学生番号</th>
	          <th>氏名</th>
	          <th>点数</th>
	        </tr>
	      </thead>
	      <tbody>
	        <c:forEach var="test" items="${tests}">
	          <tr>
	            <td>${param.entYear}</td>
	            <td>${test.classNum}</td>
	            <td>
	              ${test.student.no}
	              <input type="hidden" name="studentNo" value="${test.student.no}" />
	            </td>
	            <td>${test.student.name}</td>
	            <td>
	              <input type="number" name="point" value="${test.point}" min="0" max="100" required />
	              <input type="hidden" name="classNum" value="${test.classNum}" />
	            </td>
	          </tr>
	        </c:forEach>
	      </tbody>
	    </table>

	    <!-- 隠しパラメータ -->
	    <input type="hidden" name="entYear" value="${param.entYear}" />
	    <input type="hidden" name="subject" value="${tests[0].subject.cd}" />
	    <input type="hidden" name="num" value="${tests[0].no}" />

	    <button type="submit" class="btn btn-primary mt-3">登録して終了</button>
	  </form>
	</c:if>

</section>

</c:param>
</c:import>
