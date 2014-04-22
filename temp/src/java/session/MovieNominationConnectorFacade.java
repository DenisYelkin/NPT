/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Movie;
import entities.MovieNominationConnector;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@Stateless
public class MovieNominationConnectorFacade extends AbstractFacade<MovieNominationConnector> {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovieNominationConnectorFacade() {
        super(MovieNominationConnector.class);
    }
    
    public List<MovieNominationConnector> getNominationsByMovie(Movie movie) {
        List<MovieNominationConnector> result = em.createNamedQuery("MovieNominationConnector.findByMovie")
                .setParameter("movie", movie.getId()).getResultList();
        return result;
    }
}
