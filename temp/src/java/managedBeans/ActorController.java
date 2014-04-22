/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Actor;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.ActorFacade;
import session.CharacterFacade;
import utils.MovieCharacterPair;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "actorController")
@SessionScoped
public class ActorController {

    private Actor actor;
    
    @EJB
    private ActorFacade actorFacade;
    @EJB
    private CharacterFacade characterFacade;

    private List<MovieCharacterPair> movieCharacterList;

    public Actor getActor() {
        return actor;
    }

    public void setActorById(BigDecimal id) {
        actor = actorFacade.find(id);
        movieCharacterList = characterFacade.getMoviesWithCharactersForActor(id);
    }

    public List<MovieCharacterPair> getMovieCharacterList() {
        return movieCharacterList;
    }
    
    public String delete()
    {
        actorFacade.remove(actor);
        this.actor = null;
        return "index";
    }
}
