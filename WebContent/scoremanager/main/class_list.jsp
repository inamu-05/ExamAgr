<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">クラス管理</c:param>
  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal py-2 px-4" style="background-color: #edffdb;">クラス管理</h2>
      <div style="text-align:right; margin-bottom:16px;">
        <a href="ClassCreate.action">新規登録</a>
      </div>
      <table class="table mb-5" style="border-collapse: collapse;">
        <thead>
          <tr>
            <th class="ps-4">クラスコード</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="classNum" items="${classNumList}">
            <tr>
              <td class="ps-4">${classNum}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </section>
  </c:param>
</c:import>
