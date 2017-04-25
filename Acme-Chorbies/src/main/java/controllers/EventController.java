package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import domain.Event;


@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	@Autowired
	private EventService	eventService;

	public EventController() {
		super();
	}
	
	@RequestMapping(value = "/listAvailable", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> event;
		event = eventService.eventOrganisedLessMonthAndSeatsAvailable();
		boolean available= true;
		
		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/list.do");
		result.addObject("event", event);
		result.addObject("available", available);
		

		return result;
	}
	
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		Collection<Event> event;
		event = eventService.findAll();
		//Creo que aquí hay que crear un objeto TableFacade
		
		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/list.do");
		result.addObject("event", event);

		return result;
	}
	
}