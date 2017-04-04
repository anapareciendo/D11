package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Chorbi;

@Service
@Transactional
public class ChirpService {

	//Managed repository
	@Autowired
	private ChirpRepository	chirpRepository;


	//Validator
	/*
	 * @Autowired
	 * private Validator validator;
	 */

	//Supporting services

	//Constructors
	public ChirpService() {
		super();
	}

	//Simple CRUD methods
	public Chirp create(final Chorbi sender, final Chorbi recipient) {
		Chirp res;
		res = new Chirp();
		res.setSender(sender);
		res.setRecipient(recipient);
		res.setMoment(Calendar.getInstance().getTime());
		return res;
	}

	public Collection<Chirp> findAll() {
		final Collection<Chirp> res = this.chirpRepository.findAll();
		return res;
	}

	public Chirp findOne(final int chirpId) {
		final Chirp res = this.chirpRepository.findOne(chirpId);
		return res;
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp, "The chirp to save cannot be null.");
		
		Assert.notNull(chirp.getMoment(), "The like to save cannot have 'moment' null.");
		Assert.notNull(chirp.getSubject(), "The like to save cannot have 'subject' null.");
		Assert.notNull(chirp.getText(), "The like to save cannot have 'text' null.");
		
		final Chirp res = this.chirpRepository.save(chirp);
		res.getRecipient().getReceivedChirps().add(res);
		res.getSender().getSendChirps().add(res);
		res.setMoment(Calendar.getInstance().getTime());
		
		return res;
	}

	public void delete(final Chirp chirp) {
		Assert.notNull(chirp, "The chirp to delete cannot be null.");
		Assert.isTrue(this.chirpRepository.exists(chirp.getId()));
		

		final UserAccount ua = LoginService.getPrincipal();
		Assert.isTrue(chirp.getSender().getUserAccount().equals(ua),"You are not the owner of the message");
	}



}