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

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import forms.ChorbiForm;

@Controller
@RequestMapping("/security")
public class SigninController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public SigninController() {
		super();
	}

	@RequestMapping(value="/signinLessor", method = RequestMethod.GET)
	public ModelAndView signinUser(){
		ModelAndView result;
		ChorbiForm chorbi = new ChorbiForm();
		
		List<Integer> enums = new ArrayList<Integer>();
		enums.add(0);
		enums.add(1);
		
		result = new ModelAndView("security/signin");
		result.addObject("chorbi2", chorbi);
		result.addObject("genre", enums);
		enums.add(2);
		result.addObject("king", enums);
		
		return result;
	}

}
