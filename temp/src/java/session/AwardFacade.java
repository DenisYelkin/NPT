/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Award;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@Stateless
public class AwardFacade extends AbstractFacade<Award> {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AwardFacade() {
        super(Award.class);
    }
    
    public List<Award> getAwards(String substr) {
        return em.createNamedQuery("Award.findBySubstring").setParameter("q", "%" + substr + "%").getResultList();
    }
}
