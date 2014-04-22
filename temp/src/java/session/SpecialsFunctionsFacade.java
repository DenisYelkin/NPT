/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yelkin
 */
@Stateless
public class SpecialsFunctionsFacade {

    @PersistenceContext(unitName = "tempPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public SpecialsFunctionsFacade() {
    }

    public List<String> getBestDirectors() {
        return em.createNativeQuery("SELECT NAME FROM DIRECTOR"
                + " WHERE ID IN ("
                + "  SELECT DIRECTOR_ID FROM MOVIEDIRECTORCONNECTOR"
                + "  WHERE MOVIE_ID IN ("
                + "    SELECT ID FROM MOVIE"
                + "    WHERE RATING IN (SELECT MAX(RATING) FROM MOVIE)))").getResultList();
    }

    public List<Object[]> getMoviesCountByGenre() {
        return em.createNativeQuery("SELECT GENRE_ID, COUNT(*) AS MOVIECOUNT FROM MOVIEGENRECONNECTOR "
                + "GROUP BY GENRE_ID")
                .getResultList();
    }

    public List<String> getBestMovies() {
        return em.createNativeQuery("SELECT NAME FROM MOVIE "
                + "WHERE RATING IN ("
                + "  SELECT MAX(RATING) FROM MOVIE)")
                .getResultList();
    }

    public List<String> getMovieForActorByYear(String actor, int year) {
        return em.createNativeQuery("SELECT NAME FROM MOVIE "
                + "WHERE ID IN ( "
                + "  SELECT MOVIE_ID FROM CHARACTER "
                + "  WHERE ACTOR_ID IN ( "
                + "    SELECT ID FROM ACTOR "
                + "    WHERE LOWER(NAME) = LOWER(?))) "
                + "  AND MOVIE.RELEASE_DATE > ADD_MONTHS(SYSDATE, -?)")
                .setParameter(1, actor)
                .setParameter(2, year * 12)
                .getResultList();
    }

    public List<String> getMoviesWithActorAndDirector(String director, String actor) {
        return em.createNativeQuery("SELECT NAME FROM MOVIE\n"
                + "WHERE ID IN (\n"
                + "  SELECT MOVIE_ID FROM CHARACTER\n"
                + "  WHERE ACTOR_ID IN (\n"
                + "    SELECT ID FROM ACTOR\n"
                + "    WHERE LOWER(NAME) = LOWER(?)))\n"
                + "  AND ID IN (\n"
                + "  SELECT MOVIE_ID FROM MOVIEDIRECTORCONNECTOR\n"
                + "  WHERE DIRECTOR_ID IN (\n"
                + "    SELECT ID FROM DIRECTOR\n"
                + "    WHERE LOWER(NAME) = LOWER(?)))")
                .setParameter(1, actor)
                .setParameter(2, director)
                .getResultList();
    }

    public List<String> getMoviesWithAwardInYear(int year, String award) {
        return em.createNativeQuery("SELECT NAME FROM MOVIE\n"
                + "WHERE ID IN (\n"
                + "  SELECT MOVIE_ID FROM MOVIENOMINATIONCONNECTOR\n"
                + "  WHERE NOMINATION_ID IN (\n"
                + "    SELECT ID FROM NOMINATION\n"
                + "    WHERE AWARD_ID IN (\n"
                + "      SELECT ID FROM AWARD\n"
                + "      WHERE YEAR = ?\n"
                + "      AND LOWER(NAME) = LOWER(?)))\n"
                + "    AND LOWER(STATUS) = LOWER('лауреат'))")
                .setParameter(1, year)
                .setParameter(2, award)
                .getResultList();
    }

    public List<String> getCountriesWithoutMoviesInYear(int year) {
        return em.createNativeQuery("SELECT NAME FROM COUNTRY\n"
                + "WHERE ID NOT IN (\n"
                + "  SELECT COUNTRY_ID FROM MOVIECOUNTRYCONNECTOR\n"
                + "  WHERE MOVIE_ID IN (\n"
                + "    SELECT ID FROM MOVIE\n"
                + "    WHERE MOVIE.RELEASE_DATE BETWEEN ? AND ?))")
                .setParameter(2, new Date(year, 0, 1))
                .setParameter(3, new Date(year, 11, 31))
                .getResultList();
    }

    public List<String> getMoviesWithSeveralGenres() {
        return em.createNativeQuery("SELECT NAME FROM MOVIE\n"
                + "WHERE ID IN (\n"
                + "  SELECT MOVIE_ID FROM MOVIEGENRECONNECTOR\n"
                + "  GROUP BY MOVIE_ID\n"
                + "  HAVING COUNT(*) > 1)")
                .getResultList();
    }

    public List<String> getMoviesWithBestReview() {
        return em.createNativeQuery("SELECT NAME FROM MOVIE\n"
                + "WHERE (SELECT COUNT(*) FROM REVIEW\n"
                + "  WHERE MOVIE_ID = MOVIE.ID\n"
                + "  AND RATING = 'положительный') > (SELECT COUNT(*) FROM REVIEW\n"
                + "  WHERE MOVIE_ID = MOVIE.ID\n"
                + "  AND RATING <> 'положительный')")
                .getResultList();
    }

    public List<String> getCharactersByActorForPeriod(String actor, int fromYear, int toYear) {
        return em.createNativeQuery("SELECT CHAR_NAME FROM CHARACTER\n"
                + "WHERE ACTOR_ID IN (\n"
                + "  SELECT ID FROM ACTOR\n"
                + "  WHERE LOWER(NAME) = LOWER(?))"
                + "  AND MOVIE_ID IN (\n"
                + "    SELECT ID FROM MOVIE\n"
                + "    WHERE RELEASE_DATE BETWEEN ? AND ?)")
                .setParameter(1, actor)
                .setParameter(2, new Date(fromYear, 0, 1))
                .setParameter(3, new Date(toYear, 11, 31))
                .getResultList();
    }
}
