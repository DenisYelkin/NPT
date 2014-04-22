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
@Table(name = "MovieNominationConnector")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovieNominationConnector.findAll", query = "SELECT a FROM MovieNominationConnector a"),
    @NamedQuery(name = "MovieNominationConnector.findByMovie", query = "SELECT a FROM MovieNominationConnector a WHERE a.movieNominationItemPK.movieId = :movie"),
    @NamedQuery(name = "MovieNominationConnector.findByNomination", query = "SELECT a FROM MovieNominationConnector a WHERE a.movieNominationItemPK.nominationId = :nomination"),
    @NamedQuery(name = "MovieNominationConnector.findByStatus", query = "SELECT a FROM MovieNominationConnector a WHERE a.status = :status")})
public class MovieNominationConnector implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected MovieNominationItemPK movieNominationItemPK;
    
    @Column(name = "status")
    private String status;   

    @JoinColumn(name = "movie_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Movie movie;
    
    @JoinColumn(name = "nomination_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Nomination nomination;

    public MovieNominationConnector(MovieNominationItemPK movieNominationItemPK) {
        this.movieNominationItemPK = movieNominationItemPK;
    }

    public MovieNominationConnector(BigDecimal movieId, BigDecimal nominationId) {
        this.movieNominationItemPK = new MovieNominationItemPK(nominationId, movieId);
    }
    
    public MovieNominationConnector() {
    }

    public MovieNominationItemPK getMovieNominationConnectorItemPK() {
        return movieNominationItemPK;
    }

    public void setMovieNominationConnectorItemPK(MovieNominationItemPK movieNominationItemPK) {
        this.movieNominationItemPK = movieNominationItemPK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Nomination getNomination() {
        return nomination;
    }

    public void setNomination(Nomination nomination) {
        this.nomination = nomination;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movieNominationItemPK != null ? movieNominationItemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovieNominationConnector)) {
            return false;
        }
        MovieNominationConnector other = (MovieNominationConnector) object;
        return (this.movieNominationItemPK != null || other.movieNominationItemPK == null) && 
                (this.movieNominationItemPK == null || 
                    this.movieNominationItemPK.equals(other.movieNominationItemPK));
    }

    @Override
    public String toString() {
        return "entities.MovieNominationConnector[ id=" + movieNominationItemPK.toString() + " ]";
    }
    
}
