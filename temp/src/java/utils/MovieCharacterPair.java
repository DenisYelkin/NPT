/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import entities.Movie;

/**
 *
 * @author Yelkin
 */
public class MovieCharacterPair {

        private Movie movie;
        private entities.Character character;

        public MovieCharacterPair(Movie movie, entities.Character character) {
            this.movie = movie;
            this.character = character;
        }

        public Movie getMovie() {
            return movie;
        }

        public void setMovie(Movie movie) {
            this.movie = movie;
        }

        public entities.Character getCharacter() {
            return character;
        }

        public void setCharacter(entities.Character character) {
            this.character = character;
        }
    }
