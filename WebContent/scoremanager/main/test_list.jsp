<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績参照</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <style>
      .filter-btn {
        background-color: #e0e0e0;
        border: 1px solid #aaa;
        padding: 4px 16px;
        border-radius: 4px;
        font-weight: bold;
        color: #333;
      }
      .filter-btn:hover {
        background-color: #d0d0d0;
      }
    </style>

    <section class="me-4">
	<h2 class="h3 mb-3 fw-normal py-2 px-4" style="background-color: #d1e8ff;">
        成績参照
        <c:if test="${isStudentSearch}">（学生）</c:if>
        <c:if test="${isSubjectSearch}">（科目）</c:if>
      </h2>

      <div style="background: #fff; border: 1px solid #ccc; padding: 16px; border-radius: 8px; margin: 0 16px 16px 16px;">

        <!-- 科目情報検索フォーム -->
        <form action="TestListSubjectExecute.action" method="post" onsubmit="return validateForm();" class="mb-3">
          <div style="display: flex; align-items: flex-end; gap: 24px; margin-bottom: 8px;">
            <div style="display: flex; flex-direction: column; justify-content: flex-end;">
              <span class="fw-bold">科目情報</span>
            </div>

            <div style="display: flex; flex-direction: column; min-width: 120px;">
              <label for="entYear">入学年度</label>
              <select name="entYear" id="entYear">
                <option value="">----</option>
                <c:forEach var="year" items="${entYearList}">
                  <option value="${year}" <c:if test="${param.entYear == year}">selected</c:if>>${year}</option>
                </c:forEach>
              </select>
            </div>

            <div style="display: flex; flex-direction: column; min-width: 120px;">
              <label for="classNum">クラス</label>
              <select name="classNum" id="classNum">
                <option value="">----</option>
                <c:forEach var="c" items="${classNumList}">
                  <option value="${c}" <c:if test="${param.classNum == c}">selected</c:if>>${c}</option>
                </c:forEach>
              </select>
            </div>

            <div style="display: flex; flex-direction: column; min-width: 120px;">
              <label for="subject">科目</label>
              <select name="subject" id="subject">
                <option value="">----</option>
                <c:forEach var="c" items="${subjectList}">
                  <option value="${c.cd}" <c:if test="${param.subject == c.cd}">selected</c:if>>${c.name}</option>
                </c:forEach>
              </select>
            </div>

            <div style="display: flex; align-items: flex-end;">
              <button type="submit" class="filter-btn me-3">検索</button>
            </div>
          </div>

          <!-- エラーメッセージ表示（サーバーサイド） -->
          <c:if test="${not empty errorSubjectSearch}">
            <div class="text-danger mt-2">${errorSubjectSearch}</div>
          </c:if>

          <!-- クライアントサイド用エラー領域 -->
          <div id="error-message" class="text-danger mt-2"></div>
        </form>

        <hr>

        <!-- 学生情報検索フォーム -->
        <form action="TestListStudentExecute.action" method="post" onsubmit="return validateForm();">
          <div style="display: flex; align-items: flex-end; gap: 24px; margin-top: 16px;">
            <div style="display: flex; flex-direction: column; justify-content: flex-end;">
              <span class="fw-bold">学生情報</span>
            </div>

            <div style="display: flex; flex-direction: column;">
              <label for="stuNum">学生番号</label>
              <input type="text" id="stuNum" name="stuNum"
                     placeholder="学生番号を入力してください"
                     value="${param.stuNum != null ? param.stuNum : stuNum}"
                     style="width: 200px;"
                     required />
            </div>

            <div style="display: flex; align-items: flex-end;">
              <button type="submit" class="filter-btn">検索</button>
            </div>
          </div>

          <c:if test="${not empty errorStuNum}">
            <div class="error-message text-danger mt-2">${errorStuNum}</div>
          </c:if>
        </form>
      </div>

      <!-- 初期メッセージ -->
      <c:if test="${empty isStudentSearch and empty isSubjectSearch}">
        <div style="color: #3399FF; font-weight: bold; margin: 16px;">
          科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
        </div>
      </c:if>

      <!-- 学生検索結果 -->
      <c:if test="${isStudentSearch and not empty testListStudent}">
        <div class="px-4 mb-2">学生：${student.name}（${student.no}）</div>
        <table class="table mb-5" style="border-collapse: collapse;">
          <thead>
            <tr>
              <th>科目名</th>
              <th>科目コード</th>
              <th>回数</th>
              <th>点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="test" items="${testListStudent}">
              <tr>
                <td>${test.subjectName}</td>
                <td>${test.subjectCd}</td>
                <td>${test.num}</td>
                <td>${test.point}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

      <c:if test="${isStudentSearch and empty testListStudent}">
        <div class="px-4 mb-2">学生：${student.name}（${student.no}）</div>
        <div class="px-4 mb-4 fw-bold">成績情報が存在しませんでした。</div>
      </c:if>

      <!-- 科目検索結果 -->
      <c:if test="${isSubjectSearch and not empty testListSubject}">
        <div class="px-4 mb-2">科目：${subject.name}（${subject.cd}）</div>
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
            <c:forEach var="test" items="${testListSubject}">
              <tr>
                <td>${test.entYear}</td>
                <td>${test.classNum}</td>
                <td>${test.studentNo}</td>
                <td>${test.studentName}</td>
                <td>
                  <c:choose>
                    <c:when test="${test.getPoint(1) != null}">
                      ${test.getPoint(1)}
                    </c:when>
                    <c:otherwise>-</c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <c:choose>
                    <c:when test="${test.getPoint(2) != null}">
                      ${test.getPoint(2)}
                    </c:when>
                    <c:otherwise>-</c:otherwise>
                  </c:choose>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

      <c:if test="${isSubjectSearch and empty testListSubject}">
        <div class="px-4 mb-4 fw-bold">学生情報が存在しませんでした。</div>
      </c:if>
    </section>
  </c:param>
</c:import>
