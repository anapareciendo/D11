package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Chorbi;
import domain.Likes;

@Service
@Transactional
public class ChorbiService {

	//Managed repository
	@Autowired
	private ChorbiRepository	chorbiRepository;


	//Validator
	/*
	 * @Autowired
	 * private Validator validator;
	 */

	//Supporting services

	//Constructors
	public ChorbiService() {
		super();
	}

	//Simple CRUD methods
	public Chorbi create(final UserAccount ua) {
		Chorbi res;
		res = new Chorbi();
		res.setSendChirps(new ArrayList<Chirp>());
		res.setMakeLikes(new ArrayList<Likes>());
		res.setReceivedLikes(new ArrayList<Likes>());
		res.setReceivedChirps(new ArrayList<Chirp>());
		res.setUserAccount(ua);
		return res;
	}

	public Collection<Chorbi> findAll() {
		final Collection<Chorbi> res = this.chorbiRepository.findAll();
		return res;
	}

	public Chorbi findOne(final int chorbiId) {
		final Chorbi res = this.chorbiRepository.findOne(chorbiId);
		return res;
	}

	public Chorbi save(final Chorbi chorbi) {
		Assert.notNull(chorbi, "The chorbi to save cannot be null.");
		
		Assert.notNull(chorbi.getPicture(), "The chorbi to save cannot have 'picture' null.");
		Assert.notNull(chorbi.getKindRelationship(), "The chorbi to save cannot have 'kindRelationship' null.");
		Assert.notNull(chorbi.getBirthDate(), "The chorbi to save cannot have 'BirthDate' null.");
		Assert.notNull(chorbi.getGenre(), "The chorbi to save cannot have 'Genre' null.");
		Assert.notNull(chorbi.getBirthDate(), "The chorbi to save cannot have 'BirthDate' null.");
		Assert.notNull(chorbi.getCoordinates(), "The chorbi to save cannot have 'BirthDate' null.");
		
		final Chorbi res = this.chorbiRepository.save(chorbi);
		chorbiRepository.flush();
		return res;
	}

	public void delete(final Chorbi chorbi) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(chorbi, "The chorbi to delete cannot be null.");
		Assert.isTrue(this.chorbiRepository.exists(chorbi.getId()));

		Assert.isNull(chorbi.getSendChirps().isEmpty(), "The chorbi cannot be delete with send chirps");
		Assert.isNull(chorbi.getReceivedLikes().isEmpty(), "The chorbi cannot be delete with receive likes");
		Assert.isNull(chorbi.getReceivedChirps().isEmpty(), "The chorbi cannot be delete with receive chirps");
		Assert.isNull(chorbi.getMakeLikes().isEmpty(), "The chorbi cannot be delete with sender make likes");
		
		this.chorbiRepository.delete(chorbi);
	}

	//Utilites methods
	public Chorbi findByUserAccountId(final int id) {
		Assert.notNull(id);
		return this.chorbiRepository.findByUserAccountId(id);
		} 

}