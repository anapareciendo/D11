/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import controllers.AbstractController;
import domain.Chorbi;

@Controller
@RequestMapping("/chorbi/admin")
public class ChorbiAdminController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ChorbiService chorbiService;

	// Constructors -----------------------------------------------------------

	public ChorbiAdminController() {
		super();
	}

	@RequestMapping(value="/banned", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int chorbiId) {
		ModelAndView result;
		Chorbi chorbi = chorbiService.findOne(chorbiId);
		Collection<Chorbi> chorbis;
		result = new ModelAndView("chorbi/list");
		try{
			chorbi.setBanned(true);
			chorbiService.save(chorbi);
			chorbis = chorbiService.findNotBanned();
		}catch(Throwable oops){
			chorbis = chorbiService.findNotBanned();
			result.addObject("message", "chorbi.banned.error");
		}
		result.addObject("chorbi", chorbis);
		return result;
	}
	
}