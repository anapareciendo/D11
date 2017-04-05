/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SearchTemplateService;
import forms.TemplateForm;

@Controller
@RequestMapping("/template")
public class SearchTemplateController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private SearchTemplateService searchTemplateService;

	// Constructors -----------------------------------------------------------

	public SearchTemplateController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value="/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		TemplateForm res = new TemplateForm();
		
		result = new ModelAndView("template/search");
		result.addObject("template", res);
		
		return result;
		
	}
	
}
