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
import utils.DateFormatter;

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
    private String title;
    private boolean isCreate;
    @EJB
    private UsersFacade usersFacade;

    private boolean isRegistrationDataCorrect = true;

    public void createNewUser(Users user) {
        if (user == null) {
            this.user = new Users();
            title = "Регистрация";
            isCreate = true;
        } else {
            this.user = user;
            login = user.getLogin();
            password = user.getPassword();
            birthDate = user.getFormattedBirthDate();
            title = "Редактирование";
            isCreate = false;
        }
    }

    public String getTitle() {
        return title;
    }

    public String validate() {
        Date formattedDate = DateFormatter.setFormattedDate(birthDate);
        if (birthDate == null || formattedDate == null || user.getName() == null || password == null || login == null
                || password.length() < 1 || password.length() > 100
                || login.length() < 1 || login.length() > 100) {
            isRegistrationDataCorrect = false;
            return null;
        }
        user.setBirthDate(formattedDate);
        user.setPassword(password);
        user.setLogin(login);
        if (isCreate) {
            usersFacade.create(user);
        } else {
            usersFacade.edit(user);
        }
        isRegistrationDataCorrect = true;
        FacesContext context = FacesContext.getCurrentInstance();
        UserController bean = (UserController) context.getApplication().evaluateExpressionGet(context, "#{userController}", UserController.class);
        bean.setUserById(user.getId());
        bean.setCurrentUsers(user);
        return "User";
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
