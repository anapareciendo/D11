package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;


	//Validator
	/*
	 * @Autowired
	 * private Validator validator;
	 */

	//Supporting services

	//Constructors
	public AdministratorService() {
		super();
	}

	//Simple CRUD methods
	public Administrator create(final UserAccount ua) {
		Administrator res;
		res = new Administrator();
		res.setUserAccount(ua);
		return res;
	}

	public Collection<Administrator> findAll() {
		final Collection<Administrator> res = this.administratorRepository.findAll();
		return res;
	}

	public Administrator findOne(final int adminId) {
		final Administrator res = this.administratorRepository.findOne(adminId);
		return res;
	}

	public Administrator save(final Administrator admin) {
		Assert.notNull(admin, "The administrator to save cannot be null.");
		final Administrator res = this.administratorRepository.save(admin);
		administratorRepository.flush();
		return res;
	}

	public void delete(final Administrator admin) {
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(ua.getAuthorities().contains(a), "You must to be a admin to delete an actor.");

		Assert.notNull(admin, "The adminstrator to delete cannot be null.");
		Assert.isTrue(this.administratorRepository.exists(admin.getId()));

		this.administratorRepository.delete(admin);
	}

	//Utilites methods
	public Administrator findByUserAccountId(final int id) {
		Assert.notNull(id);
		return this.administratorRepository.findByUserAccountId(id);
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