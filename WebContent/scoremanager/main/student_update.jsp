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
</style>

<section class="me-4">
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

    <div class="form-box">
        <form action="StudentUpdateExecute.action" method="post">

             <label class="form-label">入学年度</label>
    				<div>${student.entYear}</div>
    		<input type="hidden" name="entYear" value="${student.entYear}">

		    <label class="form-label">学生番号</label>
		    		<div>${student.no}</div>
		    <input type="hidden" name="stuNum" value="${student.no}">


            <label class="form-label">氏名</label>
			<input type="text" name="name" value="${student.name}" placeholder="氏名を入力してください" required />


            <label class="form-label">クラス</label>
            <select name="classNum">
                <c:forEach var="c" items="${classNumList}">
                    <option value="${c}" <c:if test="${student.classNum == c}">selected</c:if>>${c}</option>
                </c:forEach>
            </select>

             <label class="me-3">
	    			<input type="checkbox" name="isAttend" value="true" <c:if test="${param.isAttend == 'true'}">checked</c:if>> 在学中
	  		 </label>

            <div class="mt-3">
                <button type="submit" class="submit-btn">変更</button>
            </div>
        </form>

        <div class="mt-2">
            <a href="StudentList.action">戻る</a>
        </div>
    </div>
</section>

</c:param>
</c:import>
