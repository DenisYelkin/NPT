/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.Nomination;
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
@ManagedBean(name = "nominationConverterBean")
@FacesConverter(value = "nominationConverter")
public class NominationConverter implements Converter {

    @PersistenceContext(unitName = "tempPU")
    private transient EntityManager em;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("ZJOPA " + value);
        return em.find(Nomination.class, new BigDecimal(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Nomination) value).getId().toString();
    }

}
