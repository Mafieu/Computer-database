package com.excilys.malbert.presentation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.malbert.persistence.DAOException;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.ServiceCompany;
import com.excilys.malbert.service.ServiceComputer;
import com.excilys.malbert.utils.Utils;

/**
 * Servlet implementation class ServletEdit
 */
@WebServlet("/editComputer")
public class ServletEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEdit() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	long id = Utils.stringToLong(request.getParameter("id"));
	if (id <= 0) {
	    response.sendRedirect("dashboard");
	} else {
	    Computer computer = null;
	    List<Company> companies = ServiceCompany.getAllCompanies();
	    try {
		computer = ServiceComputer.getComputer(id);
	    } catch (DAOException e) {
		this.getServletContext()
			.getRequestDispatcher("/WEB-INF/views/500.jsp")
			.forward(request, response);
	    }
	    request.setAttribute("companies", companies);
	    request.setAttribute("computer", computer);
	    this.getServletContext()
		    .getRequestDispatcher("/WEB-INF/views/editComputer.jsp")
		    .forward(request, response);
	}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	long id = Utils.stringToLong(request.getParameter("computerId"));
	String name = request.getParameter("computerName");
	LocalDateTime introduced = Utils.getDate(request
		.getParameter("introduced"));
	LocalDateTime discontinued = Utils.getDate(request
		.getParameter("discontinued"));
	Company company = ServiceCompany.getCompany(Utils.stringToLong(request
		.getParameter("companyId")));
	ServiceComputer.updateComputer(new Computer(id, name, introduced,
		discontinued, company));
	response.sendRedirect("dashboard");
    }

}
