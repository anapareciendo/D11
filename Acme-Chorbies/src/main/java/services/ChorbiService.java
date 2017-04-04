package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Chirp;
import domain.Chorbi;
import domain.Coordinates;
import domain.Genre;
import domain.KindRelationship;
import domain.Likes;
import forms.ChorbiForm;

@Service
@Transactional
public class ChorbiService {

	//Managed repository
	@Autowired
	private ChorbiRepository	chorbiRepository;


	//Validator
	@Autowired
	private Validator validator;
	
	//Supporting services
	@Autowired
	private UserAccountService userAccountService;

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
		res.setBanned(false);
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
		
//		Assert.notNull(chorbi.getKindRelationship(), "The chorbi to save cannot have 'kindRelationship' null.");
//		Assert.notNull(chorbi.getBirthDate(), "The chorbi to save cannot have 'BirthDate' null.");
//		Assert.notNull(chorbi.getGenre(), "The chorbi to save cannot have 'Genre' null.");
//		Assert.notNull(chorbi.getBirthDate(), "The chorbi to save cannot have 'BirthDate' null.");
//		Assert.notNull(chorbi.getCoordinates(), "The chorbi to save cannot have 'BirthDate' null.");
		
		final Chorbi res = this.chorbiRepository.save(chorbi);
//		chorbiRepository.flush();
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

	public Chorbi reconstruct(ChorbiForm actor, BindingResult binding) {
		Chorbi result;
		List<String> cond = Arrays.asList(actor.getConditions());
		if(!actor.getPassword1().isEmpty() && !actor.getPassword2().isEmpty() && actor.getPassword1().equals(actor.getPassword2()) && cond.contains("acepto")){
			UserAccount ua = userAccountService.create();
			
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String hash = encoder.encodePassword(actor.getPassword1(), null);
			
			ua.setUsername(actor.getUsername());
			ua.setPassword(hash);
			
			Authority a = new Authority();
			a.setAuthority(Authority.CHORBI);
			ua.getAuthorities().add(a);
			
			Coordinates coor= new Coordinates(actor.getCountry(), actor.getCity(), actor.getState(), actor.getProvince());
			
			result=this.create(ua);
			
			result.setName(actor.getName());
			result.setSurname(actor.getSurname());
			result.setEmail(actor.getEmail());
			result.setPhone(actor.getPhone());
			result.setPicture(actor.getPicture());
			result.setBirthDate(actor.getBirthDate());
			
			switch(actor.getGenre()){
			case 0: result.setGenre(Genre.WOMEN);
			break;
			case 1: result.setGenre(Genre.MAN);
			break;
			case 2: result.setGenre(Genre.OTHER);
			break;
			}
			switch(actor.getKindRelationship()){
			case 0: result.setKindRelationship(KindRelationship.ACTIVITIES);
			break;
			case 1: result.setKindRelationship(KindRelationship.FRIENDSHIP);
			break;
			case 2: result.setKindRelationship(KindRelationship.LOVE);
			break;
			}
			result.setCoordinates(coor);
			
			validator.validate(result, binding);
		}else{
			result=new Chorbi();
			result.setName("Pass");
			if(!cond.contains("acepto")){
				result.setName("Cond");
			}
		}
		return result;
	} 
	
	public Collection<Chorbi> findMyLikesId(final int likedId) {
		Assert.notNull(likedId);
		return this.chorbiRepository.findMyLikes(likedId);
	} 

}