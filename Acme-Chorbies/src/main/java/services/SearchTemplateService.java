package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SearchTemplateRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;
import domain.Coordinates;
import domain.Genre;
import domain.KindRelationship;
import domain.SearchTemplate;
import forms.TemplateForm;

@Service
@Transactional
public class SearchTemplateService {

	//Managed repository
	@Autowired
	private SearchTemplateRepository	searchTemplateRepository;


	//Validator
	@Autowired
	private Validator validator;

	//Supporting services

	//Constructors
	public SearchTemplateService() {
		super();
	}

	//Simple CRUD methods
	public SearchTemplate create() {
		SearchTemplate res;
		res = new SearchTemplate();
		res.setResults(new ArrayList<Chorbi>());
		return res;
	}

	public Collection<SearchTemplate> findAll() {
		final Collection<SearchTemplate> res = this.searchTemplateRepository.findAll();
		return res;
	}

	public SearchTemplate findOne(final int searchTemplateId) {
		final SearchTemplate res = this.searchTemplateRepository.findOne(searchTemplateId);
		return res;
	}

	public SearchTemplate save(final SearchTemplate search) {
		Assert.notNull(search, "The template to save cannot be null.");
		final SearchTemplate res = this.searchTemplateRepository.save(search);
//		searchTemplateRepository.flush();
		return res;
	}

	public void delete(final SearchTemplate search) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");
		
		Assert.notNull(search, "The search to delete cannot be null.");
		Assert.isTrue(this.searchTemplateRepository.exists(search.getId()));

		this.searchTemplateRepository.delete(search);
	}

	
	//-----------Other Methods----------------
	public SearchTemplate reconstruct(TemplateForm template, BindingResult binding) {
		SearchTemplate res = this.create();
		Coordinates coor = new Coordinates(template.getCountry(), template.getCity(), template.getState(), template.getProvince());
		
		res.setAproximateAge(template.getAproximateAge());
		res.setCoordinates(coor);
		switch(template.getGenre()){
		case 0: res.setGenre(Genre.WOMEN);
		break;
		case 1: res.setGenre(Genre.MAN);
		break;
		case 2: res.setGenre(Genre.OTHER);
		break;
		}
		res.setKeyword(template.getKeyword());
		switch(template.getKindRelationship()){
		case 0: res.setKindRelationship(KindRelationship.ACTIVITIES);
		break;
		case 1: res.setKindRelationship(KindRelationship.FRIENDSHIP);
		break;
		case 2: res.setKindRelationship(KindRelationship.LOVE);
		break;
		}
		
		validator.validate(res, binding);
		
		return res;
	}
	
	public Collection<Chorbi> searchTemplate(int kind, int genre, int age, String country, String city, 
			String state, String province, String keyword){
		List<Chorbi> res = new ArrayList<Chorbi>();
		res.addAll(searchTemplateRepository.searchTemplate(kind, genre));
		res.retainAll(searchTemplateRepository.searchTemplate(country, city, state, province));
		
		return res;
	}

	//Para el dashboard
	/* private void isAdministrator(){
	  UserAccount ua = LoginService.getPrincipal();
	  Assert.notNull(ua);
	  Authority a = new Authority();
	  a.setAuthority(Authority.ADMIN);
	  Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a administrator for this action.");
	 }
	 */
}