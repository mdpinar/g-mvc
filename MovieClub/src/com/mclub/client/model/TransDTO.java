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
import com.mclub.client.enums.ActionType;
import com.mclub.client.enums.FilmStatus;

/**
 * Film hareketleri modeli 
 * 
 * @author mdpinar
 */
public class TransDTO implements IModel {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
	 * Hareketlerdeki id alani. 
	 * Bu bilginin yansisi, herbir hareket tablosunda transId olarak gecer
	 * 
	 */
	private Long actionId;
	
	/**
	 * Hareket tipi alani
	 * 
	 */
	private ActionType type;
	
	/**
	 * Musteri iliskisi alani
	 * 
	 */
	private CustomerDTO customer;
	
	/**
	 * Film iliskisi alani
	 * 
	 */
	private MovieDTO movie;

	/**
	 * Film durumu alani
	 * 
	 */
	private FilmStatus status;
	
	/**
	 * Kullanici iliskisi alani
	 * 
	 */
	private UserDTO user;
	
	/**
	 * Hareket tarihi
	 */
	private Date transDate;

	public TransDTO() {
		this.transDate = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof TransDTO) {
			TransDTO model = (TransDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
