/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import entities.Character;
import entities.CharacterItemPK;
import java.math.BigDecimal;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import managedBeans.MovieController;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "characterConverterBean")
@FacesConverter(value = "characterConverter")
public class CharacterConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String[] parts = value.split("-");
        System.out.println(value);
        MovieController bean = (MovieController) context.getApplication().evaluateExpressionGet(context, "#{movieController}", MovieController.class);
        Collection<Character> c = bean.getCharacterMap().values();
        for (Character character : c) {
            if (character.getCharacterItemPK().equals(new CharacterItemPK(new BigDecimal(parts[0]), new BigDecimal(parts[1])))) {
                return character;
            }
        }
        return null;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Character) value).getCharacterItemPK().toString();
    }

}
