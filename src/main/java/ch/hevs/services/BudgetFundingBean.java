package ch.hevs.services;

import java.util.List;

import javax.annotation.Resource;
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

@Stateless
public class BudgetFundingBean implements BudgetFunding {
	
	
	@PersistenceContext(name = "politicsPU", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@Resource 
	private SessionContext ctx;

	@Override
	@TransactionAttribute(value=TransactionAttributeType.SUPPORTS)
	public boolean canWithdrawFromBudget(int amount)
	{
		boolean canWithdraw = (ctx.isCallerInRole("accountant") || (ctx.isCallerInRole("secretary") && amount < 50));
		
		return canWithdraw;
	}
	
	@Override
	@TransactionAttribute(value=TransactionAttributeType.SUPPORTS)
	public boolean canAccessBudgetForm()
	{
		boolean canAccessForm = (ctx.isCallerInRole("accountant") || ctx.isCallerInRole("secretary"));
		
		return canAccessForm;
	}
	
	// � tester pour voir si erreur ou persistence ?
	@TransactionAttribute(value=TransactionAttributeType.NEVER)
	public void asdf()
	{
		Party p = new Party();
		em.persist(p);
	}
	

	@Override
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public void withdrawFromBudget(Party party, int amount) throws Exception
	{
		if ( amount <= 0 )
			throw new Exception("Amount was inferior or equal to 0. Positive value required!");
		
		party = em.merge(party);
		
		int remainingBudget = party.getRemainingBudget();
		party.setRemainingBudget( remainingBudget - amount );
		
		if ( party.getRemainingBudget() < 0 )
			throw new Exception("Not enough money to withdraw! Wanted " + amount + " but only " + remainingBudget + " remaining.");
			// Automatic rollback happens here!
	}

	@Override
	public boolean canSeeBudget()
	{
		boolean canSeeBudget = ctx.isCallerInRole("accountant");
		
		return canSeeBudget;
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
