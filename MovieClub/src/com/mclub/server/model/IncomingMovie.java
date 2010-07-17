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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

import com.mclub.client.model.IncomingMovieDTO;

/**
 * Aciklamalar icin client tarafinda ayni isimli 
 * sinifin javadoclarina bakiniz
 * 
 * @see IncomingMovieDTO
 * 
 * @author mdpinar
 * 
 */
@Entity
@Table
public class IncomingMovie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date transDate;

	@ManyToOne
	private Customer customer;

	@Column
	private String description;
	
	@ManyToOne
	private User user;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="incomingMovie_id")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<IncomingMovieDetail> details;
	
	public IncomingMovie() {
		this.transDate = new Date();
		this.details = new ArrayList<IncomingMovieDetail>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<IncomingMovieDetail> getDetails() {
		return details;
	}

	public void setDetails(List<IncomingMovieDetail> details) {
		this.details = details;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof IncomingMovie) {
			IncomingMovie model = (IncomingMovie) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
