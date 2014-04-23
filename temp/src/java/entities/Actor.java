/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import utils.DateFormatter;

/**
 *
 * @author Yelkin
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actor.findAll", query = "SELECT a FROM Actor a"),
    @NamedQuery(name = "Actor.findBySubstring", query = "SELECT a FROM Actor a WHERE LOWER(a.name) LIKE LOWER(:q) ORDER BY a.name"),
    @NamedQuery(name = "Actor.findById", query = "SELECT a FROM Actor a WHERE a.id = :id"),
    @NamedQuery(name = "Actor.findByName", query = "SELECT a FROM Actor a WHERE a.name = :name"),
    @NamedQuery(name = "Actor.findByBirthDate", query = "SELECT a FROM Actor a WHERE a.birthDate = :birthDate"),
    @NamedQuery(name = "Actor.findByPhoto", query = "SELECT a FROM Actor a WHERE a.photo = :photo")})
public class Actor implements Serializable {
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
    @JoinColumn(name = "BIRTH_COUNTRY", referencedColumnName = "ID")
    @ManyToOne
    private Country birthCountry;

    public Actor() {
    }

    public Actor(BigDecimal id) {
        this.id = id;
    }

    public Actor(BigDecimal id, String name) {
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
        Calendar date = Calendar.getInstance();
        date.setTime(birthDate);
        return DateFormatter.getFormattedDate(date);
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
        if (!(object instanceof Actor)) {
            return false;
        }
        Actor other = (Actor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "temp.Actor[ id=" + id + " ]";
    }

}
