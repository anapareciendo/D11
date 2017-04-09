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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import domain.Chorbi;
import forms.ChorbiForm;

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
		Collection<Chorbi> chorbis = chorbiService.findMyLikes(chorbiId);
		
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

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
			ModelAndView result;
			
			Chorbi chorbi= chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			

			result = new ModelAndView("chorbi/edit");
			result.addObject("chorbi", chorbi);
			return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ChorbiForm chor, BindingResult binding) {
		ModelAndView result;
		Chorbi chorbi;
		chorbi= chorbiService.reconstructEdit(chor, binding);

		if (!binding.hasErrors()) {
			try{
				Chorbi edit= new Chorbi();
				
				edit.setName(chorbi.getName());
				edit.setSurname(chorbi.getSurname());
				edit.setEmail(chorbi.getEmail());
				edit.setPhone(chorbi.getPhone());
				edit.setPicture(chorbi.getPicture());
				edit.setKindRelationship(chorbi.getKindRelationship());
				edit.setBirthDate(chorbi.getBirthDate());
				edit.setGenre(chorbi.getGenre());
				
				result = new ModelAndView("chorbi/display");
				result.addObject("chorbi", edit);
			}catch(Throwable oops){
				result = new ModelAndView("chorbi/edit");
				result.addObject("chorbi", chorbi);
				result.addObject("message", "chorbi.error");
			}
		} else {
			result = new ModelAndView("chorbi/edit");
			result.addObject("chorbi", chorbi);
			result.addObject("message", "chorbi.binding");
		}
		return result;
		
	}
	

	protected ModelAndView createEditModelAndView(Chorbi chorbi) {
		ModelAndView result;

		result = createEditModelAndView(chorbi, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Chorbi chorbi, String message) {
		ModelAndView result;
		
		result = new ModelAndView("chorbi/edit");
		result.addObject("chorbi", chorbi);
		result.addObject("message", message);

		return result;
	}
}
