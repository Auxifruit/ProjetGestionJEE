package com.example.projetjee.controller;

import com.example.projetjee.model.dao.StudentDAO;
import com.example.projetjee.model.dao.UserDAO;
import com.example.projetjee.model.entities.Role;
import com.example.projetjee.model.entities.Users;
import com.example.projetjee.util.GeneratorPdfGradeReport;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.example.projetjee.model.dao.ClasseDAO.getClasseById;
import static com.example.projetjee.model.dao.GradeDAO.getGradeByStudentId;
import static com.example.projetjee.model.dao.StudentDAO.getStudentById;
import static com.example.projetjee.model.dao.UserDAO.getUserById;

/**
 * Servlet for generating and displaying a student's grade report.
 * It retrieves the student's grades and calculates the average grade,
 * then allows downloading the grade report as a PDF.
 */
@WebServlet(name = "StudentGradeReportServlet", value = "/student-grade-report-servlet")
public class StudentGradeReportServlet extends HttpServlet {
    private final GeneratorPdfGradeReport pdfGenerator = new GeneratorPdfGradeReport();
    /**
     * Handles the GET request to display a student's grade report.
     * This method retrieves the student's grades, calculates the average grade,
     * and prepares the data for displaying the grade report in the JSP view.
     *
     * @param request the HttpServletRequest object containing the request from the client
     * @param response the HttpServletResponse object containing the response to the client
     * @throws IOException if an input or output error occurs
     * @throws ServletException if the request for the GET cannot be handled
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        Integer connectedUserId = (Integer) session.getAttribute("user");
        String userIdString = request.getParameter("userId");

        // Check if user is logged in and if userId is provided
        if(connectedUserId == null || (userIdString == null || userIdString.isEmpty())) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        Users connectedUser = UserDAO.getUserById(connectedUserId);
        Integer userId = Integer.parseInt(userIdString);

        if(connectedUser.getUserRole().equals(Role.student) && !connectedUserId.equals(userId)) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        // if not a student go home
        if(getUserById(userId).getUserRole() != Role.student) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        // get the function initialy use for the PDF generator (had to set it public)
        request.setAttribute("subjectCourseGrades", pdfGenerator.fetchSubjectCourseGrades(userId));

        // set userId
        request.setAttribute("student", getUserById(userId));

        // set className
        Integer classId = StudentDAO.getStudentById(userId).getClassId();
        if(classId == null) {
            request.setAttribute("className", "Aucune");
        } else {
            request.setAttribute("className", getClasseById(classId).getClassName());
        }

        // set mean (with the mean calculator of the PDF generator
        double overallMean = pdfGenerator.calculateAverage(getGradeByStudentId(userId)); // get the mean
        String formattedMean = String.format("%.2f", overallMean); // set the format to only 2 number after the ','
        request.setAttribute("mean", formattedMean);


        request.getRequestDispatcher("/WEB-INF/jsp/pages/StudentGradeReport.jsp").forward(request, response);
    }
    /**
     * Handles the POST request to generate and download the grade report PDF.
     * It retrieves the student's ID, generates the PDF, and sends it as a download response.
     *
     * @param request the HttpServletRequest object containing the request from the client
     * @param response the HttpServletResponse object containing the response to the client
     * @throws IOException if an input or output error occurs
     * @throws ServletException if the request for the POST cannot be handled
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // get the id and send it to the form
            int userId = Integer.parseInt(request.getParameter("userId"));
            System.out.println("userId: "+userId);

            // generate the PDF
            byte[] pdfContent = pdfGenerator.generatePDF(getUserById(userId));

            // verify if the PDF is empty (error)
            if (pdfContent.length == 0) {
                throw new RuntimeException("PDF FILE EMPTY");
            }

            // set the HTTP response for the download
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"Relevé_de_notes.pdf\"");

            // send data
            response.getOutputStream().write(pdfContent);
            response.getOutputStream().flush();

            System.out.println("PDF WELL SEND");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ERROR IN THE GENERATION OF THE PDF");
        }
    }
}
