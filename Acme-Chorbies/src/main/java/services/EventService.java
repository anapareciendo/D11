package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EventRepository;
import security.LoginService;
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
//	@Autowired
//	private Validator validator;

	//Supporting services
	@Autowired
	private ManagerService managerService;

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
		res.setMoment(Calendar.getInstance().getTime());
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
		
		Assert.isTrue(event.getManager().getUserAccount().equals(LoginService.getPrincipal()), "You are not the owner of this chirp");
		
		Assert.notNull(event.getMoment(), "The evenet to save cannot have 'moment' null.");
		Assert.notNull(event.getTitle(), "The evenet to save cannot have 'title' blank.");
		Assert.notNull(event.getDescription(), "The event to save cannot have 'description' blank.");
		
		final Event res = this.eventRepository.save(event);
		res.getManager().getEvents().add(res);
		res.setMoment(Calendar.getInstance().getTime());
		
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
	

}