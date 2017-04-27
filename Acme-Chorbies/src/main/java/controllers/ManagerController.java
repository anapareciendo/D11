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

import security.LoginService;
import services.ManagerService;
import domain.Manager;

@Controller
@RequestMapping("/manager")
public class ManagerController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ManagerService managerService;
//	@Autowired
//	private CreditCardService creditService;

	// Constructors -----------------------------------------------------------

	public ManagerController() {
		super();
	}

	// Actions
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Manager manager = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
		result = new ModelAndView("manager/display");
		result.addObject("manager", manager);
		return result;
	}
	
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView pay() {
		ModelAndView result;
		Manager manager = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
		try{
			manager.setAmount(0);
			Manager res= managerService.save(manager);
			result = new ModelAndView("manager/display");
			result.addObject("manager", res);
			result.addObject("message", "manager.pay.success");
		}catch(Throwable oops){
			result = new ModelAndView("manager/display");
			result.addObject("manager", manager);
			result.addObject("message", "manager.pay.error");
		}
		return result;
	}

}
