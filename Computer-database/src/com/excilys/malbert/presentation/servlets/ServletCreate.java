package com.excilys.malbert.presentation.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.presentation.dto.CompanyDTO;
import com.excilys.malbert.service.ServiceCompany;
import com.excilys.malbert.service.ServiceComputer;
import com.excilys.malbert.utils.Mapper;
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
	List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	for (Company company : ServiceCompany.getAllCompanies()) {
	    companiesDTO.add(Mapper.companyToCompanydto(company));
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
	Company company = ServiceCompany.getCompany(Utils.stringToLong(request
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
	    ServiceComputer.createComputer(new Computer(name, introduced,
			discontinued, company));
	    // Redirect to dashboard
	    response.sendRedirect("dashboard");
	}
    }

}
