package ch.hevs.services;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Party;

@Local
public interface BudgetFunding
{
	boolean canSeeBudget();
	boolean canWithdrawFromBudget(int amount);
	void withdrawFromBudget(Party party, int amount) throws Exception;
}
