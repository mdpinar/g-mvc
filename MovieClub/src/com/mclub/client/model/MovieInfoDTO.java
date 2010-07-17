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
package com.mclub.client.model;

import java.util.ArrayList;
import java.util.List;

import com.gmvc.client.base.IModel;
import com.mclub.client.enums.MediaType;

/**
 * Film Tanitim modeli
 * 
 * @author mdpinar
 * 
 */
public class MovieInfoDTO implements IModel {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
	 * Isim alani
	 * 
	 */
	private String name;
	
	/**
	 * Yonetmen alani
	 * 
	 */
	private String director;
	
	/**
	 * Oyuncular alani
	 * 
	 */
	private String actors;
	
	/**
	 * Cekim yili alani
	 * 
	 */
	private Integer prodYear;
	
	/**
	 * Poster alani
	 * 
	 */
	private String poster;
	
	/**
	 * Fragman alani
	 * 
	 */
	private String fragment;
	
	/**
	 * Urin tipi alani
	 * 
	 */
	private MediaType mediaType;
	
	/**
	 * Film gurubu alani
	 * 
	 */
	private MovieGroupDTO movieGroup;
	
	/**
	 * Filmin kopyalari
	 * 
	 */
	private List<MovieDTO> movies;

	public MovieInfoDTO() {
		this.movies = new ArrayList<MovieDTO>();
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

	public MovieGroupDTO getMovieGroup() {
		return movieGroup;
	}

	public void setMovieGroup(MovieGroupDTO movieGroup) {
		this.movieGroup = movieGroup;
	}

	public List<MovieDTO> getMovies() {
		return movies;
	}

	public void setMovies(List<MovieDTO> movies) {
		this.movies = movies;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof MovieInfoDTO) {
			MovieInfoDTO model = (MovieInfoDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
