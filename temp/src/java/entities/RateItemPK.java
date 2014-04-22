/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Yelkin
 */
@Embeddable
public class RateItemPK implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private BigDecimal userId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "movie_id")
    private BigDecimal movieId;

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public BigDecimal getMovieId() {
        return movieId;
    }

    public void setMovieId(BigDecimal movieId) {
        this.movieId = movieId;
    }

    public RateItemPK() {
    }

    public RateItemPK(BigDecimal userId, BigDecimal movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
    
    @Override
    public boolean equals(Object otherOb) {
        if (this == otherOb) {
            return true;
        }
        if (!(otherOb instanceof RateItemPK)) {
            return false;
        }
        RateItemPK other = (RateItemPK) otherOb;
        
        if (userId == null) {
            return other.userId == null;
        } else if (movieId == null) {
            return other.movieId == null;
        } else {
            return userId.equals(other.userId) && movieId.equals(other.movieId);
        }
    }

    @Override
    public int hashCode() {
        return (((userId == null || movieId == null) ? 0 : userId.hashCode()) ^ (movieId.hashCode()));
    }

    @Override
    public String toString() {
        return "" + userId + "-" + movieId;
    }
    
}
