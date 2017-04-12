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
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import services.ConfigService;
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
	@Autowired
	private ChorbiService chorbiService;
	@Autowired
	private ConfigService configService;

	// Constructors -----------------------------------------------------------

	public SearchTemplateController() {
		super();
	}

	@RequestMapping(value="/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		TemplateForm res;
		SearchTemplate chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId()).getSearchTemplate();
		if(chorbi==null){
			res = new TemplateForm();
		}else{
			int aux = chorbi.getGenre().getValue();
			System.out.println(aux);
			res = new TemplateForm(chorbi.getKindRelationship().getValue(), chorbi.getAproximateAge(), 
					chorbi.getGenre().getValue(), chorbi.getKeyword(), chorbi.getCoordinates().getCountry(), 
					chorbi.getCoordinates().getCity(), chorbi.getCoordinates().getState(), 
					chorbi.getCoordinates().getProvince());
		}
		
		result = new ModelAndView("template/template");
		result.addObject("template", res);
		
		return result;
		
	}
	
	@RequestMapping(value = "/template", method = RequestMethod.POST, params = "search")
	public ModelAndView user(TemplateForm template, BindingResult binding) {
		ModelAndView result;
		SearchTemplate search;
		try{
			search = searchTemplateService.reconstruct(template, binding);
			if (!binding.hasErrors()) {
				try{
					List<Chorbi> chorbis = new ArrayList<Chorbi>();
					chorbis.addAll(searchTemplateService.searchTemplate(search.getKindRelationship(), search.getGenre(), template.getAproximateAge(), template.getCountry(), template.getCity(), template.getState(), template.getProvince(), template.getKeyword()));
					
					search.getResults().clear();
					search.getResults().addAll(chorbis);
					
					searchTemplateService.save(search);
					
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
		}catch(Throwable opps){
			result = new ModelAndView("template/template");
			result.addObject("template", template);
			result.addObject("message", "template.binding");
		}

		
		return result;
	}
	
	@RequestMapping(value="/result", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		TemplateForm res;
		Chorbi chorbi =chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		SearchTemplate search = chorbi.getSearchTemplate();
		List<Chorbi> chorbis = new ArrayList<Chorbi>();
		
		if(search==null){
			res = new TemplateForm();
			result = new ModelAndView("template/template");
			result.addObject("template", res);
			result.addObject("message", "template.void");
		}else{ 
			long st = search.getMoment().getTime(); //ms del SearchTemplate
			long now = Calendar.getInstance().getTime().getTime(); //ms Curren Date
			long finale = now-st; //ms desde que se uso el SearchTemplate
			long cache = configService.find().getCache()*3600000l; //3600000ms->1h
			
			if(finale>cache){
				int aux = chorbi.getGenre().getValue();
				System.out.println(aux);
				res = new TemplateForm(search.getKindRelationship().getValue(), search.getAproximateAge(), 
					search.getGenre().getValue(), search.getKeyword(), search.getCoordinates().getCountry(), 
					search.getCoordinates().getCity(), search.getCoordinates().getState(), 
					search.getCoordinates().getProvince());
				result = new ModelAndView("template/template");
				result.addObject("template", res);
				result.addObject("message", "template.expired");
			}else{
				chorbis.addAll(search.getResults());
				result = new ModelAndView("chorbi/list");
				result.addObject("chorbi", chorbis);
			}
		}

		return result;
	}
	
}
