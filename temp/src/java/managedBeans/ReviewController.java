/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Review;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import session.ReviewFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "reviewController")
@SessionScoped
public class ReviewController {

    @EJB
    private ReviewFacade reviewFacade;

    private Review review;

    private String reviewType;

    public void setReviewById(BigDecimal id) {
        review = reviewFacade.find(id);
        reviewType = review.getRating();
    }

    public String save() {
        review.setRating(reviewType);
        reviewFacade.edit(review);
        FacesContext context = FacesContext.getCurrentInstance();
        UserController bean = (UserController) context.getApplication().evaluateExpressionGet(context, "#{userController}", UserController.class);
        bean.setCurrentUsers(review.getUserId());
        bean.setUserById(review.getUserId().getId());
        return "UserReview";
    }

    public String getReviewType() {
        return reviewType;
    }

    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }

    public Review getReview() {
        return review;
    }
}
