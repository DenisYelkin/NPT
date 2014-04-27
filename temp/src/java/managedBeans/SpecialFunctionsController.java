/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Actor;
import entities.Award;
import entities.Director;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.ActorFacade;
import session.AwardFacade;
import session.DirectorFacade;
import session.SpecialsFunctionsFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "specialFunctionsController")
@SessionScoped
public class SpecialFunctionsController {

    private Map<String, Actor> actorMap;
    private Map<String, Director> directorMap;
    private Map<String, Award> awardMap;

    @EJB
    private SpecialsFunctionsFacade facade;

    @EJB
    private ActorFacade actorFacade;
    
    @EJB
    private DirectorFacade directorFacade;
    
    @EJB
    private AwardFacade awardFacade;

    private List resultList;
    private Actor actor4;
    private int year4;
    private Actor actor5;
    private Director director5;
    private Award award6;
    private int year7;
    private Actor actor10;
    private int fromYear10;
    private int toYear10;

    public List getResultList() {
        if (resultList != null && resultList.size() > 0 && resultList.get(0) instanceof Object[]) {
            List<String> result = new LinkedList<>();
            for (Iterator it = resultList.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                result.add(obj[0] + "  " + obj[1]);
            }
            if (result.isEmpty()) {
                result.add("Ничего не найдено");
            }
            return result;
        }
        if (resultList != null && resultList.isEmpty()) {
            resultList.add("Ничего не найдено");
        }
        return resultList;
    }

    public Map<String, Actor> getActorMap() {
        actorMap = new HashMap<>();
        List<Actor> actors = actorFacade.findAll();
        for (Actor actor : actors) {
            actorMap.put(actor.getName(), actor);
        }
        return actorMap;
    }

    public Map<String, Director> getDirectorMap() {
        directorMap = new HashMap<>();
        List<Director> directors = directorFacade.findAll();
        for (Director director : directors) {
            directorMap.put(director.getName(), director);
        }
        return directorMap;
    }
    
    public Map<String, Award> getAwardMap() {
        awardMap = new HashMap<>();
        List<Award> awards = awardFacade.findAll();
        for (Award award : awards) {
            awardMap.put(award.getName() + " (" + award.getYear() + ")", award);
        }
        return awardMap;
    }
    
    public Actor getActor4() {
        return actor4;
    }

    public void setActor4(Actor actor4) {
        this.actor4 = actor4;
    }

    public int getYear4() {
        return year4;
    }

    public void setYear4(int year4) {
        this.year4 = year4;
    }

    public Actor getActor5() {
        return actor5;
    }

    public void setActor5(Actor actor5) {
        this.actor5 = actor5;
    }

    public Director getDirector5() {
        return director5;
    }

    public void setDirector5(Director director5) {
        this.director5 = director5;
    }

    public Award getAward6() {
        return award6;
    }

    public void setAward6(Award award6) {
        this.award6 = award6;
    }

    public int getYear7() {
        return year7;
    }

    public void setYear7(int year7) {
        this.year7 = year7;
    }

    public Actor getActor10() {
        return actor10;
    }

    public void setActor10(Actor actor10) {
        this.actor10 = actor10;
    }

    public int getFromYear10() {
        return fromYear10 + 1900;
    }

    public void setFromYear10(int fromYear10) {
        this.fromYear10 = fromYear10 - 1900;
    }

    public int getToYear10() {
        return toYear10 + 1900;
    }

    public void setToYear10(int toYear10) {
        this.toYear10 = toYear10 - 1900;
    }

    public void setResultList(int i) {
        switch (i) {
            case 1:
                resultList = facade.getBestDirectors();
                break;
            case 2:
                resultList = facade.getMoviesCountByGenre();
                break;
            case 3:
                resultList = facade.getBestMovies();
                break;
            case 4:
                resultList = facade.getMovieForActorByYear(actor4.getName(), year4);
                break;
            case 5:
                resultList = facade.getMoviesWithActorAndDirector(director5.getName(), actor5.getName());
                break;
            case 6:
                resultList = facade.getMoviesWithAwardInYear(award6.getYear(), award6.getName());
                break;
            case 7:
                resultList = facade.getCountriesWithoutMoviesInYear(year7);
                break;
            case 8:
                resultList = facade.getMoviesWithSeveralGenres();
                break;
            case 9:
                resultList = facade.getMoviesWithBestReview();
                break;
            case 10:
                resultList = facade.getCharactersByActorForPeriod(actor10.getName(), fromYear10, toYear10);
                break;
            default:
                throw new RuntimeException("Unknown function");
        }
    }
}
