<%-- ログアウトJSP 龍也
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
	.custom-login-button {
	    background-color: #d1a3ff;
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

	.custom-login-button:hover {
		background-color: #bf7fff;
		color: black;
	}
</style>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<div id="wrap_box">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">ログアウト</h2>
			<div id="wrap_box">
				<p class="text-center" style="background-color:#66CC99">ログアウトしました</p>
				<a href="../Login.action" class="custom-login-button">ログイン</a>
			</div>
		</div>
	</c:param>
</c:import>