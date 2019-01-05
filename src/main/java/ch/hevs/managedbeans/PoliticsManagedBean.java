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
    private String message;
    
    
    private Party party;
    private Politician politician;
    
    private boolean canAccessBudgetForm;
    private boolean canSeeBudget;
    private int amount;
    
    @PostConstruct
    public void initialize() throws NamingException
    {
    	// use JNDI to inject reference to bank EJB
    	InitialContext ctx = new InitialContext();
    	populate = (Populate) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/PopulateBean!ch.hevs.services.Populate");
    	budgetFunding = (BudgetFunding) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/BudgetFundingBean!ch.hevs.services.BudgetFunding");
    	politics = (Politics) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/PoliticsBean!ch.hevs.services.Politics");
    	
    	setCanAccessBudgetForm(budgetFunding.canAccessBudgetForm());
    	setCanSeeBudget(budgetFunding.canSeeBudget());
    }
    
    public List<Party> getParties()
    {
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
	
	private void resetMessage()
	{
		message = "";
	}

	public Party getParty()
	{
		return party;
	}
	
	public void setParty( Party party )
	{
		this.party = party;
	}

	public Politician getPolitician()
	{
		return politician;
	}
	
	public void setPolitician( Politician politician )
	{
		this.politician = politician;
	}
	
	public String seePartyDetails()
	{
		politicians = politics.getPartyPoliticians(party);
		
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

	public void setAddressStreet(ValueChangeEvent event)
	{
		politician.getContactInfo().setStreet( (String) event.getNewValue() );
	}

	public void setAddressLocality(ValueChangeEvent event)
	{
		politician.getContactInfo().setLocality( (String) event.getNewValue() );
	}

	public void setAddressPostcode(ValueChangeEvent event)
	{
		politician.getContactInfo().setPostcode( (String) event.getNewValue() );
	}
	
	public void setAddressPhone(ValueChangeEvent event)
	{
		politician.getContactInfo().setPhone( (String) event.getNewValue() );
	}
	
	public void setAddressEmail(ValueChangeEvent event)
	{
		politician.getContactInfo().setEmail( (String) event.getNewValue() );
	}
	
	public String saveAddress()
	{
		politics.updateEntity( politician );
		
		return "editAddress";
	}
	
	public String withdrawFromBudget()
	{
		try
		{
			budgetFunding.withdrawFromBudget(party, amount);
			return "withdrawBudgetSuccess";
		}
		catch (Exception e)
		{
			return "withdrawBudgetFailure";
		}
	}

	public boolean getCanSeeBudget()
	{
		return canSeeBudget;
	}

	public void setCanSeeBudget(boolean canSeeBudget)
	{
		this.canSeeBudget = canSeeBudget;
	}

	public boolean getCanAccessBudgetForm()
	{
		return canAccessBudgetForm;
	}

	public void setCanAccessBudgetForm(boolean canAccessBudgetForm)
	{
		this.canAccessBudgetForm = canAccessBudgetForm;
	}
}