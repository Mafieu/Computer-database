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
import com.excilys.malbert.mapper.MapperCompany;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.ServiceCompany;
import com.excilys.malbert.service.ServiceComputer;
import com.excilys.malbert.util.Utils;

/**
 * Servlet implementation class ServletCreate
 */
@WebServlet("/addComputer")
public class ServletCreate extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceCompany serviceCompany = ServiceCompany.INSTANCE;
    private ServiceComputer serviceComputer = ServiceComputer.INSTANCE;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCreate() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	for (Company company : serviceCompany.getAllCompanies()) {
	    companiesDTO.add(MapperCompany.companyToCompanydto(company));
	}

	request.setAttribute("companies", companiesDTO);
	request.setAttribute("error", false);

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
	LocalDateTime introduced = Utils.stringToLocaldatetime(request
		.getParameter("introduced"));
	LocalDateTime discontinued = Utils.stringToLocaldatetime(request
		.getParameter("discontinued"));
	Company company = null;
	if (!request.getParameter("companyId").equals("0")) {
	    company = serviceCompany.getCompany(Utils.stringToLong(request
		    .getParameter("companyId")));
	} else {
	    company = new Company(0, "");
	}
	if (name == "" || request.getParameter("introduced") != ""
		&& introduced == null
		|| request.getParameter("discontinued") != ""
		&& discontinued == null) {
	    List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	    for (Company company1 : serviceCompany.getAllCompanies()) {
		companiesDTO.add(MapperCompany.companyToCompanydto(company1));
	    }

	    request.setAttribute("companies", companiesDTO);
	    request.setAttribute("error", true);
	    this.getServletContext()
		    .getRequestDispatcher("/WEB-INF/views/addComputer.jsp")
		    .forward(request, response);
	} else {
	    serviceComputer.createComputer(new Computer(name, introduced,
		    discontinued, company));
	    // Redirect to dashboard
	    response.sendRedirect("dashboard");
	}
    }

}
