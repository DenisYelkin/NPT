/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Actor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@Stateless
public class ActorFacade extends AbstractFacade<Actor> {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActorFacade() {
        super(Actor.class);
    }
    
    public List<Actor> getActors(String substr) {
        return em.createNamedQuery("Actor.findBySubstring").setParameter("q", "%" + substr + "%").getResultList();
    }
}
