package com.excilys.malbert.controller.servlets;

import java.io.IOException;

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
	Page<ComputerDTO> computers = new Page<ComputerDTO>();
	int totalCount = serviceComputer.getNumberComputer();
	int countPerPage = Utils.stringToInt(request.getParameter("limit"));
	int page = Utils.stringToInt(request.getParameter("page"));
	String order = request.getParameter("order");
	String column = request.getParameter("column");

	computers.setTotalCount(totalCount);
	computers.setCountPerPage(countPerPage);
	computers.setPage(page);
	computers.setOrder(order);
	computers.setColumn(column);

	if (!computers.isValid()) {
	    request.setAttribute("error", true);
	    request.setAttribute("errorMessage", "Invalid arguments");
	}

	if (computers.getOrder().equals("asc")) {
	    for (Computer computer : serviceComputer.getSomeOrderedByAscending(
		    computers.getCountPerPage(), (computers.getPage() - 1)
			    * computers.getCountPerPage(),
		    computers.getColumn())) {
		computers.getData().add(
			MapperComputer.computerToComputerdto(computer));
	    }
	} else {
	    for (Computer computer : serviceComputer
		    .getSomeOrderedByDescending(
			    computers.getCountPerPage(),
			    (computers.getPage() - 1)
				    * computers.getCountPerPage(),
			    computers.getColumn())) {
		computers.getData().add(
			MapperComputer.computerToComputerdto(computer));
	    }
	}

	for (Computer computer : serviceComputer.getSomeOrderedByAscending(
		computers.getCountPerPage(), (computers.getPage() - 1)
			* computers.getCountPerPage(), "computer.id")) {
	    computers.getData().add(
		    MapperComputer.computerToComputerdto(computer));
	}

	request.setAttribute("page", computers);

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
