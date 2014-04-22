/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Director;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@Stateless
public class DirectorFacade extends AbstractFacade<Director> {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DirectorFacade() {
        super(Director.class);
    }
    
    public List<Director> getDirectors(String substr) {
        return em.createNamedQuery("Director.findBySubstring").setParameter("q", "%" + substr + "%").getResultList();
    }
}
