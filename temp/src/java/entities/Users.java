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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByLogin", query = "SELECT u FROM Users u WHERE u.login = :login"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByBirthDate", query = "SELECT u FROM Users u WHERE u.birthDate = :birthDate"),
    @NamedQuery(name = "Users.findByRegistrationDate", query = "SELECT u FROM Users u WHERE u.registrationDate = :registrationDate"),
    @NamedQuery(name = "Users.findByName", query = "SELECT u FROM Users u WHERE u.name = :name")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "unique_id_generator")
    @SequenceGenerator(name = "unique_id_generator", sequenceName = "unique_id")
    @Basic(optional = false)    
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String password;
    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Size(max = 200)
    private String name;
    @OneToMany(mappedBy = "userId")
    private Collection<Review> reviewCollection;

    public Users() {
    }

    public Users(BigDecimal id) {
        this.id = id;
    }

    public Users(BigDecimal id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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

    public String getBirthDate() {
        Calendar date = Calendar.getInstance();
        date.setTime(birthDate);
        return DateFormatter.getRichDate(date);
    }
    
     public String getFormattedBirthDate() {
        Calendar date = Calendar.getInstance();
        date.setTime(birthDate);
        return DateFormatter.getFormattedDate(date);
    }

    public void setFormattedBirthDate(String formattedBirthDate) {
        this.birthDate = DateFormatter.setFormattedDate(formattedBirthDate);
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getRegistrationDate() {
        Calendar date = Calendar.getInstance();
        date.setTime(registrationDate);
        return DateFormatter.getRichDate(date);
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Review> getReviewCollection() {
        return reviewCollection;
    }

    public void setReviewCollection(Collection<Review> reviewCollection) {
        this.reviewCollection = reviewCollection;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "temp.Users[ id=" + id + " ]";
    }
    
}
