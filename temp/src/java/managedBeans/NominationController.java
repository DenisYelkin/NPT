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
            awardMap.put(award.getName() + "(" + award.getYear() + ")", award);
        }
        return awardMap;
    }
    
    public String create() {
        nomination = new Nomination();
        nomination.setName("New nomination");
        nominationFacade.create(nomination);
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
        nominationFacade.remove(nomination);
        return "index";
    }

    public Nomination getNomination() {
        return nomination;
    }
}
