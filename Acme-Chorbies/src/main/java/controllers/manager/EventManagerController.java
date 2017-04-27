package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ChirpService;
import services.EventService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;
import domain.Manager;


@Controller
@RequestMapping("/event/manager")
public class EventManagerController extends AbstractController {

	@Autowired
	private EventService	eventService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ChirpService chirpService;

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
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="delete")
	public ModelAndView delete(Event event) {
		ModelAndView result;
		
		try{
			Event res = eventService.findOne(event.getId());
			eventService.delete(res);
			result = new ModelAndView("redirect:list.do");
		}catch (Throwable opps){
			result = new ModelAndView("event/edit");
			result.addObject("event", event);
			result.addObject("message","event.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value="/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		ModelAndView result;
		Chirp chirp = chirpService.create(new Manager(), new Chorbi());
		
		result = new ModelAndView("chirp/event");
		result.addObject("chirp", chirp);
		result.addObject("mode", "send");

		return result;
	}
	
	@RequestMapping(value="/broadcast", method = RequestMethod.POST, params="send")
	public ModelAndView broadcast(Chirp chirp, BindingResult binding) {
		ModelAndView result;
		
//		try{
			List<Chorbi> cc = new ArrayList<Chorbi>();
			cc.addAll(eventService.findMyAssistants());
			if(!cc.isEmpty()){
				chirp.setRecipient(cc.get(0));
				Chirp res = chirpService.reconstruct(chirp, binding);
				if(!binding.hasErrors()){
					try{
						chirpService.broadcast(res);
						Chirp newChirp = chirpService.create(new Manager(), new Chorbi());
						
						result = new ModelAndView("chirp/event");
						result.addObject("chirp", newChirp);
						result.addObject("mode", "send");
						result.addObject("message", "event.broadcast.success");
					}catch (Throwable opps){
						result = new ModelAndView("chirp/event");
						result.addObject("chirp", chirp);
						result.addObject("mode", "send");
						result.addObject("message","event.commit.error");
					}
				}else{
					result = new ModelAndView("chirp/event");
					result.addObject("chirp", chirp);
					result.addObject("mode", "send");
					result.addObject("message","event.commit.incomplete");
				}
			}else{
				Chirp newChirp = chirpService.create(new Manager(), new Chorbi());
				result = new ModelAndView("chirp/event");
				result.addObject("chirp", newChirp);
				result.addObject("mode", "send");
				result.addObject("message", "event.broadcast.success");
			}
			
//		}catch(Throwable oops){
//			result = new ModelAndView("chirp/event");
//			result.addObject("chirp", chirp);
//			result.addObject("mode", "send");
//			result.addObject("message","event.commit.incomplete");
//		}
		return result;
	}
	
}