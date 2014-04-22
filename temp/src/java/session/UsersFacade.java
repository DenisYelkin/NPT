/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Rate;
import entities.Users;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Users value) {
        value.setRegistrationDate(new Date(System.currentTimeMillis()));
        super.create(value);
    }

    public UsersFacade() {
        super(Users.class);
    }

    public Users getUserByLogin(String login) {
        List<Users> resultList = em.createNamedQuery("Users.findByLogin")
                .setParameter("login", login).getResultList();
        if (resultList != null && resultList.size() == 1) {
            return resultList.get(0);
        }
        return null;
    }

    public List<Rate> getRatesForUser(Users user) {
        return em.createNamedQuery("Rate.findByUser").setParameter("user", user.getId()).getResultList();
    }

    
}
