package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.businessobject.Party;
import ch.hevs.businessobject.Politician;
import ch.hevs.businessobject.ContactInfo;
import ch.hevs.businessobject.Mandate;
import ch.hevs.services.BudgetFunding;
import ch.hevs.services.Politics;
import ch.hevs.services.Populate;


public class PoliticsManagedBean
{
	private List<Party> parties = new ArrayList<Party>();
	private List<Politician> politicians = new ArrayList<Politician>();
	private List<Mandate> positions = new ArrayList<Mandate>();
	
	
	
    private String budgetFundingResult;
    private int budgetFundingAmount;
    
    // EJBs
    private Populate populate;
    private BudgetFunding budgetFunding;
    private Politics politics;
    
    private String message;
    private Party party;
    
    @PostConstruct
    public void initialize() throws NamingException
    {
    	// use JNDI to inject reference to bank EJB
    	InitialContext ctx = new InitialContext();
    	populate = (Populate) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/PopulateBean!ch.hevs.services.Populate");
    	budgetFunding = (BudgetFunding) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/BudgetFundingBean!ch.hevs.services.BudgetFunding");
    	politics = (Politics) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/PoliticsBean!ch.hevs.services.Politics");
    }
    
    public List<Party> getParties()
    {
    	//return parties;
    	//List<Party> asdf = new ArrayList<Party>();
    	//asdf.add( new Party("UDC", 23, 50000, new ContactInfo("udc street", "1234", "udc locality", "012 345 67 89", "info@udc.ch")) );
    	return parties;
    }
    
    public String populateDB()
    {
    	try
    	{
	    	populate.populate();
	    	parties = politics.getParties();
	    	
	    	message = "The database was successfully populated! :)";
    	}
    	catch( Exception e)
    	{
    		message = "A problem occured while populating the database... :(";
    	}
    	
    	return "dbPopulated";
    }

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Party getParty()
	{
		return party;
	}
	
	public void setParty( Party party )
	{
		this.party = party;
	}
	
	public String seePartyDetails()
	{
		party = politics.getPartyById( party.getId() );
		return "showPartyDetails";
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