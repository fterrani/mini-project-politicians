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
	public void removeParty(Party party)
	{
		party = em.merge(party);
		em.remove( party );
		// implicit em.flush();
	}


	/*public Account getAccount(String accountDescription, String lastnameOwner) {
		Query query = em.createQuery("FROM Account a WHERE a.description=:description AND a.owner.lastname=:lastname");
		query.setParameter("description", accountDescription);
		query.setParameter("lastname", lastnameOwner);
		
		return (Account) query.getSingleResult();
	}
	
	/public List<Account> getAccountListFromClientLastname(String lastname) {
		return (List<Account>) em.createQuery("SELECT c.accounts FROM Client c where c.lastname=:lastname").setParameter("lastname", lastname).getResultList();
	}

	public void transfer(Account srcAccount, Account destAccount, int amount) {
		
		 ORIGINAL CODE
		em.persist(srcAccount);
		em.persist(destAccount);
		
		srcAccount.debit(amount);
		destAccount.credit(amount);
	}
	
	public Client getClient(long clientid) {
		return (Client) em.createQuery("FROM Client c where c.id=:id").setParameter("id", clientid).getSingleResult();
	}
	*/
}
