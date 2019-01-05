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
    // EJB beans
    private Populate populate;
    private BudgetFunding budgetFunding;
    private Politics politics;
    
    // Data for facelets
    private List<Party> parties = new ArrayList<Party>();
	private List<Politician> politicians = new ArrayList<Politician>();
	private List<Mandate> positions = new ArrayList<Mandate>();
    private String budgetFundingResult;
    private int budgetFundingAmount;
    private String message;
    private Party party;
    
    private ContactInfo address;
    
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
	    	return "populateSuccess";
    	}
    	catch( Exception e)
    	{
    		message = "A problem occured while populating the database... :(";
    		return "populateFailure";
    	}
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
		politicians = politics.getPartyPoliticians(party);
		
		System.out.println( ">>>>>>>>>>>>>>>>>>>>>>>>> " + politicians.get(0).getMandates().size() );
		return "showPartyDetails";
	}

	public List<Politician> getPoliticians()
	{
		return politicians;
	}

	public void setPoliticians(List<Politician> politicians)
	{
		this.politicians = politicians;
	}

	public ContactInfo getAddress()
	{
		return address;
	}

	public void setAddress(ContactInfo address)
	{
		this.address = address;
	}
}