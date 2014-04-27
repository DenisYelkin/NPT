/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yelkin
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nomination.findAll", query = "SELECT n FROM Nomination n"),
    @NamedQuery(name = "Nomination.findBySubstring", query = "SELECT m FROM Nomination m WHERE LOWER(m.name) like LOWER(:q) ORDER BY m.name"),
    @NamedQuery(name = "Nomination.findById", query = "SELECT n FROM Nomination n WHERE n.id = :id"),
    @NamedQuery(name = "Nomination.findByName", query = "SELECT n FROM Nomination n WHERE n.name = :name")})
public class Nomination implements Serializable {
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
    @Size(min = 1, max = 50)
    private String name;
    @JoinColumn(name = "AWARD_ID", referencedColumnName = "ID")
    @ManyToOne
    private Award awardId;

    public Nomination() {
    }

    public Nomination(BigDecimal id) {
        this.id = id;
    }

    public Nomination(BigDecimal id, String name) {
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

    public Award getAwardId() {
        return awardId;
    }

    public void setAwardId(Award awardId) {
        this.awardId = awardId;
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
        if (!(object instanceof Nomination)) {
            return false;
        }
        Nomination other = (Nomination) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "temp.Nomination[ id=" + id + " ]";
    }
    
}
