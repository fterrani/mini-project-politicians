package ch.hevs.services;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Party;
import ch.hevs.businessobject.Politician;

@Local
public interface Politics
{
	List<Party> getParties();
	List<Politician> getPartyPoliticians(Party party);
	Politician getPoliticianById( long id );
	Party getPartyById( long id );
	void removeParty(Party party);
	
	void updateEntity(Object entity);
	void addEntity(Object entity);
	void removeEntity(Object entity);
}
