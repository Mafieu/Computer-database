package com.excilys.malbert.presentation;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.ServiceCompany;
import com.excilys.malbert.service.ServiceComputer;
import com.excilys.malbert.utils.Utils;

/**
 * Servlet implementation class ServletCreate
 */
@WebServlet("/addComputer")
public class ServletCreate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCreate() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	request.setAttribute("listCompany", ServiceCompany.getAllCompanies());

	this.getServletContext()
		.getRequestDispatcher("/WEB-INF/views/addComputer.jsp")
		.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	String name = request.getParameter("computerName");
	LocalDateTime introduced = Utils.getDate(request
		.getParameter("introduced"));
	LocalDateTime discontinued = Utils.getDate(request
		.getParameter("discontinued"));
	Company company = ServiceCompany.getCompany(Utils.stringToLong(request
		.getParameter("companyId")));
	ServiceComputer.createComputer(new Computer(name, introduced,
		discontinued, company));
	// Redirect to dashboard
	response.sendRedirect("dashboard");
    }

}
