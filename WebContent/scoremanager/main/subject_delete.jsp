<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報削除</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal py-2 px-4" style="background-color: #ffd3a8;">科目情報削除</h2>

                <div class="mb-3">
                    「<strong><c:out value="${subject.name}" /></strong>（<strong><c:out value="${subject.cd}" /></strong>）」を削除してもよろしいですか？
                </div>

                <form action="SubjectDeleteExecute.action" method="post">
                    <!-- 削除対象の科目コードを送信 -->
                    <input type="hidden" name="cd" value="${subject.cd}">

                    <div>
                        <input type="submit" value="削除" class="btn btn-danger me-3">
                    </div>
                </form>
            	<div style="margin-top: 60px;">
    				<a href="SubjectList.action">戻る</a>
				</div>
        </section>
    </c:param>
</c:import>
