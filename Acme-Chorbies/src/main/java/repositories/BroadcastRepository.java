package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Broadcast;

@Repository
public interface BroadcastRepository extends JpaRepository<Broadcast, Integer> {
	
}