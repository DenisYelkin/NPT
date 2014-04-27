/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.Actor;
import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "actorConverterBean")
@FacesConverter(value = "actorConverter")
public class ActorConverter implements Converter {

    @PersistenceContext(unitName = "tempPU")
    private transient EntityManager em;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return em.find(Actor.class, new BigDecimal(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Actor) value).getId().toString();
    }

}
