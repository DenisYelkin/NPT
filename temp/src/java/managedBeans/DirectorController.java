/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Country;
import entities.Director;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.CountryFacade;
import session.DirectorFacade;
import utils.DateFormatter;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "directorController")
@SessionScoped
public class DirectorController {

    private Map<String, Country> countryMap;
    
    private Director director;

    @EJB
    private DirectorFacade directorFacade;
    
    @EJB
    private CountryFacade countryFacade;

    private boolean nameCorrect = true;
    private boolean photoCorrect = true;
    private boolean birthDateCorrect = true;
    
    public Director getDirector() {
        return director;
    }

    public void setDirectorById(BigDecimal id) {
        director = directorFacade.find(id);
    }
    
    public String delete()
    {
        directorFacade.remove(director);
        this.director = null;
        return "index";
    }
    
    public String save() {
        if (!nameCorrect || !photoCorrect || !birthDateCorrect) {
            return null;
        }
        directorFacade.edit(director);
        setDirectorById(director.getId());
        return "Director";
    }
    
    public String cancel() {
        setDirectorById(director.getId());
        return "Director";
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
        return director.getName();
    }
    
    public void setName(String name) {
        if (name.isEmpty()) {
            nameCorrect = false;
            return;
        }
        director.setName(name);
        nameCorrect = true;
    }
    
    public String getPhoto() {
        return director.getPhoto();
    }
    
    public void setPhoto(String photo) {
        if (photo.isEmpty()) {
            photoCorrect = false;
            return;
        }
        director.setPhoto(photo);
        photoCorrect = true;
    }
    
    public String getBirthDate() {
        return director.getFormattedBirthDate();
    }
    
    public void setBirthDate(String birthDate) {
        Date date = DateFormatter.setFormattedDate(birthDate);
        if (date == null || date.getYear() > 9999) {
            birthDateCorrect = false;
            return;
        }
        director.setBirthDate(date);
        birthDateCorrect = true;
    }
}
