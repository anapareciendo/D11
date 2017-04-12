package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("select a from Chorbi a where a.userAccount.id = ?1")
	Chorbi findByUserAccountId(int id);
	
	@Query("select distinct(l.liker) from Likes l where l.liked.id = ?1 and l.liker.banned = false")
	Collection<Chorbi> findMyLikes(int likedId);
	
	@Query("select c from Chorbi c where c.banned=false")
	Collection<Chorbi> findNotBanned();
	
	//---LEVEL C---
	
	//A listing with the number of chorbies per city
	@Query("select count(c) from Chorbi c group by c.coordinates.city")
	Collection<Integer> numChorbiesPerCity();
	
	//A listing with the number of chorbies per country
	@Query("select count(c) from Chorbi c group by c.coordinates.country")
	Collection<Integer> numChorbiesPerCountry();
	
	//The minimum ages of the chorbies
	@Query("select max(c.birthDate) from Chorbi c")
	Date minAgeChorbies();
	
	//The maximum ages of the chorbies
	@Query("select min(c.birthDate) from Chorbi c")
	Date maxAgeChorbies();
	
	
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