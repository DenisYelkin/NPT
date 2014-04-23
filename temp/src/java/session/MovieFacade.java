/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Movie;
import entities.Rate;
import entities.Users;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@Stateless
public class MovieFacade extends AbstractFacade<Movie> {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @EJB
    private RateFacade rateFacade;

    public MovieFacade() {
        super(Movie.class);
    }

    public List<Movie> getMovies(String substr) {
        return em.createNamedQuery("Movie.findBySubstring").setParameter("q", "%" + substr + "%").getResultList();
    }

    @Override
    public void remove(Movie movie) {
        List<Rate> rates = em.createNamedQuery("Rate.findByMovie")
                .setParameter("movie", movie.getId()).getResultList();
        for (Rate rate: rates)
        {
            rateFacade.remove(rate);
        }
        super.remove(movie);
    }
    
    public Rate getMovieRateForUser(Users user, Movie movie) {
        List result = em.createNamedQuery("Rate.findByMovieAndUser").setParameter("movie", movie.getId()).setParameter("user", user.getId()).getResultList();
        if (result == null || result.isEmpty()) {
            return null;
        }
        return (Rate) result.get(0);
    }
}
