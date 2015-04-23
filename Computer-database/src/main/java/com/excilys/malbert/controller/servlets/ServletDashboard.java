package com.excilys.malbert.controller.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.malbert.controller.dto.ComputerDTO;
import com.excilys.malbert.mapper.MapperComputer;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.ServiceComputer;
import com.excilys.malbert.util.Utils;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/dashboard")
public class ServletDashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceComputer serviceComputer = ServiceComputer.INSTANCE;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDashboard() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	int totalComputers = serviceComputer.getNumberComputer();
	int computersPerPage = Utils.stringToInt(request.getParameter("limit"));
	int page = Utils.stringToInt(request.getParameter("page"));

	if (computersPerPage <= 0) {
	    computersPerPage = 50;
	}
	if (page <= 0 || page - 1 > totalComputers / computersPerPage) {
	    page = 1;
	}

	List<Computer> computers;
	computers = serviceComputer.getSomeOrderedByAscending(computersPerPage,
		(page - 1) * computersPerPage, "computer.id");
	List<ComputerDTO> computersDTO = new ArrayList<>();
	for (Computer computer : computers) {
	    computersDTO.add(MapperComputer.computerToComputerdto(computer));
	}

	request.setAttribute("numberComputer", totalComputers);
	request.setAttribute("computers", computersDTO);
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
	response.sendRedirect("dashboard");
    }

}
