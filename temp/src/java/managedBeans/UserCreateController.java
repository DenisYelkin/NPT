/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Users;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import session.UsersFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "userCreateController")
@SessionScoped
public class UserCreateController {

    private Users user;

    
    private String birthDate;
    private String login;
    private String password;

    @EJB
    private UsersFacade usersFacade;

    private boolean isRegistrationDataCorrect = true;

    public void createNewUser() {
        user = new Users();
    }

    public String validate() {
        if (birthDate == null || user.getName() == null || password == null || login == null
                || password.length() < 1 || password.length() > 100
                || login.length() < 1 || login.length() > 100) {
            isRegistrationDataCorrect = false;
            return null;
        }
        String[] parseDate = birthDate.split("-");
        if (parseDate.length != 3) {
            isRegistrationDataCorrect = false;
            return null;
        }
        try {
            int day = Integer.parseInt(parseDate[0]);
            int month = Integer.parseInt(parseDate[1]);
            int year = Integer.parseInt(parseDate[2]);
            user.setBirthDate(new Date(year + 1900, month - 1, day));
            user.setPassword(password);
            user.setLogin(login);
            usersFacade.create(user);
            isRegistrationDataCorrect = true;
            FacesContext context = FacesContext.getCurrentInstance();
            UserController bean = (UserController) context.getApplication().evaluateExpressionGet(context, "#{userController}", UserController.class);
            bean.setUserById(user.getId());
            bean.setCurrentUsers(user);
            return "User";
        } catch (NumberFormatException e) {
            isRegistrationDataCorrect = false;
            return null;
        }
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getBirthDate() {
        return birthDate;
    }

    public boolean isIsRegistrationDataCorrect() {
        return isRegistrationDataCorrect;
    }

    public void setIsRegistrationDataCorrect(boolean isRegistrationDataCorrect) {
        this.isRegistrationDataCorrect = isRegistrationDataCorrect;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
