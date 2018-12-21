package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Politician
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	
	private String firstName;
	private String lastName;
	
	@Embedded
	private ContactInfo contactInfo;
	
	@ManyToOne
	private Party party;
	
	@ManyToMany
	private List<Mandate> mandates = new ArrayList<>();

	public Politician()
	{
		
	}
	
	public Politician(Party party, String firstName, String lastName)
	{
		setParty( party );
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Politician(Party party, String firstName, String lastName, ContactInfo contactInfo)
	{
		setParty( party );
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactInfo = contactInfo;
	}

	public long getId()
	{
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}


	public String getFirstName()
	{
		return firstName;
	}


	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}


	public String getLastName()
	{
		return lastName;
	}


	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public ContactInfo getContactInfo()
	{
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo)
	{
		this.contactInfo = contactInfo;
	}

	public Party getParty()
	{
		return party;
	}

	public void setParty(Party newParty)
	{
		if ( this.party != null )
		{
			// We remove the politician from its former party
			this.party.removePolitician( this );
		}
		
		// We assign the new party to the politician
		this.party = newParty;
		
		if ( newParty != null )
		{
			// We get the list of politicians belonging to the new party
			List<Politician> politicians = newParty.getPoliticians();
			
			// If the politician isn't in the list of the party...
			if ( !politicians.contains( this ) )
			{
				// ...we add it
				newParty.addPolitician( this );
			}
		}
	}

	public List<Mandate> getMandates()
	{
		return mandates;
	}
	
	// ATTENTION >>> Should only be used by the system! Otherwise data could be corrupted.
	public void setMandates( List<Mandate> mandates )
	{
		this.mandates = mandates;
	}
	
	public void addMandate( Mandate m )
	{
		// If the politician doesn't have the mandate yet...
		if ( !mandates.contains(m) )
		{
			// ..we add it
			mandates.add( m );
		}
		
		// We then get the list of politicians within the mandate
		List<Politician> politicians = m.getPoliticians();
		
		// If the mandate doesn't have the politician in its list...
		if ( !politicians.contains( this ) )
		{
			// ...we add it
			m.addPolitician( this );
		}
	}
	
	public void removeMandate( Mandate m )
	{
		// We remove the mandate from the politician
		mandates.remove( m );
		
		// We then get the list of politicians within the mandate
		List<Politician> politicians = m.getPoliticians();
		
		// If the mandate has the politician in its list...
		if ( politicians.contains( this ) )
		{
			// We remove the politician from the mandate
			m.removePolitician( this );
		}
	}
}
