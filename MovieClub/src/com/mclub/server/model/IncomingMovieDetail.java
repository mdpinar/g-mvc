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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mclub.client.model.IncomingMovieDetailDTO;

/**
 * Aciklamalar icin client tarafinda ayni isimli 
 * sinifin javadoclarina bakiniz
 * 
 * @see IncomingMovieDetailDTO
 * 
 * @author mdpinar
 * 
 */
@Entity
@Table
public class IncomingMovieDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column
	private Long transId;

	@ManyToOne
	private IncomingMovie incomingMovie;

	@ManyToOne
	private Movie movie;
	
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

	public IncomingMovie getIncomingMovie() {
		return incomingMovie;
	}

	public void setIncomingMovie(IncomingMovie incomingMovie) {
		this.incomingMovie = incomingMovie;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof IncomingMovieDetail) {
			IncomingMovieDetail model = (IncomingMovieDetail) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
