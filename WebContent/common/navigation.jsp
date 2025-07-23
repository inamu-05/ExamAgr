<%-- サイドバー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
  .sidebar-button {
    display: block;
    width: 100%;
    text-align: left;
    border: none;
    padding: 0.75rem 1rem;
    margin-bottom: 1rem;
    font-weight: bold;
    text-decoration: none;
    position: relative;
    clip-path: polygon(0 0, 100% 0, 100% 100%, 10% 100%, 0 90%);
    transition: background-color 0.3s, color 0.3s;
    font-family: 'Segoe UI', sans-serif;
    box-sizing: border-box; /* 横幅計算でpadding含む */
    box-shadow: 2px 2px 4px rgba(0,0,0,0.1);
  }

  /* メニュー */
  .sidebar-menu {
    background-color: rgba(108, 117, 125, 0.1);
    color: #444;
  }
  .sidebar-menu:hover {
    background-color: rgba(108, 117, 125, 0.25);
    color: #222;
  }

  /* 学生管理 */
  .sidebar-student {
    background-color: #ffdbe7;
    color: #663366;
  }
  .sidebar-student:hover {
    background-color: #ffc8d4;
    color: #331133;
  }

  /* 成績管理 */
  .sidebar-grade {
    background-color: #d1e8ff;
    color: #336699;
  }
  .sidebar-grade:hover {
    background-color: #a6cdfd;
    color: #123355;
  }

  /* 科目管理 */
  .sidebar-subject {
    background-color: #ffead6;
    color: #994c00;
  }
  .sidebar-subject:hover {
    background-color: #ffddb3;
    color: #663300;
  }

  /* クラス管理 */
  .sidebar-class {
    background-color: #edffdb;
    color: #446622;
  }
  .sidebar-class:hover {
    background-color: #d6f3a9;
    color: #224411;
  }

  .sidebar-label {
    font-weight: bold;
    color: #444;
    margin-left: 0.5rem;
    margin-bottom: 0.5rem;
    display: block;
  }

  .nav-item::after {
    content: "";
    display: block;
    height: 1px;
    background-color: #aaa;
    margin: 0.5rem 0;
  }

  /* 成績管理のラベルだけインデント */
  .grade-label {
    margin-left: 1.5rem;
  }
</style>

<ul class="nav nav-pills flex-column mb-auto px-4">
  <li class="nav-item">
    <a href="Menu.action" class="sidebar-button sidebar-menu">メニュー</a>
  </li>
  <li class="nav-item">
    <a href="StudentList.action" class="sidebar-button sidebar-student">学生管理</a>
  </li>
  <li class="nav-item">
    <a href="TestRegist.action" class="sidebar-button sidebar-grade">成績管理 / 成績登録</a>
  </li>
  <li class="nav-item">
    <a href="TestList.action" class="sidebar-button sidebar-grade">成績管理 / 成績参照</a>
  </li>
  <li class="nav-item">
    <a href="SubjectList.action" class="sidebar-button sidebar-subject">科目管理</a>
  </li>
  <li class="nav-item">
    <a href="ClassList.action" class="sidebar-button sidebar-class">クラス管理</a>
  </li>
</ul>
