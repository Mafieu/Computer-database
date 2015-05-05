package com.excilys.malbert.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.malbert.service.ServiceComputer;

@WebServlet("/deleteComputer")
public class ServletDeleteComputer extends ServletBasic {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ServiceComputer serviceComputer;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDeleteComputer() {
	super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// We should get id,id,id
	String[] ids = request.getParameter("selection").split(",");
	for (String id : ids) {
	    serviceComputer.deleteComputer(Long.parseLong(id));
	}

	response.sendRedirect("dashboard");
    }
}
