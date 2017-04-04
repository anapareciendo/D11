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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import domain.Chorbi;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ChorbiService chorbiService;

	// Constructors -----------------------------------------------------------

	public ChorbiController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chorbi> chorbis = chorbiService.findAll();
		
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbi", chorbis);

		return result;
	}

}
