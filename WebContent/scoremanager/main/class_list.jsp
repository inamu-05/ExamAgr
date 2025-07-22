<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
  .custom-button {
    background-color: #b4f7b4;
    color: black;
    text-decoration: none;
    border-radius: 2rem;
    font-weight: bold;
    transition: 0.3s;
    display: inline-block;
    border: 2px solid black;
    padding: 0.5rem 1.5rem;
    box-shadow: 2px 2px 5px rgba(0,0,0,0.1);
  }

  .custom-button:hover {
    background-color: #a1eea1;
  }
</style>

<c:import url="/common/base.jsp">
  <c:param name="title">クラス管理</c:param>
  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal py-2 px-4" style="background-color: #edffdb;">
        クラス管理
      </h2>

      <div style="text-align: right; margin-bottom: 16px;">
        <a href="ClassCreate.action" class="custom-button">＋ 新規登録</a>
      </div>

      <div class="px-4 mb-2">
        学校名：${school.name}（${school.cd}）
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
