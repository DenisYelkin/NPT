DROP TRIGGER onUserDelete;
drop trigger onActorDelete;
drop trigger onMovieDelete;
drop trigger rate_trigger;
drop trigger onDirectorDelete;
drop trigger onCountryDelete;
drop trigger onNominationDelete;
drop trigger onAwardDelete;


drop sequence unique_id;
drop table MovieDirectorConnector;
drop table MovieGenreConnector;
drop table MovieCountryConnector;
drop table MovieNominationConnector;
drop table director;
drop table genre;
drop table rate;
drop table character;
drop table review;
drop table actor;
drop table users;
drop table country;
drop table nomination;
drop table award;
drop table movie;

CREATE TABLE movie
(
	id NUMBER(19) PRIMARY KEY,
	name VARCHAR2(100) NOT NULL,
	release_date DATE,
	restriction NUMBER(2) DEFAULT 0,
	duration NUMBER(10),
	world_box NUMBER(20),
	budget NUMBER(20),
	site VARCHAR2(1000),
	description LONG,
	poster VARCHAR2(1000),
	rating NUMBER(5,3) DEFAULT 0,
	voice_count NUMBER(7) DEFAULT 0
);

CREATE TABLE country
(
	id NUMBER(19) PRIMARY KEY,
	name VARCHAR2(100) 
);

CREATE TABLE director
(
	id NUMBER(19) PRIMARY KEY,
	name VARCHAR2(100) NOT NULL,
	birth_date DATE,
	photo VARCHAR2(1000),
	birth_country NUMBER(19), 
	CONSTRAINT fk_country_director FOREIGN KEY (birth_country) REFERENCES country(id)
);

CREATE TABLE actor
(
	id NUMBER(19) PRIMARY KEY,
	name VARCHAR2(100) NOT NULL,
	birth_date DATE,
	photo VARCHAR2(1000),
	birth_country NUMBER(19), 
	CONSTRAINT fk_country_actor FOREIGN KEY (birth_country) REFERENCES country(id)
);

CREATE TABLE genre
(
	name VARCHAR2(30) PRIMARY KEY,
	description LONG
);

CREATE TABLE award
(
	id NUMBER(19) PRIMARY KEY,
	name VARCHAR2(50) NOT NULL,
	year NUMBER(4),
	location VARCHAR2(1000)
);

CREATE TABLE nomination
(
	id NUMBER(19) PRIMARY KEY,
	award_id NUMBER(19),
	name VARCHAR2(50) NOT NULL,
	CONSTRAINT fk_award FOREIGN KEY (award_id) REFERENCES award(id)
);

CREATE TABLE users
(
	id NUMBER(19) PRIMARY KEY,
	login VARCHAR2(100) NOT NULL,
	password VARCHAR2(100) NOT NULL,
	birth_date DATE,
	registration_date DATE DEFAULT SYSDATE,
	name VARCHAR2(200)
);

CREATE TABLE rate
(
	movie_id NUMBER(19),
	user_id NUMBER(19),
	rating NUMBER(2) NOT NULL,
	CONSTRAINT fk_movie_rate FOREIGN KEY (movie_id) REFERENCES movie(id),
	CONSTRAINT fk_user_rate FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE character
(
	actor_id NUMBER(19),
	movie_id NUMBER(19),
	char_name VARCHAR(50) NOT NULL,
	description LONG,
	CONSTRAINT fk_actor_char FOREIGN KEY (actor_id) REFERENCES actor(id),
	CONSTRAINT fk_movie_char FOREIGN KEY (movie_id) REFERENCES movie(id)
);

CREATE TABLE review
(
	review_id NUMBER(19) PRIMARY KEY,
	movie_id NUMBER(19),
	user_id NUMBER(19),
	text LONG,
	review_date DATE,
	rating VARCHAR2(40),
	CONSTRAINT fk_movie_review FOREIGN KEY (movie_id) REFERENCES movie(id),
	CONSTRAINT fk_user_review FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE MovieNominationConnector
(
	movie_id NUMBER(19),
	nomination_id NUMBER(19),
	status VARCHAR2(20) NOT NULL,
	CONSTRAINT fk_movie_MNConnector FOREIGN KEY (movie_id) REFERENCES movie(id),
	CONSTRAINT fk_nomination_MNConnector FOREIGN KEY (nomination_id) REFERENCES nomination(id)
);

CREATE TABLE MovieDirectorConnector
(
	movie_id NUMBER(19),
	director_id NUMBER(19),
	CONSTRAINT fk_movie_MDConnector FOREIGN KEY (movie_id) REFERENCES movie(id),
	CONSTRAINT fk_director_MDConnector FOREIGN KEY (director_id) REFERENCES director(id)
);

CREATE TABLE MovieGenreConnector
(
	movie_id NUMBER(19),
	genre_id VARCHAR2(30),
	CONSTRAINT fk_movie_MGConnector FOREIGN KEY (movie_id) REFERENCES movie(id),
	CONSTRAINT fk_genre_MGConnector FOREIGN KEY (genre_id) REFERENCES genre(name)
);

CREATE TABLE MovieCountryConnector
(
	movie_id NUMBER(19),
	country_id NUMBER(19),
	CONSTRAINT fk_movie_MCConnector FOREIGN KEY (movie_id) REFERENCES movie(id),
	CONSTRAINT fk_country_MCConnector FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE SEQUENCE unique_id
	INCREMENT BY 1
	START WITH 150
	MINVALUE 0
	NOMAXVALUE
	NOCYCLE
	NOCACHE;

CREATE OR REPLACE TRIGGER rate_trigger
AFTER INSERT OR DELETE OR UPDATE
ON rate
FOR EACH ROW
DECLARE
	current_voice_count movie.voice_count%TYPE;
	current_rating movie.rating%TYPE;
BEGIN
	IF (INSERTING) THEN
		SELECT voice_count, rating INTO current_voice_count, current_rating FROM movie
			WHERE id = :NEW.movie_id;
		UPDATE movie
			SET rating = (current_rating * current_voice_count + :NEW.rating) / (current_voice_count + 1)
			WHERE id = :NEW.movie_id;
		UPDATE movie
			SET voice_count = current_voice_count + 1
			WHERE id = :NEW.movie_id;
	ELSIF (UPDATING) THEN
		SELECT voice_count, rating INTO current_voice_count, current_rating FROM movie
			WHERE id = :NEW.movie_id;
		UPDATE movie
			SET rating = (current_rating * current_voice_count + :NEW.rating - :OLD.rating) / current_voice_count
			WHERE id = :NEW.movie_id;
	ELSE
		SELECT voice_count, rating INTO current_voice_count, current_rating FROM movie
			WHERE id = :OLD.movie_id;
			IF (current_voice_count <> 1) THEN
				UPDATE movie
				SET rating = (current_rating * current_voice_count - :OLD.rating) / (current_voice_count - 1)
				WHERE id = :OLD.movie_id;
			ELSE
				UPDATE movie
				SET rating = 0
				WHERE id = :OLD.movie_id;
			END IF;
		UPDATE movie
			SET voice_count = current_voice_count - 1
			WHERE id = :OLD.movie_id;
	END IF;
END;
/

CREATE OR REPLACE TRIGGER onMovieDelete
AFTER DELETE
ON MOVIE
FOR EACH ROW
DECLARE
BEGIN
  DELETE FROM MOVIEDIRECTORCONNECTOR
  WHERE MOVIE_ID = :OLD.id;
  DELETE FROM MOVIEGENRECONNECTOR
  WHERE MOVIE_ID = :OLD.id;
  DELETE FROM MOVIECOUNTRYCONNECTOR
  WHERE MOVIE_ID = :OLD.id;
  DELETE FROM MOVIENOMINATIONCONNECTOR
  WHERE MOVIE_ID = :OLD.id;
  DELETE FROM CHARACTER
  WHERE MOVIE_ID = :OLD.id;
  DELETE FROM REVIEW
  WHERE MOVIE_ID = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER onActorDelete
AFTER DELETE
ON ACTOR
FOR EACH ROW
DECLARE
BEGIN
  DELETE FROM CHARACTER
  WHERE ACTOR_ID = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER onUserDelete
AFTER DELETE
ON USERS
FOR EACH ROW
DECLARE
BEGIN
  DELETE FROM RATE
  WHERE USER_ID = :OLD.id;
  DELETE FROM REVIEW
  WHERE USER_ID = :OLD.ID;
END;
/

CREATE OR REPLACE TRIGGER onDirectorDelete
AFTER DELETE
ON DIRECTOR
FOR EACH ROW
BEGIN
	DELETE FROM MOVIEDIRECTORCONNECTOR
	WHERE DIRECTOR_ID = :OLD.ID
END;
/

CREATE OR REPLACE TRIGGER onCountryDelete
AFTER DELETE
ON COUNTRY
FOR EACH ROW
BEGIN
	DELETE FROM MOVIECOUNTRYCONNECTOR
	WHERE COUNTRY_ID = :OLD.ID;
	DELETE FROM ACTOR
	WHERE BIRTH_COUNTRY = :OLD.ID;
	DELETE FROM DIRECTOR
	WHERE BIRTH_COUNTRY = :OLD.ID;
END;
/

CREATE OR REPLACE TRIGGER onNominationDelete
AFTER DELETE
ON NOMINATION
FOR EACH ROW
BEGIN
	DELETE FROM MOVIENOMINATIONCONNECTOR
	WHERE NOMINATION_ID = :OLD.ID;
END;
/

CREATE OR REPLACE TRIGGER onAwardDelete
AFTER DELETE
ON AWARD
FOR EACH ROW
BEGIN
	DELETE FROM nomination
	WHERE award_id = :OLD.ID;
END;
/