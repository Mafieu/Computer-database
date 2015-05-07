package com.excilys.malbert.controller.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.malbert.controller.Page;
import com.excilys.malbert.service.IServiceCompany;
import com.excilys.malbert.service.IServiceComputer;

@Controller
@RequestMapping(value = "/dashboard")
public class ServletDashboard {
    @Autowired
    private IServiceComputer serviceComputer;
    @Autowired
    private IServiceCompany serviceCompany;

    @RequestMapping(method = RequestMethod.GET)
    public String dashboard(@ModelAttribute Page page, Model model) {
	if (!page.isValid(serviceComputer, serviceCompany)) {
	    model.addAttribute("error", true);
	    model.addAttribute("errorMessage", "Invalid arguments");
	}
	model.addAttribute(page);
	return "dashboard";
    }
}
