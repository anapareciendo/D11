package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SearchTemplateRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chorbi;
import domain.SearchTemplate;

@Service
@Transactional
public class SearchTemplateService {

	//Managed repository
	@Autowired
	private SearchTemplateRepository	searchTemplateRepository;


	//Validator
//	@Autowired
//	private Validator validator;

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
		Assert.notNull(search, "The administrator to save cannot be null.");
		final SearchTemplate res = this.searchTemplateRepository.save(search);
		searchTemplateRepository.flush();
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