package ch.hevs.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import ch.hevs.businessobject.Party;
import ch.hevs.businessobject.Politician;

@Stateless
public class PoliticsBean implements Politics {
	
	
	@PersistenceContext(name = "politicsPU", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;

	@Override
	public List<Party> getParties()
	{
		Query query = em.createQuery("FROM Party");
		
		return (List<Party>) query.getResultList();
	}

	@Override
	public Politician getPoliticianById(long id)
	{
		Query query = em.createQuery("FROM Politician p WHERE p.id = :id");
		query.setParameter("id", id);
		
		return (Politician) query.getSingleResult();
	}

	@Override
	public Party getPartyById(long id)
	{
		Query query = em.createQuery("FROM Party p WHERE p.id = :id");
		query.setParameter("id", id);
		
		return (Party) query.getSingleResult();
	}

	public void updateEntity(Object entity)
	{
		entity = em.merge( entity );
		em.flush();
	}
	
	public void addEntity(Object entity)
	{
		em.persist( entity );
		em.flush();
	}
	
	public void removeEntity(Object entity)
	{
		em.merge( entity );
		em.remove( entity );
		// implicit em.flush();
	}

	@Override
	public void removeParty(Party party)
	{
		party = em.merge(party);
		em.remove( party );
		// implicit em.flush();
	}

	@Override
	public List<Politician> getPartyPoliticians(Party party)
	{
		party = em.merge( party );
		
		List<Politician> pols = party.getPoliticians();
		
		// Hack to trigger lazy loading of politicians...
		// (would be preferable to use entity graphs if we have time to do so)
		pols.size();
		
		return pols;
	}

}
