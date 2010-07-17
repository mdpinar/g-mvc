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

import java.util.Date;

import com.gmvc.client.base.IModel;
import com.mclub.client.enums.FilmStatus;

/**
 * Film Kopyasi modeli
 * 
 * name sahasi movieInfo tablosundaki alan ile birebir aynidir, 
 * sorgulardan daha hizli sonuc almak icin burda da tutuldu
 * 
 * </p>
 * performans mi? normalizasyon mu?
 * 
 * @author mdpinar
 * 
 */
public class MovieDTO implements IModel {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
	 * MovieInfo tablosu iliski alani
	 */
	private MovieInfoDTO movieInfo;
	
	/**
	 * Kopya numarasi, ayni filmin birden fazla kopyasi kullanilabilir (yasal olsun ama)
	 * 
	 */
	private Integer copyNo;
	
	/**
	 * Isim alani
	 * 
	 */
	private String name;
	
	/**
	 * Son hareket alani iliskisi (dolayli iliski olacak)
	 * 
	 */
	private Long lastTransId;

	/**
	 * Son hareket tarihi
	 * 
	 */
	private Date lastTransDate;
	
	/**
	 * Son kullanan musteri iliski alani
	 * 
	 */
	private CustomerDTO lastCustomer;
	
	/**
	 * Son durumu
	 * 
	 */
	private FilmStatus lastStatus;
	
	public MovieDTO() {
		this.lastStatus = FilmStatus.IN_STORE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MovieInfoDTO getMovieInfo() {
		return movieInfo;
	}

	public void setMovieInfo(MovieInfoDTO movieInfo) {
		this.movieInfo = movieInfo;
	}

	public Integer getCopyNo() {
		return copyNo;
	}

	public void setCopyNo(Integer copyNo) {
		this.copyNo = copyNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLastTransId() {
		return lastTransId;
	}

	public void setLastTransId(Long lastTransId) {
		this.lastTransId = lastTransId;
	}

	public Date getLastTransDate() {
		return lastTransDate;
	}

	public void setLastTransDate(Date lastTransDate) {
		this.lastTransDate = lastTransDate;
	}

	public CustomerDTO getLastCustomer() {
		return lastCustomer;
	}

	public void setLastCustomer(CustomerDTO lastCustomer) {
		this.lastCustomer = lastCustomer;
	}

	public FilmStatus getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(FilmStatus lastStatus) {
		this.lastStatus = lastStatus;
	}

	public String getSuggestText() {
		return this.name + " - " + this.copyNo;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof MovieDTO) {
			MovieDTO model = (MovieDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
