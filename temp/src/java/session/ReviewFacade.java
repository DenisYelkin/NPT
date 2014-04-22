/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Review;
import entities.Users;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@Stateless
public class ReviewFacade extends AbstractFacade<Review> {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Review value) {
        value.setReviewDate(new Date(System.currentTimeMillis()));
        super.create(value);
    }
    
    @Override
    public void edit(Review review) {
        review.setReviewDate(new Date(System.currentTimeMillis()));
        super.edit(review);
    }

    public ReviewFacade() {
        super(Review.class);
    }
}
