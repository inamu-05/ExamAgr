<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:import url="/common/base.jsp">
<c:param name="title">科目管理や</c:param>
<c:param name="content">
  <section class="me-4">
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目一覧</h2>
    <div style="text-align:right; margin-bottom:16px;">
      <a href="SubjectCreate.action">新規登録</a>
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
            <td><a href="SubjectEdit.action?cd=${subjects.cd}">変更</a></td>
            <td><a href="SubjectDelete.action?cd=${subjects.cd}">削除</a></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </section>
</c:param>
</c:import>　


