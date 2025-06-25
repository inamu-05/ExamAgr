<%-- エラーページ --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<title>エラーページ</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
${param.scripts}
</head>
<body>
	<div id="wrapper" class="container">
		<header
			class="d-flex flex-wrap justify-content-center py-3 px-5 mb-4 border-bottom border-2 bg-primary bg-opacity-10 bg-gradient">
			<div class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
				<h1 class="fs-1">得点管理システム</h1>
			</div>
			<c:if test="${user.isAuthenticated()}">
				<div class="nav align-self-end">
					<span class="nav-item px-2">${user.getName()}様</span>
					<a class="nav-item px-2" href="/exam_login/scoremanager/main/Logout.action">ログアウト</a>
				</div>
			</c:if>
		</header>

		<div class="row justify-content-center">
			<c:choose>
				<%-- ログイン済みの場合 --%>
				<c:when test="${user.isAuthenticated()}">
					<nav class="col-3">

						<ul class="nav nav-pills flex-column mb-auto px-4">
							<li class="nav-item my-3"><a href="/exam_login/scoremanager/main/Menu.action">メニュー</a></li>
							<li class="nav-item mb-3"><a href="/exam_login/scoremanager/main/StudentList.action">学生管理</a></li>
							<li class="nav-item">成績管理</li>
							<li class="nav-item mx-3 mb-3"><a href="/exam_login/scoremanager/main/TestRegist.action">成績登録</a></li>
							<li class="nav-item mx-3 mb-3"><a href="/exam_login/scoremanager/main/TestList.action">成績参照</a></li>
							<li class="nav-item mb-3"><a href="/exam_login/scoremanager/main/SubjectList.action">科目管理</a></li>
							<li class="nav-item mb-3"><a href="/exam_login/scoremanager/main/ClassList.action">クラス管理</a></li>
						</ul>

					</nav>
					<main class="col-9 border-start">
						<section>
							<p>エラーが発生しました</p>
						</section>
					</main>
				</c:when>
				<%-- 未ログインの場合 --%>
				<c:otherwise>
					<main class="col-8">
						<section>
							<p>エラーが発生しました</p>
						</section>
					</main>
				</c:otherwise>
			</c:choose>
		</div>
		<footer	class="py-2 my-4 bg-dark bg-opacity-10 border-top border-3 align-bottom">
			<p class="text-center text-muted mb-0">&copy; 2023 TIC </p>
			<p class="text-center text-muted mb-0">大原学園</p>
		</footer>

	</div>
</body>
</html>
