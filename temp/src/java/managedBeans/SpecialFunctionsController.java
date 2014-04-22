/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.SpecialsFunctionsFacade;

/**
 *
 * @author Yelkin
 */
@ManagedBean(name = "specialFunctionsController")
@SessionScoped
public class SpecialFunctionsController {

    @EJB
    SpecialsFunctionsFacade facade;

    private List resultList;
    private String actor4;
    private int year4;
    private String actor5;
    private String director5;
    private String award6;
    private int year6;
    private int year7;
    private String actor10;
    private int fromYear10;
    private int toYear10;

    public List getResultList() {      
        if (resultList != null && resultList.size() > 0 && resultList.get(0) instanceof Object[])
        {
            List<String> result = new LinkedList<>();
            for (Iterator it = resultList.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                result.add(obj[0]  + "  "  + obj[1]);
            }
            return result;
        }
        return resultList;
    }

    public String getActor4() {
        return actor4;
    }

    public void setActor4(String actor4) {
        this.actor4 = actor4;
    }

    public int getYear4() {
        return year4;
    }

    public void setYear4(int year4) {
        this.year4 = year4;
    }

    public String getActor5() {
        return actor5;
    }

    public void setActor5(String actor5) {
        this.actor5 = actor5;
    }

    public String getDirector5() {
        return director5;
    }

    public void setDirector5(String director5) {
        this.director5 = director5;
    }

    public String getAward6() {
        return award6;
    }

    public void setAward6(String award6) {
        this.award6 = award6;
    }

    public int getYear6() {
        return year6;
    }

    public void setYear6(int year6) {
        this.year6 = year6;
    }

    public int getYear7() {
        return year7;
    }

    public void setYear7(int year7) {
        this.year7 = year7;
    }

    public String getActor10() {
        return actor10;
    }

    public void setActor10(String actor10) {
        this.actor10 = actor10;
    }

    public int getFromYear10() {
        return fromYear10 + 1900;
    }

    public void setFromYear10(int fromYear10) {
        this.fromYear10 = fromYear10 - 1900;
    }

    public int getToYear10() {
        return toYear10 + 1900;
    }

    public void setToYear10(int toYear10) {
        this.toYear10 = toYear10 - 1900;
    }
    
    public void setResultList(int i) {
        switch (i) {
            case 1:
                resultList = facade.getBestDirectors();
                break;
            case 2:
                resultList = facade.getMoviesCountByGenre();
                break;
            case 3:
                resultList = facade.getBestMovies();
                break;
            case 4:
                resultList = facade.getMovieForActorByYear(actor4, year4);
                break;
            case 5:
                resultList = facade.getMoviesWithActorAndDirector(director5, actor5);
                break;
            case 6:
                resultList = facade.getMoviesWithAwardInYear(year6, award6);
                break;
            case 7:
                resultList = facade.getCountriesWithoutMoviesInYear(year7);
                break;
            case 8:
                resultList = facade.getMoviesWithSeveralGenres();
                break;
            case 9:
                resultList = facade.getMoviesWithBestReview();
                break;
            case 10:
                resultList = facade.getCharactersByActorForPeriod(actor10, fromYear10, toYear10);
                break;
            default:
                throw new RuntimeException("Unknown function");
        }
    }
}
