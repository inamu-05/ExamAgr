<%-- メニューJSP都鳥 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">
        工藤得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">メニュー</h2>
            <div class="row text-center px-4 fs-3 my-5">

                <div class="col d-flex align-items-center justify-content-center mx-2 shadow"
                    style="height: 10rem; background-color: #ffdbe7; border-radius: 2rem;">
                    <a href="StudentList.action">学生管理</a>
                </div>

                <div class="col d-flex align-items-center justify-content-center mx-2 shadow"
                    style="height: 10rem; background-color: #d1e8ff; border-radius: 2rem;">
                    <div>
                        <div>成績管理</div>
                        <div><a href="TestRegist.action">成績登録</a></div>
                        <div><a href="TestList.action">成績参照</a></div>
                    </div>
                </div>

                <div class="col d-flex align-items-center justify-content-center mx-2 shadow"
                    style="height: 10rem; background-color:#ffead6; border-radius: 2rem;">
                    <a href="SubjectList.action">科目管理</a>
                </div>

                <div class="col d-flex align-items-center justify-content-center mx-2 shadow"
                    style="height: 10rem; background-color: #edffdb; border-radius: 2rem;">
                    <a href="ClassList.action">クラス管理</a>
                </div>

            </div>
        </section>
    </c:param>
</c:import>
