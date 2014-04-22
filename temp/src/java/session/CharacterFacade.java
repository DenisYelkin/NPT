/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entities.Actor;
import entities.Character;
import entities.Movie;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import utils.MovieCharacterPair;

/**
 *
 * @author Yelkin
 */
@Stateless
public class CharacterFacade extends AbstractFacade<Character> {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CharacterFacade() {
        super(Character.class);
    }
    
    public List<MovieCharacterPair> getMoviesWithCharactersForActor(BigDecimal actorId) {
        List<Character> characters = em.createNamedQuery("Character.findByActor").setParameter("actor", actorId).getResultList();
        List<MovieCharacterPair> result = new LinkedList<>();
        for (Character character : characters) {
            result.add(new MovieCharacterPair(character.getMovie(), character));
        }        
        return result;
    }
    
    public List<Actor> getActorsForMovie(BigDecimal movieId) {
        List<Character> characters = em.createNamedQuery("Character.findByMovie").setParameter("movie", movieId).getResultList();
        List<Actor> result = new LinkedList<>();
        for (Character character : characters) {
            result.add(character.getActor());
        }
        return result;
    }
    
    public List<Character> getCharactersForMovie(BigDecimal movieId)
    {
         return em.createNamedQuery("Character.findByMovie").setParameter("movie", movieId).getResultList();
    }
    
}