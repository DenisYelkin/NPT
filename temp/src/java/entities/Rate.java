/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yelkin
 */
@Entity
@Table(name = "Rate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rate.findAll", query = "SELECT a FROM Rate a"),
    @NamedQuery(name = "Rate.findByMovie", query = "SELECT a FROM Rate a WHERE a.rateItemPK.movieId = :movie"),
    @NamedQuery(name = "Rate.findByUser", query = "SELECT a FROM Rate a WHERE a.rateItemPK.userId = :user"),
    @NamedQuery(name = "Rate.findByMovieAndUser", query = "SELECT a FROM Rate a WHERE a.rateItemPK.movieId = :movie AND a.rateItemPK.userId = :user"),
    @NamedQuery(name = "Rate.findRating", query = "SELECT a FROM Rate a WHERE a.rating = :rating")})
public class Rate implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected RateItemPK rateItemPK;
    
    @Column(name = "rating")
    @Min(0)
    @Max(10)
    private Short rating;

    @JoinColumn(name = "movie_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Movie movie;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users user;

    public Rate(RateItemPK rateItemPK) {
        this.rateItemPK = rateItemPK;
    }

    public Rate(BigDecimal movieId, BigDecimal userId) {
        this.rateItemPK = new RateItemPK(userId, movieId);
    }
    
    public Rate() {
    }

    public RateItemPK getRateItemPK() {
        return rateItemPK;
    }

    public void setRateItemPK(RateItemPK rateItemPK) {
        this.rateItemPK = rateItemPK;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    
    public Short getRating() {
        return rating;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rateItemPK != null ? rateItemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Character)) {
            return false;
        }
        Rate other = (Rate) object;
        return (this.rateItemPK != null || other.rateItemPK == null) && 
                (this.rateItemPK == null || this.rateItemPK.equals(other.rateItemPK));
    }

    @Override
    public String toString() {
        return "entities.Character[ id=" + rateItemPK.hashCode() + " ]";
    }
    
}
