/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import utils.DateFormatter;

/**
 *
 * @author Yelkin
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Director.findAll", query = "SELECT d FROM Director d"),
    @NamedQuery(name = "Director.findBySubstring", query = "SELECT m FROM Director m WHERE LOWER(m.name) like LOWER(:q) ORDER BY m.name"),
    @NamedQuery(name = "Director.findById", query = "SELECT d FROM Director d WHERE d.id = :id"),
    @NamedQuery(name = "Director.findByName", query = "SELECT d FROM Director d WHERE d.name = :name"),
    @NamedQuery(name = "Director.findByBirthDate", query = "SELECT d FROM Director d WHERE d.birthDate = :birthDate"),
    @NamedQuery(name = "Director.findByPhoto", query = "SELECT d FROM Director d WHERE d.photo = :photo")})
public class Director implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "unique_id_generator")
    @SequenceGenerator(name = "unique_id_generator", sequenceName = "unique_id")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    @Size(max = 1000)
    private String photo;
    @JoinTable(name = "MOVIEDIRECTORCONNECTOR", joinColumns = {
        @JoinColumn(name = "DIRECTOR_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "MOVIE_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Movie> movieCollection;
    @JoinColumn(name = "BIRTH_COUNTRY", referencedColumnName = "ID")
    @ManyToOne
    private Country birthCountry;

    public Director() {
    }

    public Director(BigDecimal id) {
        this.id = id;
    }

    public Director(BigDecimal id, String name) {
        this.id = id;
        this.name = name;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        Calendar date = Calendar.getInstance();
        date.setTime(birthDate);
        return DateFormatter.getRichDate(date);
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFormattedBirthDate() {
        if (birthDate == null) {
            return "";
        } else {
            Calendar date = Calendar.getInstance();
            date.setTime(birthDate);
            return DateFormatter.getFormattedDate(date);
        }
    }

    public void setFormattedBirthDate(String formattedBirthDate) {
        this.birthDate = DateFormatter.setFormattedDate(formattedBirthDate);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @XmlTransient
    public Collection<Movie> getMovieCollection() {
        return movieCollection;
    }

    public void setMovieCollection(Collection<Movie> movieCollection) {
        this.movieCollection = movieCollection;
    }

    public Country getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(Country birthCountry) {
        this.birthCountry = birthCountry;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Director)) {
            return false;
        }
        Director other = (Director) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "temp.Director[ id=" + id + " ]";
    }

}
