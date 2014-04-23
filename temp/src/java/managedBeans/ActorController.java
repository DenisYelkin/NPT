/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Actor;
import entities.Country;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.ActorFacade;
import session.CharacterFacade;
import session.CountryFacade;
import utils.DateFormatter;
import utils.MovieCharacterPair;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "actorController")
@SessionScoped
public class ActorController {

    private Map<String, Country> countryMap;
    
    private Actor actor;
    
    @EJB
    private ActorFacade actorFacade;
    @EJB
    private CharacterFacade characterFacade;
    @EJB
    private CountryFacade countryFacade;

    private List<MovieCharacterPair> movieCharacterList;

    private boolean nameCorrect = true;
    private boolean photoCorrect = true;
    private boolean birthDateCorrect = true;
    
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
    
    
    
    public String save() {
        if (!nameCorrect || !photoCorrect || !birthDateCorrect) {
            return null;
        }
        actorFacade.edit(actor);
        setActorById(actor.getId());
        return "Actor";
    }
    
    public String cancel() {
        setActorById(actor.getId());
        return "Actor";
    }

    public Map<String, Country> getCountryMap() {
        countryMap = new HashMap<>();
        List<Country> countries = countryFacade.findAll();
        for (Country country : countries) {
            countryMap.put(country.getName(), country);
        }
        return countryMap;
    }

    public boolean isNameCorrect() {
        return nameCorrect;
    }

    public void setNameCorrect(boolean nameCorrect) {
        this.nameCorrect = nameCorrect;
    }

    public boolean isPhotoCorrect() {
        return photoCorrect;
    }

    public void setPhotoCorrect(boolean photoCorrect) {
        this.photoCorrect = photoCorrect;
    }

    public boolean isBirthDateCorrect() {
        return birthDateCorrect;
    }

    public void setBirthDateCorrect(boolean birthDateCorrect) {
        this.birthDateCorrect = birthDateCorrect;
    }
    
    public String getName() {
        return actor.getName();
    }
    
    public void setName(String name) {
        if (name.isEmpty()) {
            nameCorrect = false;
            return;
        }
        actor.setName(name);
        nameCorrect = true;
    }
    
    public String getPhoto() {
        return actor.getPhoto();
    }
    
    public void setPhoto(String photo) {
        if (photo.isEmpty()) {
            photoCorrect = false;
            return;
        }
        actor.setPhoto(photo);
        photoCorrect = true;
    }
    
    public String getBirthDate() {
        return actor.getFormattedBirthDate();
    }
    
    public void setBirthDate(String birthDate) {
        Date date = DateFormatter.setFormattedDate(birthDate);
        if (date == null || date.getYear() > 9999) {
            birthDateCorrect = false;
            return;
        }
        actor.setBirthDate(date);
        birthDateCorrect = true;
    }
}
