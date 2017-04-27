package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.EventService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Event;


@Controller
@RequestMapping("/event/manager")
public class EventManagerController extends AbstractController {

	@Autowired
	private EventService	eventService;
	@Autowired
	private ManagerService managerService;

	public EventManagerController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> event = eventService.findMyEvents();
		
		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/manager/list.do");
		result.addObject("event", event);
		

		return result;
	}
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Event res = eventService.create(managerService.findByUserAccountId(LoginService.getPrincipal().getId()));
		
		result = new ModelAndView("event/create");
		result.addObject("event", res);

		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int eventId) {
		ModelAndView result;
		
		Event res = eventService.findOne(eventId);
		
		result = new ModelAndView("event/edit");
		result.addObject("event", res);

		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView saveEvent(Event event, BindingResult binding) {
		ModelAndView result;
		
		try{
			Event res = eventService.reconstruct(event, binding);
			if(!binding.hasErrors()){
				try{
					eventService.save(res);
					if(event.getId()==0){
						managerService.eventFee();
					}
					result = new ModelAndView("redirect:list.do");
				}catch (Throwable opps){
					result = new ModelAndView("event/edit");
					result.addObject("event", event);
					result.addObject("message","event.commit.error");
				}
			}else{
				result = new ModelAndView("event/edit");
				result.addObject("event", event);
				result.addObject("message","event.commit.incomplete");
			}
		}catch(Throwable oops){
			result = new ModelAndView("event/edit");
			result.addObject("event", event);
			result.addObject("message","event.commit.incomplete");
		}
		
		return result;
	}
	
}