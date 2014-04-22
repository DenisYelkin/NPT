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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yelkin
 */
@Entity
@Table(name = "CHARACTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Character.findAll", query = "SELECT a FROM Character a"),
    @NamedQuery(name = "Character.findByMovie", query = "SELECT a FROM Character a WHERE a.characterItemPK.movieId = :movie"),
    @NamedQuery(name = "Character.findByActor", query = "SELECT a FROM Character a WHERE a.characterItemPK.actorId = :actor"),
    @NamedQuery(name = "Character.findByDescription", query = "SELECT a FROM Character a WHERE a.description = :description"),
    @NamedQuery(name = "Character.findByCharacterName", query = "SELECT a FROM Character a WHERE a.characterName = :characterName")})
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CharacterItemPK characterItemPK;

    @Column(name = "char_name")
    private String characterName;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "movie_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Movie movie;

    @JoinColumn(name = "actor_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Actor actor;

    public Character() {
    }

    public Character(CharacterItemPK characterItemPK) {
        this.characterItemPK = characterItemPK;
    }

    public Character(BigDecimal movieId, BigDecimal actorId) {
        this.characterItemPK = new CharacterItemPK(actorId, movieId);
    }

    public CharacterItemPK getCharacterItemPK() {
        return characterItemPK;
    }

    public void setCharacterItemPK(CharacterItemPK characterItemPK) {
        this.characterItemPK = characterItemPK;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (characterItemPK != null ? characterItemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (this == object) {
            return true;
        }
        if (!(object instanceof Character)) {
            return false;
        }
        Character other = (Character) object;
        return (this.characterItemPK.equals(other.characterItemPK));
    }

    @Override
    public String toString() {
        return "entities.Character[ id=" + characterItemPK + " ]";
    }

}
