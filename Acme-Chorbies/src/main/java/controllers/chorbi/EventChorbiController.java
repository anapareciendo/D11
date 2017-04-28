package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChorbiService;
import controllers.AbstractController;
import domain.Event;


@Controller
@RequestMapping("/event/chorbi")
public class EventChorbiController extends AbstractController {

	@Autowired
	private ChorbiService chorbiService;

	public EventChorbiController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> event = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId()).getEvents();
		
		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/chorbi/list.do");
		result.addObject("event", event);
		result.addObject("own", true);

		return result;
	}
	
	@RequestMapping(value = "/unregister", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam int eventId) {
		ModelAndView result;
		
		
		result = new ModelAndView("event/list");
		
		
		try{
			chorbiService.register(eventId);
			result.addObject("message", "event.unregister.success");
		}catch(Throwable oops){
			result.addObject("message", "event.commit.error");
		}
		
		Collection<Event> event = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId()).getEvents();
		
		result.addObject("requestURI", "event/chorbi/list.do");
		result.addObject("event", event);
		result.addObject("own", true);
		
		return result;
	}
	
}