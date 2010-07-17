/*
 * Copyleft 2010 Mustafa DUMLUPINAR
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 * http://www.gnu.org/licenses/gpl.html
 *
 * dumlupinar01@gmail.com
 * http://mdpinar.blogspot.com/
 *
 */
package com.mclub.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.IndexColumn;

import com.mclub.client.enums.MediaType;
import com.mclub.client.model.MovieInfoDTO;

/**
 * Aciklamalar icin client tarafinda ayni isimli 
 * sinifin javadoclarina bakiniz
 * 
 * @see MovieInfoDTO
 * 
 * @author mdpinar
 * 
 */
@Entity
@Table
public class MovieInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length=50, nullable=false, unique=true)
	private String name;

	@Column(length=30)
	private String director;

	@Column
	private String actors;

	@Column
	private Integer prodYear;

	@Column
	private String poster;

	@Column
	private String fragment;

	@Column(length=5)
	@Enumerated(EnumType.STRING)
	private MediaType mediaType;

	@ManyToOne
	private MovieGroup movieGroup;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="movieInfo_id")
	@IndexColumn(name="copyNo", base=1)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Movie> movies;
	
	public MovieInfo() {
		this.movies = new ArrayList<Movie>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public Integer getProdYear() {
		return prodYear;
	}

	public void setProdYear(Integer prodYear) {
		this.prodYear = prodYear;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getFragment() {
		return fragment;
	}

	public void setFragment(String fragment) {
		this.fragment = fragment;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public MovieGroup getMovieGroup() {
		return movieGroup;
	}

	public void setMovieGroup(MovieGroup movieGroup) {
		this.movieGroup = movieGroup;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof MovieInfo) {
			MovieInfo model = (MovieInfo) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
