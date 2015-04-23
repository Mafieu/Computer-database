package com.excilys.malbert.controller.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.malbert.controller.dto.CompanyDTO;
import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.mapper.MapperCompany;
import com.excilys.malbert.mapper.MapperComputer;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.ServiceCompany;
import com.excilys.malbert.service.ServiceComputer;
import com.excilys.malbert.util.Utils;

/**
 * Servlet implementation class ServletEdit
 */
@WebServlet("/editComputer")
public class ServletEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceComputer serviceComputer = ServiceComputer.INSTANCE;
    private ServiceCompany serviceCompany = ServiceCompany.INSTANCE;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEdit() {
	super();
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
	    List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	    try {
		computer = serviceComputer.getComputer(id);
	    } catch (DAOException e) {
		this.getServletContext()
			.getRequestDispatcher("/WEB-INF/views/500.jsp")
			.forward(request, response);
	    }
	    for (Company company : serviceCompany.getAllCompanies()) {
		companiesDTO.add(MapperCompany.companyToCompanydto(company));
	    }

	    request.setAttribute("companies", companiesDTO);
	    request.setAttribute("computer",
		    MapperComputer.computerToComputerdto(computer));
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
	LocalDateTime introduced = Utils.stringToLocaldatetime(request
		.getParameter("introduced"));
	LocalDateTime discontinued = Utils.stringToLocaldatetime(request
		.getParameter("discontinued"));
	Company company = serviceCompany.getCompany(Utils.stringToLong(request
		.getParameter("companyId")));
	serviceComputer.updateComputer(new Computer(id, name, introduced,
		discontinued, company));
	response.sendRedirect("dashboard");
    }

}
