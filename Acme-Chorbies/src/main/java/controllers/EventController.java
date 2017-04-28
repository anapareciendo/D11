package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import services.EventService;
import domain.Event;


@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	@Autowired
	private EventService	eventService;
	@Autowired
	private ChorbiService chorbiService;

	public EventController() {
		super();
	}
	
	@RequestMapping(value = "/listAvailable", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> event;
		event = eventService.eventOrganisedLessMonthAndSeatsAvailable();
		
		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/listAvailable.do");
		result.addObject("event", event);
		result.addObject("available", true);
		try{
			if(chorbiService.findByUserAccountId(LoginService.getPrincipal().getId())!=null){
				result.addObject("all", true);
			}
		}catch(Throwable oops){}

		return result;
	}
	
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		Collection<Event> event;
		event = eventService.findAll();
		
		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/listAll.do");
		result.addObject("event", event);

		return result;
	}
	
}