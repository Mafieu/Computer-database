package com.excilys.malbert.controller.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.malbert.controller.Page;
import com.excilys.malbert.controller.dto.ComputerDTO;
import com.excilys.malbert.mapper.MapperComputer;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.ServiceComputer;
import com.excilys.malbert.util.Utils;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/dashboard")
public class ServletDashboard extends ServletBasic {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ServiceComputer serviceComputer;

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
	String search = request.getParameter("search");

	computers.setTotalCount(totalCount);
	computers.setCountPerPage(countPerPage);
	computers.setPage(page);
	computers.setOrder(order);
	computers.setColumn(column);
	computers.setSearch(search);

	if (!computers.isValid()) {
	    if (!(countPerPage == 0 && page == 0 && order == null
		    && column == null && search == null)) {
		request.setAttribute("error", true);
		request.setAttribute("errorMessage", "Invalid arguments");
	    }
	}

	List<Computer> list = null;
	if (computers.getOrder().equals("asc")) {
	    if (!computers.isSearchValid()) {
		list = serviceComputer.getSomeOrderedByAscending(
			computers.getCountPerPage(), (computers.getPage() - 1)
				* computers.getCountPerPage(),
			computers.getColumn());
	    } else {
		computers.setTotalCount(serviceComputer
			.getNumberComputerSearch(search));
		list = serviceComputer.getSomeSearch(
			computers.getCountPerPage(), (computers.getPage() - 1)
				* computers.getCountPerPage(),
			computers.getColumn(), computers.getOrder(),
			computers.getSearch());
	    }
	} else {
	    if (!computers.isSearchValid()) {
		list = serviceComputer.getSomeOrderedByDescending(
			computers.getCountPerPage(), (computers.getPage() - 1)
				* computers.getCountPerPage(),
			computers.getColumn());
	    } else {
		computers.setTotalCount(serviceComputer
			.getNumberComputerSearch(search));
		list = serviceComputer.getSomeSearch(
			computers.getCountPerPage(), (computers.getPage() - 1)
				* computers.getCountPerPage(),
			computers.getColumn(), computers.getOrder(),
			computers.getSearch());
	    }
	}

	for (Computer computer : list) {
	    computers.getData().add(
		    MapperComputer.computerToComputerdto(computer));
	}

	request.setAttribute("page", computers);

	this.getServletContext()
		.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
		.forward(request, response);
    }

}
