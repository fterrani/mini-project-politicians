package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
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

    private boolean canEditAddress = false;
    private boolean canAccessBudgetForm = false;
    private boolean canSeeBudget = false;
    private int amount;
    

    
    @PostConstruct
    public void initialize() throws NamingException
    {
    	// use JNDI to inject reference to bank EJB
    	InitialContext ctx = new InitialContext();
    	populate = (Populate) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/PopulateBean!ch.hevs.services.Populate");
    	budgetFunding = (BudgetFunding) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/BudgetFundingBean!ch.hevs.services.BudgetFunding");
    	politics = (Politics) ctx.lookup("java:global/mini-project-politicians-0.0.1-SNAPSHOT/PoliticsBean!ch.hevs.services.Politics");
    	
    	parties = politics.getParties();
    	
    	setCanAccessBudgetForm(budgetFunding.canAccessBudgetForm());
    	setCanSeeBudget(budgetFunding.canSeeBudget());
    	setCanEditAddress(politics.canEditAddress());
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
    
    public String deleteParty()
    {
    	try
    	{
	    	politics.removeParty(party);
	    	parties = politics.getParties();
	    	
	    	return "deletePartySuccess";
    	}
    	catch( Exception e)
    	{
    		return "deletePartyFailure";
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
			if(budgetFunding.canWithdrawFromBudget(amount))
			{
				party = budgetFunding.withdrawFromBudget(party.getId(), amount);
				//Refresh the party in the list of parties
				for(Party p : parties)
				{
					if(p.getId() == party.getId())
					{
						int i = parties.indexOf(p);
						parties.set(i, party);
					}
				}
				message = amount + " CHF were withdrawn from " + party.getName() + "'s budget.";
			}
			else
			{
				message = "You can only withdraw up to 50 CHF";
			}
			
			return "withdrawBudgetSuccess";
		}
		catch (Exception e)
		{
			message = "There was an error when withdrawing from the party's funds: " + e.getMessage();
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

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public void setAmount(ValueChangeEvent event)
	{
		Object value = event.getNewValue();
		
		try
		{
			amount = Integer.parseInt( value.toString() );
		}
		catch(Exception e)
		{}
	}

	public boolean getCanEditAddress()
	{
		return canEditAddress;
	}

	public void setCanEditAddress(boolean canEditAddress)
	{
		this.canEditAddress = canEditAddress;
	}
}