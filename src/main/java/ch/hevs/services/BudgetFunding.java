package ch.hevs.services;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Party;

@Local
public interface BudgetFunding
{
	boolean canSeeBudget();
	boolean canAccessBudgetForm();
	boolean canWithdrawFromBudget(int amount);
	Party withdrawFromBudget(long partyId, int amount) throws Exception;
}
