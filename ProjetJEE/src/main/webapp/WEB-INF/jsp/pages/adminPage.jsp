<%@ page import="com.example.projetjee.model.dao.UserDAO" %>
<%@ page import="com.example.projetjee.model.entities.Role" %><%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 22/11/2024
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
            #OldInfos {
                width: 100%;
                max-width: 600px;
                margin: 20px auto;
                padding: 20px;
                background-color: #283348;
                color: white;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            }
            .button-container {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                justify-content: center;
            }
            .button-container button {
                flex: 1 1 calc(50% - 15px);
                max-width: calc(50% - 15px);
                padding: 15px;
                background-color: #C4DFE9;
                color: #283348;
                font-size: 16px;
                text-align: center;
                border: none;
                border-radius: 10px;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.3s ease;
            }
            .button-container button:hover {
                background-color: #A6BBD6;
                transform: scale(1.05);
            }
        </style>
</head>
<%
    Integer userId = (Integer) session.getAttribute("user");
    if(userId == null || !Role.administrator.equals(UserDAO.getUserById(userId).getUserRole())) {
      response.sendRedirect("index.jsp");
      return;
    }
%>
<body>
<jsp:include page="/elements/sidebar.jsp" />
<div>
<h1>Page admin</h1>
    <div id="OldInfos">
        <div class="button-container">
            <button onclick="window.location.href='${pageContext.request.contextPath}/userToValidateManager-servlet'">
                Gérer les inscriptions
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/userManager-servlet?roleFilter=student'">
                Gérer les utilisateurs
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/lessonManager-servlet'">
                Gérer les séances
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/subjectManager-servlet'">
                Gérer les matières
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/courseManager-servlet'">
                Gérer les cours
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/majorManager-servlet'">
                Gérer les filières
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/gradeManager-servlet'">
                Gérer les notes
            </button>
            <button onclick="window.location.href='${pageContext.request.contextPath}/classesManager-servlet'">
                Gérer les classes
            </button>
        </div>
    </div>
</div>
</body>
</html>
