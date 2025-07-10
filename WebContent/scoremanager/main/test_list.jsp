<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
<c:param name="title">成績参照</c:param>

<c:param name="scripts"></c:param>

<c:param name="content">
<style>
/* 略：スタイルは今のままでOK */
</style>

	<section class="me-4">
	<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
	  成績参照
	  <c:if test="${isStudentSearch}">
	    （学生）
	  </c:if>
	  <c:if test="${isSubjectSearch}">
	    （科目）
	  </c:if>
	</h2>

	<!-- 検索フォーム -->
	<div style="background: #fff; border: 1px solid #ccc; padding: 16px; border-radius: 8px; margin: 0 16px 16px 16px;">

	    <!-- 科目情報 -->
	    <p class="fw-bold mb-2">科目情報</p>

	    <form action="TestListSubjectExecute.action" method="post" onsubmit="return validateForm();" class="mb-3">
	        <!-- ラベルと入力欄横並び -->
	        <div style="display: flex; align-items: center; gap: 16px; margin-bottom: 8px;">
	            <label>入学年度</label>
	            <select name="entYear">
	                <option value="">----</option>
	                <c:forEach var="year" items="${entYearList}">
	                    <option value="${year}" <c:if test="${param.entYear == year}">selected</c:if>>${year}</option>
	                </c:forEach>
	            </select>

	            <label>クラス</label>
	            <select name="classNum">
	                <option value="">----</option>
	                <c:forEach var="c" items="${classNumList}">
	                    <option value="${c}" <c:if test="${param.classNum == c}">selected</c:if>>${c}</option>
	                </c:forEach>
	            </select>

	            <label>科目</label>
	            <select name="subject">
	                <option value="">----</option>
	                <c:forEach var="c" items="${subjectList}">
	                    <option value="${c.cd}" <c:if test="${param.subject == c.cd}">selected</c:if>>${c.name}</option>
	                </c:forEach>
	            </select>

	            <button type="submit" class="filter-btn">検索</button>
	        </div>
	        <div id="error-message" class="text-danger mt-2"></div>
	    </form>

	    <!-- 区切り線 -->
	    <hr>

	    <!-- 学生情報 -->
	    <p class="fw-bold mb-2">学生情報</p>

	    <form action="TestListStudentExecute.action" method="post" onsubmit="return validateForm();">
	        <div style="display: flex; align-items: center; gap: 16px;">
	            <label>学生番号</label>
	            <input type="text" name="stuNum" placeholder="学生番号を入力してください" value="${param.stuNum != null ? param.stuNum : stuNum}" required />
	            <button type="submit" class="filter-btn">検索</button>
	        </div>

	        <c:if test="${not empty errorStuNum}">
	            <div class="error-message text-danger mt-2">${errorStuNum}</div>
	        </c:if>
	    </form>

	</div>
	<!-- まだ検索していない状態のメッセージ -->
<c:if test="${empty isStudentSearch and empty isSubjectSearch}">
    <div style="color: #3399FF; font-weight: bold; margin: 16px;">
        科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
    </div>
    </c:if>


	<!-- 学生検索結果表示 -->
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

<!-- 科目検索結果表示 -->
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

</c:param>
</c:import>
