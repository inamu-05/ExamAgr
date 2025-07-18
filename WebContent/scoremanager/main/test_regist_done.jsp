<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <h2 class="h3 mb-3 fw-normal py-2 px-4" style="background-color: #d1e8ff;">成績登録</h2>

    <c:choose>
        <c:when test="${success}">
            <p class="text-center" style="background-color:#66CC99">
                成績の登録が完了しました。
            </p>
        </c:when>
        <c:otherwise>
            <p class="text-center" style="background-color:#FF9999">
                成績の登録に失敗しました。もう一度お試しください。
            </p>
        </c:otherwise>
    </c:choose>

    <div class="mt-2">
    	<span style="margin-right: 32px;">
        	<a href="TestRegist.action">戻る</a>
        </span>
        <span>
        	<a href="TestList.action">成績参照</a>
        </span>
    </div>
</section>

</c:param>
</c:import>