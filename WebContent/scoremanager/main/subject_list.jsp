<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
  .custom-button {
    background-color: #ffad5b;
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
    background-color: #ff9933;
    color: black;
  }
</style>

<c:import url="/common/base.jsp">
<c:param name="title">科目管理</c:param>
<c:param name="content">
  <section class="me-4">
    <h2 class="h3 mb-3 fw-normal py-2 px-4" style="background-color: #ffd3a8;">科目管理</h2>
    <div style="text-align:right; margin-bottom:16px;">
      <a href="SubjectCreate.action" class="custom-button">＋ 新規登録</a>
    </div>
    <div class="px-4 mb-2">登録件数：${fn:length(subjects)}件</div>

    <table class="table mb-5" style="border-collapse: collapse;">
      <thead>
        <tr>
          <th class="ps-4">科目コード</th>
          <th>科目名</th>

        </tr>
      </thead>
      <tbody>
        <c:forEach var="subjects" items="${subjects}">
          <tr>
            <td class="ps-4">${subjects.cd}</td>
            <td>${subjects.name}</td>
            <td><a href="SubjectUpdate.action?cd=${subjects.cd}">変更</a></td>
            <td><a href="SubjectDelete.action?cd=${subjects.cd}">削除</a></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </section>
</c:param>
</c:import>　


