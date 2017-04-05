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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SearchTemplateService;
import domain.Chorbi;
import domain.SearchTemplate;
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

	@RequestMapping(value="/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		TemplateForm res = new TemplateForm();
		
		result = new ModelAndView("template/template");
		result.addObject("template", res);
		
		return result;
		
	}
	
	@RequestMapping(value = "/template", method = RequestMethod.POST, params = "search")
	public ModelAndView user(TemplateForm template, BindingResult binding) {
		ModelAndView result;
		SearchTemplate search;
		search = searchTemplateService.reconstruct(template, binding);

		if (!binding.hasErrors()) {
			try{
				searchTemplateService.save(search);
				List<Chorbi> chorbis = new ArrayList<Chorbi>();
				chorbis.addAll(searchTemplateService.searchTemplate(template.getKindRelationship(), template.getGenre(), template.getAproximateAge(), template.getCountry(), template.getCity(), template.getState(), template.getProvince(), template.getKeyword()));
				result = new ModelAndView("chorbi/list");
				result.addObject("chorbi", chorbis);
			}catch(Throwable oops){
				result = new ModelAndView("template/template");
				result.addObject("template", template);
				result.addObject("message", "template.error");
			}
		} else {
			result = new ModelAndView("template/template");
			result.addObject("template", template);
			result.addObject("message", "template.binding");
		}
		return result;
	}
	
}
