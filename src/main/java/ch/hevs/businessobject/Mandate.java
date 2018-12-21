package ch.hevs.businessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class Mandate {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	private String name;
	
	@ManyToMany(mappedBy="mandates")
	private List<Politician> politicians = new ArrayList<>();

	public Mandate()
	{
		
	}
	
	public Mandate(String name)
	{
		this.name = name;
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
		// If the mandate's politicians list doesn't contain the politician...
		if ( !politicians.contains(p) )
		{
			// ...we add it
			politicians.add( p );
		}
		
		// We then get the list of mandates of the politician
		List<Mandate> mandates = p.getMandates();
		
		// If the politician doesn't have the mandate...
		if ( ! mandates.contains(this) )
		{
			// ...we add it
			p.addMandate( this );
		}
	}

	public void removePolitician(Politician p)
	{
		// We remove the politician from the mandate
		politicians.remove( p );
		
		// We then get the list of mandates of the politician
		List<Mandate> mandates = p.getMandates();
		
		// If the politician doesn't have the mandate...
		if ( mandates.contains(this) )
		{
			// ...we remove it
			p.removeMandate( this );
		}
	}
}
