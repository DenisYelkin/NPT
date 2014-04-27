/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Award;
import entities.Country;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import session.AwardFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "awardController")
@SessionScoped
public class AwardController {

    private Award award;

    @EJB
    private AwardFacade awardFacade;

    private boolean awardNameCorrect = true;
    private boolean locationCorrect = true;
    private boolean yearCorrect = true;
    private boolean awardCreate;

    public String getName() {
        return award.getName();
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            awardNameCorrect = false;
        } else {
            award.setName(name);
            awardNameCorrect = true;
        }

    }

    public String getLocation() {
        return award.getLocation();
    }

    public void setLocation(String location) {
        if (location == null || location.isEmpty()) {
            locationCorrect = false;
        } else {
            award.setLocation(location);
            locationCorrect = true;
        }
    }

    public String getAwardYear() {
        Short result = award.getYear();
        if (result == null) {
            return "";
        }
        return result.toString();
    }

    public void setAwardYear(String year) {
        if (year == null || year.isEmpty()) {
            yearCorrect = false;
        } else {
            try {
                Short awardYear = Short.parseShort(year);
                if (awardYear < 0 || awardYear > 3000) {
                    yearCorrect = false;
                } else {
                    award.setYear(awardYear);
                    yearCorrect = true;
                }
            } catch (NumberFormatException e) {
                yearCorrect = false;
            }
        }
    }

    public boolean isNameCorrect() {
        return awardNameCorrect;
    }

    public boolean isLocationCorrect() {
        return locationCorrect;
    }

    public boolean isYearCorrect() {
        return yearCorrect;
    }

    public String create(Award award) {
        if (award == null) {
            awardCreate = true;
            this.award = new Award();
            this.award.setName("Новая награда");
            awardFacade.create(this.award);
        } else {
            awardCreate = false;
            this.award = award;
        }
        return "AwardCreate";
    }

    public String getAwardCreate() {
        return awardCreate ? "Создание новой награды" : "Редактирование награды";
    }

    public String save() {
        if (!awardNameCorrect || !yearCorrect || !locationCorrect) {
            return null;
        }
        awardFacade.edit(award);
        return "index";
    }

    public String cancel() {
        if (awardCreate) {
            awardFacade.remove(award);
        }
        awardNameCorrect = true;
        yearCorrect = true;
        locationCorrect = true;
        return "index";
    }

    public String delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        SearchController bean = (SearchController) context.getApplication().evaluateExpressionGet(context, "#{searchController}", SearchController.class);
        awardFacade.remove(award);
        this.award = null;
        awardNameCorrect = true;
        yearCorrect = true;
        locationCorrect = true;
        return bean.backToSearch();
    }
}
