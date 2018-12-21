package ch.hevs.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import ch.hevs.businessobject.ContactInfo;
import ch.hevs.businessobject.Mandate;
import ch.hevs.businessobject.Party;
import ch.hevs.businessobject.Politician;

@Stateless
public class PopulateBean implements Populate {
	
	
	@PersistenceContext(name = "politicsPU", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;

	@Override
	public void populate()
	{
		Party udc = new Party("UDC", 215, 34000, new ContactInfo("Chemin des Arbres 88", "3972", "Miège", "032 555 66 88", "info@udc.ch") );
		Party pdc = new Party("PDC", 31, 12000, new ContactInfo("Chemin des Cailloux 5", "1860", "Aigle", "222 123 45 43", "contact@pdc.ch") );
		Party plr = new Party("PLR", 214, 999000, new ContactInfo("Route du Midi 12", "1200", "Genève", "444 656 11 11", "info@plr.ch") );
		Party pbd = new Party("PBD", 17, 9999000, new ContactInfo("Rue des Oiseaux 59", "1981", "Vex", "777 666 54 32", "info@pbd.ch") );
		Party verts = new Party("Verts", 23, 2000, new ContactInfo("Allée des Chênes 2", "1950", "Sion", "878 88 77 88", "contact@verts.ch") );
		Party ps = new Party("PS", 25, 100, new ContactInfo("Avenue des Pommiers 90", "1200", "Genève", "999 999 99 99", "info@ps.ch") );
		Party pirate = new Party("Partie pirate", 4, 1337, new ContactInfo("Rue de France 42", "1000", "Lausanne", "404 403 42 42", "info@pirate.ch") );
		
		Mandate c_national = new Mandate("Conseil National");
		Mandate c_etats = new Mandate("Conseil des États");
		Mandate c_federal = new Mandate("Conseil Fédéral");
		
		Politician freysinger = new Politician(udc, "Oskar", "Freysinger", new ContactInfo("Rue du Rawill 12", "1950", "Sion", "012 345 67 89", "oskar.freysinger@caramail.fr"));
		Politician reynard = new Politician(ps, "Mathias", "Reynard", new ContactInfo("Rue de Pratifori 34", "1950", "Sion", "000 111 22 33", "mathias.reynard@protonmail.ch"));
		Politician couchepin = new Politician(plr, "Pascal", "Couchepin", new ContactInfo("Route de Sion 55", "3960", "Sierre", "987 654 32 10", "pascal.couchepin@groupmutuel.ch"));
		Politician calmyrey = new Politician(ps, "Micheline", "Calmy-Rey", new ContactInfo("Rue du Pont 7", "1000", "Lausanne", "111 2222 33 44", "m.calmyrey@gmail.com"));
		Politician sutter = new Politician(plr, "Karin", "Keller-Sutter", new ContactInfo("Neuhofstrasse 8", "9243", "Jonschwil", "365 125 24 54", "karin.sutter@stgallen.ch"));
		Politician amherd = new Politician(pdc, "Viola", "Amherd", new ContactInfo("Gstipfstrasse 45", "3902", "Glis", "027 896 65 89", "viola.amherd@valais.ch"));
		Politician berset = new Politician(udc, "Alain", "Berset", new ContactInfo("Route du Nitou 1", "1721", "Mesiry-Courtion", "075 245 14 23", "alain.berset@fribourg.ch"));
		Politician parmelin = new Politician(plr, "Guy", "Parmelin", new ContactInfo("Chemin du Marais", "1183", "Bursins", "024 565 45 78", "guy.parmelin@vaud.ch"));
		Politician sommaruga = new Politician(ps, "Simonetta", "Sommaruga", new ContactInfo("Gerbe 498", "3537", "Eggiwil", "026 253 69 78", "simonetta.somarruga@bern.ch"));
		
		c_national.addPolitician( freysinger );
		c_national.addPolitician( reynard );
		c_national.addPolitician( couchepin );
		c_national.addPolitician( berset );

		c_etats.addPolitician( freysinger );
		c_etats.addPolitician( calmyrey );
		c_etats.addPolitician( sommaruga );
		c_etats.addPolitician( berset );

		c_federal.addPolitician( freysinger );
		c_federal.addPolitician( sutter );
		c_federal.addPolitician( amherd );
		c_federal.addPolitician( parmelin );
		

		Party[] parties = new Party[]{ udc, pdc, plr, pbd, verts, ps, pirate };
		for( Party p : parties ) em.persist( p );
		
		Mandate[] mandates = new Mandate[]{ c_national, c_federal, c_etats };
		for( Mandate m : mandates ) em.persist( m );
		
		Politician[] politicians = new Politician[]{ freysinger, reynard, couchepin, calmyrey, sutter, amherd, berset, parmelin, sommaruga };
		for( Politician p : politicians ) em.persist( p );
	}
}
