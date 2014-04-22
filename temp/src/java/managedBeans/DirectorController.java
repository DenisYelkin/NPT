/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Director;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.DirectorFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "directorController")
@SessionScoped
public class DirectorController {

    private Director director;

    @EJB
    private DirectorFacade directorFacade;


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
}
