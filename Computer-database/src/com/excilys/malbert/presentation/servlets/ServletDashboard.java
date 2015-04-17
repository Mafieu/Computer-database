package com.excilys.malbert.presentation.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.presentation.dto.ComputerDTO;
import com.excilys.malbert.service.ServiceComputer;
import com.excilys.malbert.utils.Mapper;
import com.excilys.malbert.utils.Utils;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/dashboard")
public class ServletDashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private int page, computersPerPage;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDashboard() {
	super();
	page = 1;
	computersPerPage = 50;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	int totalComputers = ServiceComputer.getNumberComputer();
	int newComputersPerPage = Utils.stringToInt(request
		.getParameter("limit"));
	int newPage = Utils.stringToInt(request.getParameter("page"));

	if (newComputersPerPage > 0) {
	    computersPerPage = newComputersPerPage;
	}
	if (!(newPage <= 0 || newPage - 1 > totalComputers / computersPerPage)) {
	    page = newPage;
	}

	List<Computer> computers = ServiceComputer.getSome(computersPerPage,
		(page - 1) * computersPerPage);
	List<ComputerDTO> computersDTO = new ArrayList<>();
	for (Computer computer : computers) {
	    computersDTO.add(Mapper.computerToComputerdto(computer));
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
	// TODO Auto-generated method stub
    }

}
