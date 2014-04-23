/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Actor;
import entities.Character;
import entities.Country;
import entities.Director;
import entities.Genre;
import entities.Movie;
import entities.MovieNominationConnector;
import entities.Nomination;
import entities.Rate;
import entities.Review;
import entities.Users;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.ActorFacade;
import session.CharacterFacade;
import session.CountryFacade;
import session.DirectorFacade;
import session.GenreFacade;
import session.MovieFacade;
import session.MovieNominationConnectorFacade;
import session.NominationFacade;
import session.RateFacade;
import session.ReviewFacade;
import utils.DateFormatter;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "movieController")
@SessionScoped
public class MovieController {

    private static final String DESCRIPTION_STRING = "Описание героя";
    private Map<String, Country> countryMap;
    private Map<String, Director> directorMap;
    private Map<String, Genre> genreMap;
    private Map<String, Character> characterMap;
    private Map<String, Nomination> nominationMap;
    private String characterName;
    private String characterDescription;

    private List<Country> unsavedCounties;
    private Country selectedCountry;

    private List<Director> unsavedDirectors;
    private Director selectedDirector;

    private List<Character> unsavedCharacters;
    private Character selectedCharacter;

    private List<Genre> unsavedGenres;
    private Genre selectedGenre;

    private List<MovieNominationConnector> unsavedNomination;
    private Nomination selectedNomination;

    @EJB
    private MovieFacade movieFacade;
    private Movie movie;

    @EJB
    private CharacterFacade characterFacade;
    private List<Actor> actorList;

    @EJB
    private MovieNominationConnectorFacade movieNominationConnectorFacade;
    private List<MovieNominationConnector> movieNominationList;

    @EJB
    private RateFacade rateFacade;

    @EJB
    private DirectorFacade directorFacade;
    @EJB
    private ActorFacade actorFacade;
    @EJB
    private GenreFacade genreFacade;

    @EJB
    private CountryFacade countryFacade;

    @EJB
    private NominationFacade nominationFacade;

    @EJB
    private ReviewFacade reviewFacade;

    private Short userRating;
    private String nominationStatus = "номинант";
    private boolean characterNameCorrect = true;
    private boolean movieNameCorrect = true;
    private boolean dateCorrect = true;
    private boolean posterCorrect = true;
    private boolean budgetCorrect = true;
    private boolean restrictionCorrect = true;
    private boolean durationCorrect = true;

    private String reviewText;
    private String reviewType = "положительный";

    public Short getUserRating() {
        return userRating;
    }

    public void setUserRating(Short userRating) {
        this.userRating = userRating;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setMovieById(BigDecimal id) {
        movie = movieFacade.find(id);
        actorList = characterFacade.getActorsForMovie(id);
        movieNominationList = movieNominationConnectorFacade.getNominationsByMovie(movie);
        unsavedCounties = new LinkedList<>();
        unsavedCounties.addAll(movie.getCountryCollection());
        unsavedDirectors = new LinkedList<>();
        unsavedDirectors.addAll(movie.getDirectorCollection());
        unsavedGenres = new LinkedList<>();
        unsavedGenres.addAll(movie.getGenreCollection());
        unsavedCharacters = new LinkedList<>();
        unsavedCharacters.addAll(characterFacade.getCharactersForMovie(id));
        unsavedNomination = new LinkedList<>();
        unsavedNomination.addAll(movieNominationList);
        characterDescription = DESCRIPTION_STRING;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public String getRichBudget() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(Long.parseLong(movie.getBudget().toString()));
    }

    public List<MovieNominationConnector> getMovieNominationList() {
        return movieNominationList;
    }

    public String delete() {
        movieFacade.remove(movie);
        this.movie = null;
        return "index";
    }

    public String getRateForCurrentUser(Users user) {
        Rate rate = movieFacade.getMovieRateForUser(user, movie);
        if (rate == null) {
            return "Нет оценки";
        }
        userRating = rate.getRating();
        return rate.getRating().toString();
    }

    public String updateRating(Users user) {
        Rate rate = movieFacade.getMovieRateForUser(user, movie);
        rate.setRating(userRating);
        rateFacade.edit(rate);
        setMovieById(movie.getId());
        return "MovieDetails";
    }

    public String addCountryForMovie() {
        selectedCountry.getMovieCollection().add(movie);
        unsavedCounties.add(selectedCountry);
        selectedCountry = null;
        return null;
    }

    public String addDirectorForMovie() {
        selectedDirector.getMovieCollection().add(movie);
        unsavedDirectors.add(selectedDirector);
        selectedDirector = null;
        return null;
    }

    public String addGenreForMovie() {
        selectedGenre.getMovieCollection().add(movie);
        unsavedGenres.add(selectedGenre);
        selectedGenre = null;
        return null;
    }

    public String addCharacterForMovie() {
        if (characterName == null || characterName.isEmpty()) {
            characterNameCorrect = false;
            return null;
        }
        selectedCharacter.setCharacterName(characterName);
        selectedCharacter.setDescription(characterDescription);
        unsavedCharacters.add(selectedCharacter);
        selectedCharacter = null;
        characterDescription = DESCRIPTION_STRING;
        characterName = "";
        characterNameCorrect = true;
        return null;
    }

    public String addNominationForMovie() {
        MovieNominationConnector mnc = new MovieNominationConnector(movie.getId(), selectedNomination.getId());
        mnc.setMovie(movie);
        mnc.setNomination(selectedNomination);
        mnc.setStatus(nominationStatus);
        unsavedNomination.add(mnc);
        return null;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public Genre getSelectedGenre() {
        return selectedGenre;
    }

    public void setSelectedGenre(Genre selectedGenre) {
        this.selectedGenre = selectedGenre;
    }

    public Nomination getSelectedNomination() {
        return selectedNomination;
    }

    public void setSelectedNomination(Nomination selectedNomination) {
        this.selectedNomination = selectedNomination;
    }

    public Map<String, Country> getCountryMap() {
        List<Country> countries = countryFacade.findAll();
        countryMap = new HashMap<>();
        for (Country country : countries) {
            if (!unsavedCounties.contains(country)) {
                countryMap.put(country.getName(), country);
            }
        }
        return countryMap;
    }

    public Map<String, Director> getDirectorMap() {
        List<Director> directors = directorFacade.findAll();
        directorMap = new HashMap<>();
        for (Director director : directors) {
            if (!unsavedDirectors.contains(director)) {
                directorMap.put(director.getName(), director);
            }
        }
        return directorMap;
    }

    public Map<String, Character> getCharacterMap() {
        List<Actor> actors = actorFacade.findAll();
        for (Character character : unsavedCharacters) {
            actors.remove(character.getActor());
        }
        characterMap = new HashMap<>();
        for (Actor actor : actors) {
            Character character = new Character(movie.getId(), actor.getId());
            character.setActor(actor);
            character.setMovie(movie);
            characterMap.put(actor.getName(), character);
        }
        return characterMap;
    }

    public Map<String, Genre> getGenreMap() {
        List<Genre> genres = genreFacade.findAll();
        genreMap = new HashMap<>();
        for (Genre genre : genres) {
            if (!unsavedGenres.contains(genre)) {
                genreMap.put(genre.getName(), genre);
            }
        }
        return genreMap;
    }

    public Map<String, Nomination> getNominationMap() {
        List<Nomination> nominations = nominationFacade.findAll();
        nominationMap = new HashMap<>();
        List<Nomination> movieNominations = new LinkedList<>();
        for (MovieNominationConnector mnc : unsavedNomination) {
            movieNominations.add(mnc.getNomination());
        }
        for (Nomination nomination : nominations) {
            if (!movieNominations.contains(nomination)) {
                nominationMap.put(nomination.getAwardId().getName() + " (" + nomination.getAwardId().getYear() + ") " + nomination.getName(), nomination);
            }
        }
        return nominationMap;
    }

    public String removeCountryForMovie(Country country) {
        unsavedCounties.remove(country);
        return null;
    }

    public String removeDirectorForMovie(Director director) {
        unsavedDirectors.remove(director);
        return null;
    }

    public String removeGenreForMovie(Genre genre) {
        unsavedGenres.remove(genre);
        return null;
    }

    public String removeCharacterForMovie(Character character) {
        unsavedCharacters.remove(character);
        return null;
    }

    public String removeNominationForMovie(MovieNominationConnector mnc) {
        unsavedNomination.remove(mnc);
        return null;
    }

    public List<Country> getUnsavedCounties() {
        return unsavedCounties;
    }

    public List<Director> getUnsavedDirectors() {
        return unsavedDirectors;
    }

    public List<Genre> getUnsavedGenres() {
        return unsavedGenres;
    }

    public List<MovieNominationConnector> getUnsavedNomination() {
        return unsavedNomination;
    }

    public Director getSelectedDirector() {
        return selectedDirector;
    }

    public void setSelectedDirector(Director selectedDirector) {
        this.selectedDirector = selectedDirector;
    }

    public List<Character> getUnsavedCharacters() {
        return unsavedCharacters;
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterDescription() {
        return characterDescription;
    }

    public void setCharacterDescription(String characterDescription) {
        this.characterDescription = characterDescription;
    }

    public String getNominationStatus() {
        return nominationStatus;
    }

    public void setNominationStatus(String nominationStatus) {
        this.nominationStatus = nominationStatus;
    }

    public String save() {
        if (!movieNameCorrect || !posterCorrect || !dateCorrect || !characterNameCorrect || !budgetCorrect || !restrictionCorrect || !durationCorrect) {
            return null;
        }
        movieFacade.edit(movie);
        for (MovieNominationConnector mnc : movieNominationList) {
            movieNominationConnectorFacade.remove(mnc);
        }
        for (MovieNominationConnector mnc : unsavedNomination) {
            movieNominationConnectorFacade.create(mnc);
        }
        List<Character> movieCharacters = characterFacade.getCharactersForMovie(movie.getId());
        for (Character character : movieCharacters) {
            characterFacade.remove(character);
        }
        for (Character character : unsavedCharacters) {
            characterFacade.create(character);
        }
        for (Director director : movie.getDirectorCollection()) {
            director.getMovieCollection().remove(movie);
            directorFacade.edit(director);
        }
        for (Director director : unsavedDirectors) {
            director.getMovieCollection().add(movie);
            directorFacade.edit(director);
        }
        for (Country country : movie.getCountryCollection()) {
            country.getMovieCollection().remove(movie);
            countryFacade.edit(country);
        }
        for (Country country : unsavedCounties) {
            country.getMovieCollection().add(movie);
            countryFacade.edit(country);
        }
        for (Genre genre : movie.getGenreCollection()) {
            genre.getMovieCollection().remove(movie);
            genreFacade.edit(genre);
        }
        for (Genre genre : unsavedGenres) {
            genre.getMovieCollection().add(movie);
            genreFacade.edit(genre);
        }
        setMovieById(movie.getId());
        return "MovieDetails";
    }

    public String cancel() {
        setMovieById(movie.getId());
        return "MovieDetails";
    }

    public boolean isCharacterNameCorrect() {
        return characterNameCorrect;
    }

    public void setCharacterNameCorrect(boolean characterNameCorrect) {
        this.characterNameCorrect = characterNameCorrect;
    }

    public String getMovieName() {
        return movie.getName();
    }

    public void setMovieName(String movieName) {
        if (movieName.isEmpty()) {
            movieNameCorrect = false;
            return;
        }
        movie.setName(movieName);
        movieNameCorrect = true;
    }

    public boolean isMovieNameCorrect() {
        return movieNameCorrect;
    }

    public String getDate() {
        return movie.getFormattedReleaseDate();
    }

    public void setDate(String date) {
        Date movieDate = DateFormatter.setFormattedDate(date);
        if (movieDate == null || movieDate.getYear() > 9999) {
            dateCorrect = false;
            return;
        }
        movie.setReleaseDate(movieDate);
        dateCorrect = true;
    }

    public boolean isDateCorrect() {
        return dateCorrect;
    }

    public String getPoster() {
        return movie.getPoster();
    }

    public void setPoster(String poster) {
        if (poster.isEmpty()) {
            posterCorrect = false;
            return;
        }
        movie.setPoster(poster);
        posterCorrect = true;
    }

    public boolean isPosterCorrect() {
        return posterCorrect;
    }

    public String getBudget() {
        return movie.getBudget().toString();
    }

    public void setBudget(String budget) {
        try {
            BigInteger movieBudget = new BigInteger(budget);
            movie.setBudget(movieBudget);
            budgetCorrect = true;
        } catch (NumberFormatException e) {
            budgetCorrect = false;
        }
    }

    public boolean isBudgetCorrect() {
        return budgetCorrect;
    }

    public String getRestriction() {
        return movie.getRestriction().toString();
    }

    public void setRestriction(String restriction) {
        try {
            Short movieRestriction = Short.parseShort(restriction);
            movie.setRestriction(movieRestriction);
            restrictionCorrect = true;
        } catch (NumberFormatException e) {
            restrictionCorrect = false;
        }
    }

    public boolean isRestrictionCorrect() {
        return restrictionCorrect;
    }

    public String getDuration() {
        return movie.getDuration().toString();
    }

    public void setDuration(String duration) {
        try {
            Short movieDuration = Short.parseShort(duration);
            movie.setRestriction(movieDuration);
            durationCorrect = true;
        } catch (NumberFormatException e) {
            durationCorrect = false;
        }
    }

    public boolean isDurationCorrect() {
        return durationCorrect;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewType() {
        return reviewType;
    }

    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }

    public String saveReview(Users user) {
        Review review = new Review();
        review.setMovieId(movie);
        review.setRating(reviewType);
        review.setText(reviewText);
        review.setUserId(user);
        reviewFacade.create(review);
        setMovieById(movie.getId());
        reviewText = "";
        return null;
    }

    public boolean isReviewExistForUser(Users user) {
        if (movie != null) {
            for (Review review : movie.getReviewCollection()) {
                if (review.getUserId().equals(user)) {
                    return true;
                }
            }
        }
        return false;
    }
}
