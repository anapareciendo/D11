package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EventRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;
import domain.Event;
import domain.Manager;

@Service
@Transactional
public class EventService {

	//Managed repository
	@Autowired
	private EventRepository	eventRepository;


	//Validator
	@Autowired
	private Validator validator;

	//Supporting services
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ChorbiService chorbiService;

	//Constructors
	public EventService() {
		super();
	}

	//Simple CRUD methods
	public Event create(final Manager manager) {
		Assert.notNull(manager, "The manager cannot be null.");
		Event res;
		res = new Event();
		res.setManager(manager);
		Calendar date =Calendar.getInstance();
		int day=date.get(Calendar.DAY_OF_MONTH);
		int month=date.get(Calendar.MONTH);
		int year=date.get(Calendar.YEAR);
		int hour=date.get(Calendar.HOUR);
		int minutes=date.get(Calendar.MINUTE);
		res.setDay(day);
		res.setMonth(month);
		res.setYear(year);
		res.setHour(hour);
		res.setMinutes(minutes);
		res.setChorbies(new ArrayList<Chorbi>());
		return res;
	}

	public Collection<Event> findAll() {
		final Collection<Event> res = this.eventRepository.findAll();
		return res;
	}

	public Event findOne(final int eventId) {
		final Event res = this.eventRepository.findOne(eventId);
		return res;
	}

	public Event save(final Event event) {
		Assert.notNull(event, "The evenet to save cannot be null.");
		
		Assert.isTrue(event.getManager().getUserAccount().equals(LoginService.getPrincipal()), "You are not the owner of this event");
		
//		Assert.notNull(event.getMoment(), "The evenet to save cannot have 'moment' null.");
//		Assert.notNull(event.getTitle(), "The evenet to save cannot have 'title' null.");
//		Assert.notNull(event.getDescription(), "The event to save cannot have 'description' null.");
		
		final Event res = this.eventRepository.save(event);
		res.getManager().getEvents().add(res);

		Calendar date =Calendar.getInstance();
		int day=date.get(Calendar.DAY_OF_MONTH);
		int month=date.get(Calendar.MONTH);
		int year=date.get(Calendar.YEAR);
		int hour=date.get(Calendar.HOUR);
		int minutes=date.get(Calendar.MINUTE);
		res.setDay(day);
		res.setMonth(month);
		res.setYear(year);
		res.setHour(hour);
		res.setMinutes(minutes);
		
		return res;
	}

	public void delete(final Event event) {
		Assert.notNull(event, "The event to delete cannot be null.");
		Assert.isTrue(this.eventRepository.exists(event.getId()));
		Manager principal = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(principal.getEvents().contains(event),"You are not the owner of this event");
		
		eventRepository.delete(event);
	}
	
	//----------Other Methods------------------------
	
	public Collection<Event> eventOrganisedLessMonth(){
		return this.eventRepository.eventOrganisedLessMonth();
	}
	
	public Collection<Event> eventOrganisedLessMonthAndSeatsAvailable(){
		return this.eventRepository.eventOrganisedLessMonthAndSeatsAvailable();
	}
	
	public Collection<Event> pastEvents(){
		return this.eventRepository.pastEvents();
	}
	
	public Collection<Event> findMyEvents(){
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.MANAGER);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a manager for this action.");
		return this.eventRepository.findMyEvents(ua.getId());
	}
	
	public Collection<Chorbi> findMyAssistants(){
		UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		Authority a = new Authority();
		a.setAuthority(Authority.MANAGER);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a manager for this action.");
		return this.eventRepository.findMyAssistants(ua.getId());
	}

	public Event reconstruct(Event event, BindingResult binding) {
		Event res = null;
		if(event.getId()==0){
			res = this.create(managerService.findByUserAccountId(LoginService.getPrincipal().getId()));
		}else{
			res = eventRepository.findOne(event.getId());
		}
		res.setDescription(event.getDescription());
		res.setPicture(event.getPicture());
		res.setSeatsOffered(event.getSeatsOffered());
		res.setTitle(event.getTitle());
		
		validator.validate(res, binding);
		
		return res;
	}
	
	public void unregister(int eventId) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.CHORBI);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a chorbi for this action");
		
		Event event = eventRepository.findOne(eventId);
		Chorbi chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		chorbi.getEvents().remove(event);
		chorbiService.save(chorbi);
	}

	public void register(int eventId) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.CHORBI);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a chorbi for this action");
		
		Event event = eventRepository.findOne(eventId);
		Chorbi chorbi = chorbiService.findByUserAccountId(LoginService.getPrincipal().getId());
		if(!chorbi.getEvents().contains(event)){
			chorbi.getEvents().add(event);
			chorbiService.save(chorbi);
		}
	}
	

}