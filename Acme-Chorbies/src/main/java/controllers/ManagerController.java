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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CreditCardService;
import services.ManagerService;
import domain.CreditCard;
import domain.Manager;

@Controller
@RequestMapping("/manager")
public class ManagerController extends AbstractController {
	
	// Services -------------------------------------------------------
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private CreditCardService creditCardService;
	
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
	
	@RequestMapping(value = "/creditCard", method = RequestMethod.GET)
	public ModelAndView creditCard() {
			ModelAndView result;
			
			Manager manager = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
			CreditCard res = manager.getCreditCard();
			if(res==null){
				res=creditCardService.create(manager);
			}
			
			result = new ModelAndView("manager/creditCard");
			result.addObject("card", res);
			return result;
	}
	
	@RequestMapping(value = "/creditCard", method = RequestMethod.POST, params = "save")
	public ModelAndView creditCard(CreditCard card, BindingResult binding) {
		ModelAndView result;
		CreditCard creditCard = creditCardService.reconstruct(card, binding);
		if (!binding.hasErrors()) {
			try{
				CreditCard res = creditCardService.save(creditCard);
				result = new ModelAndView("manager/creditCard");
				result.addObject("card", res);
				result.addObject("message", "manager.card.success");
			}catch(Throwable oops){
				result = new ModelAndView("manager/creditCard");
				result.addObject("card", card);
				result.addObject("message", "manager.card.error");
			}
		} else {
			result = new ModelAndView("manager/creditCard");
			result.addObject("card", card);
			result.addObject("message", "manager.card.error");
		}
		return result;
		
	}
	


}
