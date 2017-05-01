package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BroadcastRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Broadcast;
import domain.Manager;

@Service
@Transactional
public class BroadcastService {

	//Managed repository
	@Autowired
	private BroadcastRepository	broadcastRepository;


	//Validator
//	@Autowired
//	private Validator validator;

	//Supporting services
	@Autowired
	private ManagerService managerService;

	//Constructors
	public BroadcastService() {
		super();
	}

	//Simple CRUD methods
	public Broadcast create() {
		Broadcast res;
		res = new Broadcast();
		return res;
	}

	public Collection<Broadcast> findAll() {
		final Collection<Broadcast> res = this.broadcastRepository.findAll();
		return res;
	}

	public Broadcast findOne(final int broadcastId) {
		final Broadcast res = this.broadcastRepository.findOne(broadcastId);
		return res;
	}

	public Broadcast save(final Broadcast broadcast) {
		Assert.notNull(broadcast, "The broadcast to save cannot be null.");
		
		final Broadcast res = this.broadcastRepository.save(broadcast);
		
		return res;
	}

	public void delete(final Broadcast broadcast) {
		Assert.notNull(broadcast, "The broadcast to delete cannot be null.");
		Assert.isTrue(this.broadcastRepository.exists(broadcast.getId()));
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.MANAGER);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a manager for this action.");
		
		broadcastRepository.delete(broadcast);
	}
	//----------Other Methods------------------------

	public void reNew(Broadcast res) {
		Manager manager = managerService.findByUserAccountId(LoginService.getPrincipal().getId());
		Broadcast bm = manager.getBroadcast();
		if(bm!=null){
			this.delete(bm);
		}
		manager.setBroadcast(res);
		managerService.save(manager);
	}

}