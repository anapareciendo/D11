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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
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

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chorbi> chorbis = chorbiService.findNotBanned();
		
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbi", chorbis);

		return result;
	}
	
	@RequestMapping(value="/listMyLikes", method = RequestMethod.GET)
	public ModelAndView listMyLikes(@RequestParam int chorbiId) {
		ModelAndView result;
		Collection<Chorbi> chorbis = chorbiService.findMyLikesId(chorbiId);
		
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbi", chorbis);

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Chorbi chorbi;
		try{
		Integer chorbiId= LoginService.getPrincipal().getId();
		chorbi = chorbiService.findByUserAccountId(chorbiId);
		result = new ModelAndView("chorbi/display");
		result.addObject("chorbi", chorbi);
		}catch(Throwable oops){
			
			result= new ModelAndView("hacker/hackers");

		}
		return result;
	}

}
