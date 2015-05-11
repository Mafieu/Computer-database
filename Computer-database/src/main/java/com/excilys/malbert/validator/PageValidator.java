package com.excilys.malbert.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.malbert.controller.Page;
import com.excilys.malbert.util.DbValidator;

public class PageValidator implements Validator {

    public boolean supports(Class<?> param) {
	return Page.class.equals(param);
    }

    @Override
    public void validate(Object obj, Errors errors) {
	Page page = (Page) obj;
	if (page.getCountPerPage() <= 0) {
	    errors.reject("page.invalid.countPerPage");
	}
	if (!DbValidator.isColumnValid(page.getColumn())) {
	    errors.reject("page.invalid.column");
	}
	if (page.isOrderValid()) {
	    errors.reject("page.invalid.order");
	}
	if (page.getPage() <= 0
		|| page.getPage() - 1 > page.getTotalCount()
			/ page.getCountPerPage()) {
	    errors.reject("page.invalid.page");
	}
    }

}
