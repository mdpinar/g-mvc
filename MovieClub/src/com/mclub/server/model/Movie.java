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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mclub.client.enums.FilmStatus;
import com.mclub.client.model.MovieDTO;

/**
 * Aciklamalar icin client tarafinda ayni isimli 
 * sinifin javadoclarina bakiniz
 * 
 * @see MovieDTO
 * 
 * @author mdpinar
 * 
 */
@Entity
@Table
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private MovieInfo movieInfo;

	@Column
	private Integer copyNo;

	@Column(length=50)
	private String name;
	
	@Column
	private Long lastTransId;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTransDate;

	@ManyToOne
	private Customer lastCustomer;
	
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private FilmStatus lastStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MovieInfo getMovieInfo() {
		return movieInfo;
	}

	public void setMovieInfo(MovieInfo movieInfo) {
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

	public Customer getLastCustomer() {
		return lastCustomer;
	}

	public void setLastCustomer(Customer lastCustomer) {
		this.lastCustomer = lastCustomer;
	}

	public FilmStatus getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(FilmStatus lastStatus) {
		this.lastStatus = lastStatus;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Movie) {
			Movie model = (Movie) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
