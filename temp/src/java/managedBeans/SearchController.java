/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.Movie;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.ActorFacade;
import session.AwardFacade;
import session.CountryFacade;
import session.DirectorFacade;
import session.MovieFacade;
import session.NominationFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "searchController")
@SessionScoped
public class SearchController {

    private String searchQuerySubstring;
    private List searchResult;
    
    @EJB
    private MovieFacade movieFacade;
    @EJB
    private ActorFacade actorFacade;
    @EJB
    private DirectorFacade directorFacade;
    @EJB
    private CountryFacade countryFacade;
    @EJB
    private NominationFacade nominationFacade;
    @EJB
    private AwardFacade awardFacade;
    
    private String searchType = SEARCH_TYPE_MOVIE;
    
    private static final String SEARCH_TYPE_MOVIE = "Movie";
    private static final String SEARCH_TYPE_ACTOR = "Actor";
    private static final String SEARCH_TYPE_DIRECTOR = "Director";
    private static final String SEARCH_TYPE_COUNTRY = "Country";
    private static final String SEARCH_TYPE_NOMINATION = "Nomination";
    private static final String SEARCH_TYPE_AWARD = "Award";

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchQuerySubstring() {
        return searchQuerySubstring;
    }

    public void setSearchQuerySubstring(String searchQuerySubstring) {
        this.searchQuerySubstring = searchQuerySubstring;
    }

    public void setSelectedMovie(Movie movie) {
    }

    public List<Movie> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Movie> searchResult) {
        this.searchResult = searchResult;
    }

    public SearchController() {
    }

    public MovieFacade getFacade() {
        return movieFacade;
    }

    public void doSearch() {
        switch (searchType) {
            case SEARCH_TYPE_MOVIE:
                searchResult = movieFacade.getMovies(searchQuerySubstring);
                break;
                
            case SEARCH_TYPE_ACTOR:
                searchResult = actorFacade.getActors(searchQuerySubstring);
                break;
            case SEARCH_TYPE_DIRECTOR:
                searchResult = directorFacade.getDirectors(searchQuerySubstring);
                break;
            case SEARCH_TYPE_COUNTRY:
                searchResult = countryFacade.getCountries(searchQuerySubstring);
                break;
            case SEARCH_TYPE_NOMINATION:
                searchResult = nominationFacade.getNominations(searchQuerySubstring);
                break;
            case SEARCH_TYPE_AWARD:
                searchResult = awardFacade.getAwards(searchQuerySubstring);
                break;
        }
    }

    public int getYear(Movie movie) {
        return movie.getReleaseYear();
    }

    public String backToSearch() {
        searchQuerySubstring = "";
        searchResult = null;
        searchType = SEARCH_TYPE_MOVIE;
        return "index";
    }
}
