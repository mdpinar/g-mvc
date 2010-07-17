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

import com.gmvc.client.base.IModel;

/**
 * Gelen film hareketleri detay modeli 
 * 
 * @author mdpinar
 * 
 */
public class IncomingMovieDetailDTO implements IModel {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * Hareket tablosundaki iliskili kaydin id alani (dolayli iliski olacak)
	 * 
	 */
	private Long transId;

	/**
	 * Baslik tablo iliskisi alani
	 * 
	 */
	private IncomingMovieDTO incomingMovie;

	/**
	 * Film alani
	 * 
	 */
	private MovieDTO movie;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public IncomingMovieDTO getIncomingMovie() {
		return incomingMovie;
	}

	public void setIncomingMovie(IncomingMovieDTO incomingMovie) {
		this.incomingMovie = incomingMovie;
	}

	public MovieDTO getMovie() {
		return movie;
	}

	public void setMovie(MovieDTO movie) {
		this.movie = movie;
	}

	public String getMovieName() {
		if (movie != null) {
			return movie.getSuggestText();
		} else {
			return null;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof IncomingMovieDetailDTO) {
			IncomingMovieDetailDTO model = (IncomingMovieDetailDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
