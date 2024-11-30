<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projetjee.model.entities.Users" %>
<%@ page import="com.example.projetjee.model.entities.Grade" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Relevé de notes</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/elements/sidebar.jsp" />
<div>
  <form id="downloadbutton" action="<%= request.getContextPath() %>/student-grade-report-servlet" method="post">
    <input type="hidden" name="userId" value="<%= ((Users) request.getAttribute("student")).getUserId() %>">
    <button type="submit"><i class="fas fa-file-pdf" style="margin-right: 5px;"></i> Télécharger au format PDF</button>
  </form>
  <h1>Relevé de notes</h1>

  <table id="gradesTable">
    <thead>
    <tr>
      <th>Matière</th>
      <th>Moyenne</th>
      <th>Notes(Coefficient)</th>
    </tr>
    </thead>
    <tbody>
    <!-- Moyenne générale -->
    <tr>
      <td><strong>Moyenne Générale</strong></td>
      <td>
        <%
          double totalGeneral = 0;
          int totalSubjects = 0;
          Map<String, Map<String, List<Grade>>> subjectCourseGrades = (Map<String, Map<String, List<Grade>>>) request.getAttribute("subjectCourseGrades");

          if (subjectCourseGrades != null && !subjectCourseGrades.isEmpty()) {
            for (Map.Entry<String, Map<String, List<Grade>>> subjectEntry : subjectCourseGrades.entrySet()) {
              double subjectAverage = 0;
              int totalGradesForSubject = 0;
              double totalGradeValueForSubject = 0;
              int totalCoefficientForSubject = 0;

              Map<String, List<Grade>> subjectCourses = subjectEntry.getValue();
              for (Map.Entry<String, List<Grade>> courseEntry : subjectCourses.entrySet()) {
                List<Grade> grades = courseEntry.getValue();
                for (Grade grade : grades) {
                  totalGradeValueForSubject += grade.getGradeValue() * grade.getGradeCoefficient();
                  totalCoefficientForSubject += grade.getGradeCoefficient();
                }
                totalGradesForSubject++;
              }
              subjectAverage = (totalCoefficientForSubject > 0) ? (totalGradeValueForSubject / totalCoefficientForSubject) : 0;

              totalGeneral += subjectAverage;
              totalSubjects++;
            }
          }
          double generalAverage = (totalSubjects > 0) ? totalGeneral / totalSubjects : 0;
        %>
        <%= String.format("%.2f", generalAverage) %>
      </td>
      <td></td>
    </tr>

    <!-- Parcours des UE -->
    <%
      if (subjectCourseGrades != null && !subjectCourseGrades.isEmpty()) {
        for (Map.Entry<String, Map<String, List<Grade>>> subjectEntry : subjectCourseGrades.entrySet()) {
          String subjectName = subjectEntry.getKey();
    %>
    <tr>
      <td><strong><%= subjectName %></strong></td>
      <td>
        <%
          double subjectAverage = 0;
          int totalGradesForSubject = 0;
          double totalGradeValueForSubject = 0;
          int totalCoefficientForSubject = 0;

          Map<String, List<Grade>> subjectCoursesUE = subjectEntry.getValue();
          for (Map.Entry<String, List<Grade>> courseEntry : subjectCoursesUE.entrySet()) {
            List<Grade> grades = courseEntry.getValue();
            for (Grade grade : grades) {
              totalGradeValueForSubject += grade.getGradeValue() * grade.getGradeCoefficient();
              totalCoefficientForSubject += grade.getGradeCoefficient();
            }
            totalGradesForSubject++;
          }
          subjectAverage = (totalCoefficientForSubject > 0) ? (totalGradeValueForSubject / totalCoefficientForSubject) : 0;
        %>
        <%= String.format("%.2f", subjectAverage) %>
      </td>
      <td></td>
    </tr>

    <!-- Parcours des matières sous chaque UE -->
    <%
      Map<String, List<Grade>> subjectCoursesMatiere = subjectEntry.getValue();
      for (Map.Entry<String, List<Grade>> courseEntry : subjectCoursesMatiere.entrySet()) {
        String courseName = courseEntry.getKey();
        List<Grade> grades = courseEntry.getValue();
    %>
    <tr>
      <td><%= courseName %></td>
      <td>
        <%
          double courseAverage = 0;
          double totalGradeValueForCourse = 0;
          int totalCoefficientForCourse = 0;

          for (Grade grade : grades) {
            totalGradeValueForCourse += grade.getGradeValue() * grade.getGradeCoefficient();
            totalCoefficientForCourse += grade.getGradeCoefficient();
          }

          courseAverage = (totalCoefficientForCourse > 0) ? (totalGradeValueForCourse / totalCoefficientForCourse) : 0;
        %>
        <%= String.format("%.2f", courseAverage) %>
      </td>
      <td>
        <%
          for (Grade grade : grades) {
        %>
        <span title="<%= grade.getGradeName() %>"><%= grade.getGradeValue() %> (<%= grade.getGradeCoefficient() %>)</span><%= grades.indexOf(grade) < grades.size() - 1 ? " " : "" %>
        <%
          }
        %>
      </td>
    </tr>
    <%
          }
        }
      }
    %>
    </tbody>
  </table>
</div>
</body>
</html>
