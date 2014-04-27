/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Award;
import entities.Nomination;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import session.AwardFacade;
import session.NominationFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "nominationController")
@SessionScoped
public class NominationController {

    private Map<String, Award> awardMap;

    private Nomination nomination;

    @EJB
    private NominationFacade nominationFacade;

    @EJB
    private AwardFacade awardFacade;

    private boolean nominationCreate;
    private boolean nominationNameCorrect = true;

    public String getName() {
        return nomination.getName();
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            nominationNameCorrect = false;
        } else {
            nomination.setName(name);
            nominationNameCorrect = true;
        }
    }

    public boolean isNominationNameCorrect() {
        return nominationNameCorrect;
    }

    public Map<String, Award> getAwardMap() {
        awardMap = new HashMap<>();
        List<Award> awards = awardFacade.findAll();
        for (Award award : awards) {
            awardMap.put(award.getName() + " (" + award.getYear() + ")", award);
        }
        return awardMap;
    }

    public String create(Nomination nomination) {
        if (nomination == null) {
            this.nomination = new Nomination();
            this.nomination.setName("Новая номинация");
            nominationFacade.create(this.nomination);
            nominationCreate = true;
        } else {
            this.nomination = nomination;
            nominationCreate = false;
        }
        return "NominationCreate";
    }

    public String save() {
        if (!nominationNameCorrect) {
            return null;
        }
        nominationFacade.edit(nomination);
        return "index";
    }

    public String cancel() {
        if (nominationCreate) {
            nominationFacade.remove(nomination);
        }
        nominationNameCorrect = true;
        return "index";
    }

    public Nomination getNomination() {
        return nomination;
    }

    public String getNominationCreate() {
        return nominationCreate ? "Создание новой номинации" : "Редактирование номинации";
    }

    public String delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        SearchController bean = (SearchController) context.getApplication().evaluateExpressionGet(context, "#{searchController}", SearchController.class);
        nominationFacade.remove(nomination);
        this.nomination = null;
        nominationNameCorrect = true;
        return bean.backToSearch();
    }
}
