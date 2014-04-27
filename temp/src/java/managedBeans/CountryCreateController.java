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
import javax.faces.context.FacesContext;
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

    private boolean countryCreate;

    public String getName() {
        return country.getName();
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            nameCorrect = false;
        } else {
            country.setName(name);
            nameCorrect = true;
        }
    }

    public boolean isNameCorrect() {
        return nameCorrect;
    }

    public String create(Country country) {
        if (country == null) {
            countryCreate = true;
            this.country = new Country();
            countryFacade.create(this.country);
        } else {
            countryCreate = false;
            this.country = country;
        }
        return "CountryCreate";
    }

    public String getCountryCreate() {
        return countryCreate ? "Создание новой страны" : "Редактирование страны";
    }

    public boolean isCreateCountry() {
        return countryCreate;
    }

    public String save() {
        if (!nameCorrect) {
            return null;
        }
        countryFacade.edit(country);
        return "index";
    }

    public String cancel() {
        if (countryCreate) {
            countryFacade.remove(country);
        }
        nameCorrect = true;
        return "index";
    }

    public String delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        SearchController bean = (SearchController) context.getApplication().evaluateExpressionGet(context, "#{searchController}", SearchController.class);
        countryFacade.remove(country);
        this.country = null;
        nameCorrect = true;
        return bean.backToSearch();
    }
}
