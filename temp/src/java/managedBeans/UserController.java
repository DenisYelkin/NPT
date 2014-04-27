/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Rate;
import entities.Review;
import entities.Users;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import session.ReviewFacade;
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

    @EJB
    private ReviewFacade reviewFacade;

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
        FacesContext context = FacesContext.getCurrentInstance();
        SearchController bean = (SearchController) context.getApplication().evaluateExpressionGet(context, "#{searchController}", SearchController.class);
        bean.setSearchResult(null);
        currentUsers = null;
        return null;
    }

    public String delete() {
        usersFacade.remove(user);
        this.currentUsers = null;
        this.user = null;
        return "index";
    }

    public String removeReview(Review review) {
        reviewFacade.remove(review);
        currentUsers = review.getUserId();
        setUserById(review.getUserId().getId());
        return null;
    }

    public String editReview(Review review) {
        FacesContext context = FacesContext.getCurrentInstance();
        ReviewController bean = (ReviewController) context.getApplication().evaluateExpressionGet(context, "#{reviewController}", ReviewController.class);
        bean.setReviewById(review.getReviewId());
        return "ReviewEdit";
    }

    public String editUser(boolean isNewUser) {
        FacesContext context = FacesContext.getCurrentInstance();
        UserCreateController bean = (UserCreateController) context.getApplication().evaluateExpressionGet(context, "#{userCreateController}", UserCreateController.class);
        if (isNewUser) {
            bean.createNewUser(null);
        } else {
            bean.createNewUser(currentUsers);
        }
        return "UserCreate";
    }
}
