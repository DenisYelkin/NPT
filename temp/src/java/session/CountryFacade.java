/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Country;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@Stateless
public class CountryFacade extends AbstractFacade<Country> {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CountryFacade() {
        super(Country.class);
    }
    
    public Country getCountryByName(String name)
    {
        List result = em.createNamedQuery("Country.findByName").setParameter("name", name).getResultList();
        if (result != null && !result.isEmpty())
            return (Country) result.get(0);
        return null;
    }
    
    public List<Country> getCountries(String substr) {
        return em.createNamedQuery("Country.findBySubstring").setParameter("q", "%" + substr + "%").getResultList();
    }
}
