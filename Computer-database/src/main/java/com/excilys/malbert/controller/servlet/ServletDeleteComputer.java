package com.excilys.malbert.controller.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.malbert.service.IServiceComputer;

@Controller
@RequestMapping(value = "/deleteComputer")
public class ServletDeleteComputer {

    @Autowired
    private IServiceComputer serviceComputer;

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(@ModelAttribute("selection") String selection) {
	// We should get id,id,id
	String[] ids = selection.split(",");
	for (String id : ids) {
	    serviceComputer.deleteComputer(Long.parseLong(id));
	}

	return "redirect:dashboard";
    }
}
