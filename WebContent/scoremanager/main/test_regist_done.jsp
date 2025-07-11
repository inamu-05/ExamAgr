<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績登録完了</c:param>

  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-success bg-opacity-10 py-2 px-4">成績登録完了</h2>

      <div style="background: #fff; border: 1px solid #ccc; padding: 16px; border-radius: 8px; margin: 0 16px;">
        <c:choose>
          <c:when test="${success}">
            <p style="color: green; font-weight: bold;">成績の登録が完了しました。</p>
          </c:when>
          <c:otherwise>
            <p style="color: red; font-weight: bold;">成績の登録に失敗しました。もう一度お試しください。</p>
          </c:otherwise>
        </c:choose>

        <a href="TestRegist.action" class="btn btn-secondary mt-3">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
