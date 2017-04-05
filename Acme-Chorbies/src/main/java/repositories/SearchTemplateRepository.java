package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.SearchTemplate;

@Repository
public interface SearchTemplateRepository extends JpaRepository<SearchTemplate, Integer> {

	@Query("select c from Chorbi c where c.kindRelationship=?1 and c.genre=?2") //poner la edad
	Collection<Chorbi> searchTemplate(int kind, int genre);
	
	@Query("select c from Chorbi c where c.coordinates.country like '%'||:country||'%'" +
			"and c.coordinates.city like '%'||:city||'%' and c.coordinates.state like '%'||:state||'%'" +
			"and c.coordinates.province like '%'||:province||'%'")
	Collection<Chorbi> searchTemplate(@Param("country") String country, @Param("city") String city, 
			@Param("state") String state, @Param("province") String province);
	
}