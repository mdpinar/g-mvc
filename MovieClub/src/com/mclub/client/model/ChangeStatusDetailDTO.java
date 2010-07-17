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
import com.mclub.client.enums.FilmStatus;

/**
 * Durumu degistirilen film hareketleri detay modeli 
 * 
 * @author mdpinar
 * 
 */
public class ChangeStatusDetailDTO implements IModel {

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
	private ChangeStatusDTO changeStatusMovie;

	/**
	 * Film alani
	 * 
	 */
	private MovieDTO movie;

	/**
	 * Film durumu alani
	 * 
	 */
	private FilmStatus status;
	
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

	public ChangeStatusDTO getChangeStatusMovie() {
		return changeStatusMovie;
	}

	public void setChangeStatusMovie(ChangeStatusDTO changeStatusMovie) {
		this.changeStatusMovie = changeStatusMovie;
	}

	public MovieDTO getMovie() {
		return movie;
	}

	public void setMovie(MovieDTO movie) {
		this.movie = movie;
	}

	public FilmStatus getStatus() {
		return status;
	}

	public void setStatus(FilmStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof ChangeStatusDetailDTO) {
			ChangeStatusDetailDTO model = (ChangeStatusDetailDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
