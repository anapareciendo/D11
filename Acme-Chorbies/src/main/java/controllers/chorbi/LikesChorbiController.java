/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.LikesService;
import controllers.AbstractController;
import domain.Chorbi;

@Controller
@RequestMapping("/likes/chorbi")
public class LikesChorbiController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ChorbiService chorbiService;
	@Autowired
	private LikesService likesService;

	// Constructors -----------------------------------------------------------

	public LikesChorbiController() {
		super();
	}

	
	@RequestMapping(value="/listMyLikes", method = RequestMethod.GET)
	public ModelAndView listMyLikes(@RequestParam int chorbiId) {
		ModelAndView result;
		Collection<Chorbi> chorbis = chorbiService.findMyLikesId(chorbiId);
		
		result = new ModelAndView("chorbi/list");
		result.addObject("chorbi", chorbis);

		return result;
	}
	
}
