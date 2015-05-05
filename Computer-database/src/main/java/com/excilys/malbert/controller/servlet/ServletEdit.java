package com.excilys.malbert.controller.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.malbert.controller.dto.CompanyDTO;
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
public class ServletEdit extends ServletBasic {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ServiceComputer serviceComputer;
    @Autowired
    private ServiceCompany serviceCompany;

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
	    computer = serviceComputer.getComputer(id);
	    if (computer == null) {
		response.sendRedirect("dashboard");
	    } else {
		for (Company company : serviceCompany.getAllCompanies()) {
		    companiesDTO
			    .add(MapperCompany.companyToCompanydto(company));
		}

		request.setAttribute("companies", companiesDTO);
		request.setAttribute("computer",
			MapperComputer.computerToComputerdto(computer));
		this.getServletContext()
			.getRequestDispatcher("/WEB-INF/views/editComputer.jsp")
			.forward(request, response);
	    }
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
	if (name == "" || request.getParameter("introduced") != ""
		&& introduced == null
		|| request.getParameter("discontinued") != ""
		&& discontinued == null) {
	    request.setAttribute("error", true);
	    this.getServletContext()
		    .getRequestDispatcher("/WEB-INF/views/addComputer.jsp")
		    .forward(request, response);
	} else {
	    serviceComputer.updateComputer(new Computer(id, name, introduced,
		    discontinued, company));
	    response.sendRedirect("dashboard");
	}
    }

}
