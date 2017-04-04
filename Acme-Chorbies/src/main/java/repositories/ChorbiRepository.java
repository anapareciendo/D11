package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("select a from Chorbi a where a.userAccount.id = ?1")
	Chorbi findByUserAccountId(int id);
	
	@Query("select distinct(l.liker) from Likes l where l.liked.id = ?1")
	Collection<Chorbi> findMyLikes(int likedId);
}