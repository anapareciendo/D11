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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import services.LikesService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.Likes;

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

	
	@RequestMapping(value="/like", method = RequestMethod.GET)
	public ModelAndView listMyLikes(@RequestParam int chorbiId) {
		ModelAndView result;
		
		Chorbi liked = chorbiService.findOne(chorbiId);
		Chorbi liker = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		
		Likes res = likesService.create(liker, liked);
		
		result = new ModelAndView("likes/like");
		result.addObject("likes", res);

		return result;
	}
	
		@RequestMapping(value="/saylike", method = RequestMethod.POST)
		public ModelAndView listMyLikes(@RequestParam int chorbiId) {
			ModelAndView result;
			
			Chorbi liked = chorbiService.findOne(chorbiId);
			Chorbi liker = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
			
			Likes res = likesService.create(liker, liked);
			
			result = new ModelAndView("likes/like");
			result.addObject("likes", res);

			return result;
		}
	
}
