package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Chorbi;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);
	
	//---LEVEL C---
	
		//A listing with the number of chorbies per city
		@Query("select count(c) from Chorbi c group by c.coordinates.city")
		Collection<Integer> numChorbiesPerCity();
		
		//A listing with the number of chorbies per country
		@Query("select count(c) from Chorbi c group by c.coordinates.country")
		Collection<Integer> numChorbiesPerCountry();
		
		//The minimum ages of the chorbies
		@Query("select max(c.age) from Chorbi c")
		Integer minAgeChorbies();
		
		//The maximum ages of the chorbies
		@Query("select min(c.age) from Chorbi c")
		Integer maxAgeChorbies();
		
		//The average ages of the chorbies
		@Query("select avg(c.age) from Chorbi c")
		Double avgAgeChorbies();
		
//		The ratio of chorbies who have not registered a credit card or have registered an invalid credit card
		@Query("select count(c) from Chorbi c where c.creditCard is null")
		Integer chorbisWithCreditCard();
		
		@Query("select count(c) from Chorbi c where c.creditCard is not null")
		Integer chorbisWithoutCreditCard();
		
//		The ratios of chorbies who search for activities, friendship, and love.
		@Query("select count(c) from Chorbi c where c.searchTemplate is not null and c.searchTemplate.kindRelationship=1 and c.banned=false")
		Integer chorbisWhoSearchActivities();
		@Query("select count(c) from Chorbi c where c.searchTemplate is not null and c.searchTemplate.kindRelationship=2 and c.banned=false")
		Integer chorbisWhoSearchFriendship();
		@Query("select count(c) from Chorbi c where c.searchTemplate is not null and c.searchTemplate.kindRelationship=3 and c.banned=false")
		Integer chorbisWhoSearchLove();
		@Query("select count(c) from Chorbi c where c.banned=false")
		Integer chorbisNotBannedRatio();
		
		//---LEVEL B---
		
		//List of chorbies, sorted by the number of likes the have got
		@Query("select c from Chorbi c order by c.receivedLikes.size DESC")
		Collection<Chorbi> chorbiesSortedByLikes();
		
		//The minimum number of likes per chorbi
		@Query("select min(c.receivedLikes.size) from Chorbi c")
		Integer minLikesPerChorbi();
		
		//The maximum number of likes per chorbi
		@Query("select max(c.receivedLikes.size) from Chorbi c")
		Integer maxLikesPerChorbi();
		
		//The avg number of likes per chorbi
		@Query("select avg(c.receivedLikes.size) from Chorbi c")
		Double avgLikesPerChorbi();
		
		//---LEVEL A---
		
		//The minimum number of chirps that a chorbi receives from other chorbies
		@Query("select min(c.receivedChirps.size) from Chorbi c")
		Integer minChirpsReceived();
		
		//The maximum number of chirps that a chorbi receives from other chorbies
		@Query("select max(c.receivedChirps.size) from Chorbi c")
		Integer maxChirpsReceived();
		
		//The avg number of chirps that a chorbi receives from other chorbies
		@Query("select avg(c.receivedChirps.size) from Chorbi c")
		Double avgChirpsReceived();
		
		//The minimum number of chirps that a chorbi send to other chorbies
		@Query("select min(c.sendChirps.size) from Chorbi c")
		Integer minChirpsSend();
		
		//The maximum number of chirps that a chorbi send to other chorbies
		@Query("select max(c.sendChirps.size) from Chorbi c")
		Integer maxChirpsSend();
		
		//The avg number of chirps that a chorbi send to other chorbies
		@Query("select avg(c.sendChirps.size) from Chorbi c")
		Double avgChirpsSend();
		
		//The chorbies who have got more chirps
		@Query("select c from Chorbi c where c.receivedChirps.size > (select avg(c.receivedChirps.size) from Chorbi c)")
		Collection<Chorbi> chorbiesMoreChirpsReceived();
		
		//The chorbies who have sent more chirps
		@Query("select c from Chorbi c where c.sendChirps.size > (select avg(c.sendChirps.size) from Chorbi c)")
		Collection<Chorbi> chorbiesMoreChirpsSent();
	
}