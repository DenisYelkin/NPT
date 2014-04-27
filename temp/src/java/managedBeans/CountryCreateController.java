/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Country;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.CountryFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "countryController")
@SessionScoped
public class CountryCreateController {

    private Country country;

    @EJB
    private CountryFacade countryFacade;

    private boolean nameCorrect = true;

    public String getName() {
        return country.getName();
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            nameCorrect = false;
        } else {
            country.setName(name);
        }

    }

    public boolean isNameCorrect() {
        return nameCorrect;
    }

    public String create() {
        country = new Country();
        countryFacade.create(country);
        return "CountryCreate";
    }

    public String save() {
        if (!nameCorrect) {
            return null;
        }
        countryFacade.edit(country);
        return "index";
    }

    public String cancel() {
        countryFacade.remove(country);
        return "index";
    }

}
