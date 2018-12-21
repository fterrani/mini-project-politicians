package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Party {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	
	@Column(unique=true)
	private String name;
	
	private int totalPoliticians;
	private int remainingBudget;
	
	@Embedded
	private ContactInfo contactInfo;
	
	@OneToMany(mappedBy="party", cascade=CascadeType.REMOVE)
	private List<Politician> politicians = new ArrayList<>();
	
	
	public Party()
	{
		
	}
	
	public Party(String name, int totalPoliticians, int remainingBudget)
	{
		this.name = name;
		this.totalPoliticians = totalPoliticians;
		this.remainingBudget = remainingBudget;
		this.contactInfo = new ContactInfo();
	}
	
	public Party(String name, int totalPoliticians, int remainingBudget, ContactInfo contactInfo)
	{
		this.name = name;
		this.totalPoliticians = totalPoliticians;
		this.remainingBudget = remainingBudget;
		this.contactInfo = contactInfo;
	}

	public long getId()
	{
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalPoliticians() {
		return totalPoliticians;
	}

	public void setTotalPoliticians(int totalPoliticians) {
		this.totalPoliticians = totalPoliticians;
	}

	public int getRemainingBudget() {
		return remainingBudget;
	}

	public void setRemainingBudget(int remainingBudget) {
		this.remainingBudget = remainingBudget;
	}
	
	public ContactInfo getContactInfo()
	{
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo)
	{
		this.contactInfo = contactInfo;
	}

	public List<Politician> getPoliticians()
	{
		return politicians;
	}
	
	// ATTENTION >>> Should only be used by the system! Otherwise data could be corrupted.
	public void setPoliticians( List<Politician> politicians )
	{
		this.politicians = politicians;
	}
	
	public void addPolitician(Politician p)
	{
		// If the politician isn't already in the party...
		if ( !politicians.contains(p) )
		{
			// ...we add it
			politicians.add( p );
		}
		
		// If the politician still has his/her former party...
		if ( p.getParty() != this )
		{
			// ...we set his/her party to the new one
			p.setParty( this );
		}
	}

	public void removePolitician(Politician p)
	{
		// We remove the politician from the party
		politicians.remove( p );
		
		// Is the politician still has a party...
		if (p.getParty() != null )
		{
			// ...we set the politician's party to null
			p.setParty( null );
		}
	}
}
