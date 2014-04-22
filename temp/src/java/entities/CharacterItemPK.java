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
public class CharacterItemPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "actor_id")
    private BigDecimal actorId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "movie_id")
    private BigDecimal movieId;

    
    public BigDecimal getActorId() {
        return actorId;
    }

    public void setActorId(BigDecimal actorId) {
        this.actorId = actorId;
    }

    public BigDecimal getMovieId() {
        return movieId;
    }

    public void setMovieId(BigDecimal movieId) {
        this.movieId = movieId;
    }

    public CharacterItemPK() {
    }

    public CharacterItemPK(BigDecimal actorId, BigDecimal movieId) {
        this.actorId = actorId;
        this.movieId = movieId;
    }
    
    @Override
    public boolean equals(Object otherOb) {
        if (this == otherOb) {
            return true;
        }
        if (!(otherOb instanceof CharacterItemPK)) {
            return false;
        }
        CharacterItemPK other = (CharacterItemPK) otherOb;
        
        if (actorId == null) {
            return other.actorId == null;
        } else if (movieId == null) {
            return other.movieId == null;
        } else {
            return actorId.equals(other.actorId) && movieId.equals(other.movieId);
        }
    }

    @Override
    public int hashCode() {
        return (((actorId == null || movieId == null) ? 0 : actorId.hashCode()) ^ (movieId.hashCode()));
    }

    @Override
    public String toString() {
        return actorId + "-" + movieId;
    }

}
