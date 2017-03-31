package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Config extends DomainEntity{

	//----------------------Attributes-------------------------
	private int cache;

	public int getCache() {
		return cache;
	}

	public void setCache(int cache) {
		this.cache = cache;
	}
	
	
}
