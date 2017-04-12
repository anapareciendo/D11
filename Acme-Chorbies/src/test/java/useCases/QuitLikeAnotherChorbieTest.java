package useCases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ChorbiService;
import utilities.AbstractTest;
import domain.Chorbi;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QuitLikeAnotherChorbieTest extends AbstractTest{
	
	/* *----Browse the list of chorbies who have registered to the system.-----*
	  -El orden de los parámetros es: Usuario que se va a autenticar, error esperado
	  
	  Cobertura del test:
	  		//El usuario autenticado existe(test positivo)
			//El usuario no está autenticado(test negativo)
				
	 */
	
	@Autowired
	private ChorbiService chorbiService;
	
	@Autowired
	//private LikesService likeService;
	
	private List<Chorbi> chorbies;
	
	@Before
    public void setup() {
		this.chorbies= new ArrayList<Chorbi>();
		this.chorbies.addAll(this.chorbiService.findAll());
		
		Collections.shuffle(this.chorbies);
	}
	
	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				this.chorbies.get(0).getUserAccount().getUsername(), null
			}, {
				"", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	

	protected void template(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			//Chorbi c= chorbiService.findOne(LoginService.getPrincipal().getId());
			//Collection<Chorbi> likes = chorbiService.findMyLikes(c.getId());
			
			
			//Chorbi unlike = ((List<Chorbi>) likes).get(0);
			//likeService.delete(unlike);
			
			
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
