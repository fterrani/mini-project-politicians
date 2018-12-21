package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.businessobject.Party;
import ch.hevs.businessobject.Politician;
import ch.hevs.businessobject.Mandate;
import ch.hevs.services.BudgetFunding;
import ch.hevs.services.Politics;
import ch.hevs.services.Populate;

public class PoliticsManagedBean
{
	private List<Party> parties = new ArrayList<Party>();
	private List<Politician> politicians;
	private List<Mandate> positions;
	
	
	
    private String budgetFundingResult;
    private int budgetFundingAmount;
    
    // EJBs
    private Populate populate;
    private BudgetFunding budgetFunding;
    private Politics politics;
    
    @PostConstruct
    public void initialize() throws NamingException
    {
    	// use JNDI to inject reference to bank EJB
    	InitialContext ctx = new InitialContext();
    	populate = (Populate) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/PopulateBean!ch.hevs.services.Populate");
    	budgetFunding = (BudgetFunding) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/BudgetFundingBean!ch.hevs.services.BudgetFunding");
    	politics = (Politics) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/PoliticsBean!ch.hevs.services.Politics");
		
    	
    	parties = politics.getParties();
    }
    
    public List<Party> getParties()
    {
    	return parties;
    }
    
    public void populateDB()
    {
    	populate.populate();
    }
    
    /*public String performTransfer() {
    	
    	try {
			if (sourceClientName.equals(destinationClientName) && sourceAccountDescription.equals(destinationAccountDescription)) {
				
				this.transactionResult="Error: accounts are identical!";
			} 
			else {
				
				Account compteSrc = bank.getAccount(sourceAccountDescription, sourceClientName);
				Account compteDest = bank.getAccount(destinationAccountDescription, destinationClientName);
	
				// Transfer
				bank.transfer(compteSrc, compteDest, transactionAmount);
				this.transactionResult="Success!";
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

		return "showTransferResult"; //  the String value returned represents the outcome used by the navigation handler to determine what page to display next.
	} */
}