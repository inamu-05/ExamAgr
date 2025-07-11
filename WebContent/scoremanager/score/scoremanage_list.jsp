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
	        background-color: #e0e0e0; /* グレー調に */
	        border: 1px solid #aaa;
	        padding: 4px 16px;
	        border-radius: 4px;
	        font-weight: bold;
	        color: #333;
	      }

	      .filter-btn:hover {
	        background-color: #d0d0d0;
	      }

	      .table th,
	      .table td {
	        border-top: 1px solid #ccc !important; /* 統一された細い線 */
	        border-bottom: none !important;
	      }

	      .table thead th {
	        font-weight: bold;
	      }

	      .table td, .table th {
	        vertical-align: middle;
	      }
	</style>

	    <section class="me-4">
	<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

	      <!-- フィルター -->
	<div style="background: #fff; border: 1px solid #ccc; padding: 16px; border-radius: 8px; margin: 0 16px 16px 16px;">
	<form action="TestRegist.action" method="get" class="px-4 mb-3">

	  <label class="me-2">入学年度：</label>
	  <select name="entYear" class="me-4">
	    <option value="">----</option>
	    <c:forEach var="year" items="${entYearList}">
	      <option value="${year}" <c:if test="${param.entYear == year}">selected</c:if>>${year}</option>
	    </c:forEach>
	  </select>

	  <label class="me-2">クラス：</label>
	  <select name="classNum" class="me-4">
	    <option value="">----</option>
	    <c:forEach var="cls" items="${classNumList}">
	      <option value="${cls}" <c:if test="${param.classNum == cls}">selected</c:if>>${cls}</option>
	    </c:forEach>
	  </select>

	  	  <label class="me-2">科目：</label>
	  <select name="subject" class="me-4">
	    <option value="">----</option>
	    <c:forEach var="subject" items="${subjectList}">
	      <option value="${subject}" <c:if test="${param.subject == subject}">selected</c:if>>${subject}</option>
	    </c:forEach>
	  </select>

	  	  <label class="me-2">回数：</label>
	  <select name="num" class="me-4">
	    <option value="">----</option>
	    <c:forEach var="number" items="${numList}">
	      <option value="${number}" <c:if test="${param.num == number}">selected</c:if>>${number}</option>
	    </c:forEach>
	  </select>


	  <button type="submit" class="filter-btn me-3">検索</button>
	</form>
	</div>

</section>
</c:param>
</c:import>