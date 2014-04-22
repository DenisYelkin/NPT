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
public class MovieNominationItemPK implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "nomination_id")
    private BigDecimal nominationId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "movie_id")
    private BigDecimal movieId;

    public BigDecimal getNominationId() {
        return nominationId;
    }

    public void setNominationId(BigDecimal nominationId) {
        this.nominationId = nominationId;
    }

    public BigDecimal getMovieId() {
        return movieId;
    }

    public void setMovieId(BigDecimal movieId) {
        this.movieId = movieId;
    }

    public MovieNominationItemPK() {
    }

    public MovieNominationItemPK(BigDecimal nominationId, BigDecimal movieId) {
        this.nominationId = nominationId;
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object otherOb) {
        if (this == otherOb) {
            return true;
        }
        if (!(otherOb instanceof MovieNominationItemPK)) {
            return false;
        }
        MovieNominationItemPK other = (MovieNominationItemPK) otherOb;
        
        if (nominationId == null) {
            return other.nominationId == null;
        } else if (movieId == null) {
            return other.movieId == null;
        } else {
            return nominationId.equals(other.nominationId) && movieId.equals(other.movieId);
        }
    }

    @Override
    public int hashCode() {
        return (((nominationId == null || movieId == null) ? 0 : nominationId.hashCode()) ^ (movieId.hashCode()));
    }

    @Override
    public String toString() {
        return "" + nominationId + "-" + movieId;
    }
    
}
