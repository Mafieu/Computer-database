package com.excilys.malbert.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.malbert.model.Page;

public class PageValidator implements Validator {

	public boolean supports(Class<?> param) {
		return Page.class.equals(param);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if (obj == null) {
			errors.reject("page.null");
		} else {
			Page page = (Page) obj;
			if (page.getLimit() <= 0) {
				errors.rejectValue("limit", "page.invalid.limit");
			}
			if (!page.isColumnValid()) {
				errors.rejectValue("column", "page.invalid.column");
			}
			if (!page.isOrderValid()) {
				errors.rejectValue("order", "page.invalid.order");
			}
			if (page.getPage() <= 0) {
				errors.rejectValue("page", "page.invalid.page");
			}
		}
	}
}
