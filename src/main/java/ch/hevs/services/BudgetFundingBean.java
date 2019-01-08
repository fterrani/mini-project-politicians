package ch.hevs.services;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.ApplicationException;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.RollbackException;

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
		boolean canWithdraw = (ctx.isCallerInRole("accountant") || (ctx.isCallerInRole("secretary") && amount <= 50));
		
		return canWithdraw;
	}
	
	@Override
	@TransactionAttribute(value=TransactionAttributeType.SUPPORTS)
	public boolean canAccessBudgetForm()
	{
		boolean canAccessForm = (ctx.isCallerInRole("accountant") || ctx.isCallerInRole("secretary"));
		
		return canAccessForm;
	}
	

	@Override
	@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
	public Party withdrawFromBudget(Party party, int amount) throws Exception
	{
		if ( amount <= 0 )
			throw new RollbackException("Amount was inferior or equal to 0. Positive value required!");
		
		party = em.merge(party);
		
		int remainingBudget = party.getRemainingBudget();

		party.setRemainingBudget( remainingBudget - amount );
		
		if ( party.getRemainingBudget() < 0 )
			throw new RollbackException("Not enough money to withdraw! Wanted " + amount + " but only " + remainingBudget + " remaining.");
			// Automatic rollback happens here!
		return party;
	}

	@Override
	public boolean canSeeBudget()
	{
		boolean canSeeBudget = ctx.isCallerInRole("accountant");
		
		return canSeeBudget;
	}

}
