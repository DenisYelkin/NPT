/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Rate;
import entities.Users;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.UsersFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "userController")
@SessionScoped
public class UserController {

    @EJB
    private UsersFacade usersFacade;

    private Users currentUsers;
    private Users user;
    private String login;
    private String password;
    private boolean isLastLoginSuccessful = true;
    private List<Rate> ratesForUserList;

    public Users getUser() {
        return user;
    }

    public void setUserById(BigDecimal id) {
        user = usersFacade.find(id);
        ratesForUserList = usersFacade.getRatesForUser(user);
    }

    public List<Rate> getRatesForUserList() {
        return ratesForUserList;
    }

    public Users getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(Users currentUsers) {
        this.currentUsers = currentUsers;
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

    public boolean isIsLastLoginSuccessful() {
        if (!isLastLoginSuccessful) {
            isLastLoginSuccessful = true;
            return false;
        }
        return isLastLoginSuccessful;
    }

    public String validate() {
        login = "alena";
        password = "*****";
        Users user = usersFacade.getUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            this.currentUsers = user;
            login = "";
            password = "";
            isLastLoginSuccessful = true;
            return null;
        }
        this.currentUsers = null;
        isLastLoginSuccessful = false;
        return null;
    }

    public String logout() {
        currentUsers = null;
        return null;
    }
    
    public String delete()
    {
        usersFacade.remove(user);
        this.currentUsers = null;
        this.user = null;
        return "index";
    }
}
