package com.excilys.malbert.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.malbert.service.ServiceComputer;
import com.excilys.malbert.utils.Utils;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/dashboard")
public class ServletDashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private int page, computersPerPage, totalComputers;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDashboard() {
	super();
	page = 1;
	computersPerPage = 50;
	totalComputers = 0;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	totalComputers = ServiceComputer.getNumberComputer();
	computersPerPage = Utils.stringToInt(request.getParameter("limit"));
	page = Utils.stringToInt(request.getParameter("page"));

	if (computersPerPage <= 0) {
	    computersPerPage = 50;
	}
	if (page <= 0 || page - 1 > totalComputers / computersPerPage) {
	    page = 1;
	}

	request.setAttribute("numberComputer", totalComputers);
	request.setAttribute(
		"listComputer",
		ServiceComputer.getSome(computersPerPage, (page - 1)
			* computersPerPage));
	request.setAttribute("page", page);
	request.setAttribute("limit", computersPerPage);

	this.getServletContext()
		.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
		.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

}
