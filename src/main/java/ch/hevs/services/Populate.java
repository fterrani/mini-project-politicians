package ch.hevs.services;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Party;
import ch.hevs.businessobject.Politician;

@Local
public interface Populate
{
	void populate();
}
