<%-- ヘッダー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
	h1{
		font-family: 'fantasy', serif;
	}
	.custom-logout-button {
	    background-color: #c0c0c0;
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

	.custom-logout-button:hover {
		background-color: #808080;
		color: black;
	}

	.nav {
	    border-bottom: none !important;
	}

	.nav-item {
	    border-bottom: none !important;
	}


</style>

<div class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
	<h1 class="fs-1">得点管理システム</h1>
</div>
<c:if test="${user.isAuthenticated()}">
	<div class="nav align-self-end">
		<span class="nav-item px-2">${user.getName()}　様</span>
	</div>
	<div style="text-align: right; margin-bottom: 16px;">
        <a href="Logout.action" class="custom-logout-button">ログアウト</a>
    </div>

</c:if>