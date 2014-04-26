/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m"),
    @NamedQuery(name = "Movie.findBySubstring", query = "SELECT m FROM Movie m WHERE LOWER(m.name) like LOWER(:q) ORDER BY m.name"),
    @NamedQuery(name = "Movie.findById", query = "SELECT m FROM Movie m WHERE m.id = :id"),
    @NamedQuery(name = "Movie.findByName", query = "SELECT m FROM Movie m WHERE m.name = :name"),
    @NamedQuery(name = "Movie.findByReleaseDate", query = "SELECT m FROM Movie m WHERE m.releaseDate = :releaseDate"),
    @NamedQuery(name = "Movie.findByRestriction", query = "SELECT m FROM Movie m WHERE m.restriction = :restriction"),
    @NamedQuery(name = "Movie.findByDuration", query = "SELECT m FROM Movie m WHERE m.duration = :duration"),
    @NamedQuery(name = "Movie.findByWorldBox", query = "SELECT m FROM Movie m WHERE m.worldBox = :worldBox"),
    @NamedQuery(name = "Movie.findByBudget", query = "SELECT m FROM Movie m WHERE m.budget = :budget"),
    @NamedQuery(name = "Movie.findBySite", query = "SELECT m FROM Movie m WHERE m.site = :site"),
    @NamedQuery(name = "Movie.findByPoster", query = "SELECT m FROM Movie m WHERE m.poster = :poster"),
    @NamedQuery(name = "Movie.findByRating", query = "SELECT m FROM Movie m WHERE m.rating = :rating"),
    @NamedQuery(name = "Movie.findByVoiceCount", query = "SELECT m FROM Movie m WHERE m.voiceCount = :voiceCount")})
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "unique_id_generator")
    @SequenceGenerator(name = "unique_id_generator", sequenceName = "unique_id")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @Column(name = "RELEASE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;
    private Short restriction;
    private Short duration;
    @Column(name = "WORLD_BOX")
    private BigInteger worldBox;
    private BigInteger budget;
    @Size(max = 1000)
    private String site;
    @Lob
    private String description;
    @Size(max = 1000)
    private String poster;
    private BigDecimal rating;
    @Column(name = "VOICE_COUNT")
    private Integer voiceCount;
    @ManyToMany(mappedBy = "movieCollection")
    private Collection<Country> countryCollection;
    @ManyToMany(mappedBy = "movieCollection")
    private Collection<Director> directorCollection;
    @ManyToMany(mappedBy = "movieCollection")
    private Collection<Genre> genreCollection;
    @OneToMany(mappedBy = "movieId")
    private Collection<Review> reviewCollection;

    public Movie() {
    }

    public Movie(BigDecimal id) {
        this.id = id;
    }

    public Movie(BigDecimal id, String name) {
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

    public String getReleaseDate() {
        Calendar date = Calendar.getInstance();
        date.setTime(releaseDate);
        return DateFormatter.getRichDate(date);
    }

    public String getFormattedReleaseDate() {
        if (releaseDate == null) {
            return "";
        } else {
            Calendar date = Calendar.getInstance();
            date.setTime(releaseDate);
            return DateFormatter.getFormattedDate(date);
        }
    }

    public void setFormattedReleaseDate(String formattedReleaseDate) {
        this.releaseDate = DateFormatter.setFormattedDate(formattedReleaseDate);
    }

    public int getReleaseYear() {
        Calendar date = Calendar.getInstance();
        date.setTime(releaseDate);
        return date.get(Calendar.YEAR);
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Short getRestriction() {
        return restriction;
    }

    public void setRestriction(Short restriction) {
        this.restriction = restriction;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    public BigInteger getWorldBox() {
        return worldBox;
    }

    public void setWorldBox(BigInteger worldBox) {
        this.worldBox = worldBox;
    }

    public BigInteger getBudget() {
        return budget;
    }

    public void setBudget(BigInteger budget) {
        this.budget = budget;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getVoiceCount() {
        return voiceCount;
    }

    public void setVoiceCount(Integer voiceCount) {
        this.voiceCount = voiceCount;
    }

    @XmlTransient
    public Collection<Country> getCountryCollection() {
        return countryCollection;
    }

    public void setCountryCollection(Collection<Country> countryCollection) {
        this.countryCollection = countryCollection;
    }

    @XmlTransient
    public Collection<Director> getDirectorCollection() {
        return directorCollection;
    }

    public void setDirectorCollection(Collection<Director> directorCollection) {
        this.directorCollection = directorCollection;
    }

    @XmlTransient
    public Collection<Genre> getGenreCollection() {
        return genreCollection;
    }

    public void setGenreCollection(Collection<Genre> genreCollection) {
        this.genreCollection = genreCollection;
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
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "temp.Movie[ id=" + id + " ]";
    }

}
