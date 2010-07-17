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
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.gmvc.client.base.IModel;

/**
 * Verilen film hareketleri baslik modeli 
 * 
 * @author mdpinar
 * 
 */
public class GivenMovieDTO implements IModel {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * Hareket tarihi alani
	 * 
	 */
	private Date transDate;

	/**
	 * Filmi verdigimiz musteri alani iliskisi
	 * 
	 */
	private CustomerDTO customer;
	
	/**
	 * Aciklama alani
	 * 
	 */
	private String description;

	/**
	 * Islemi yapan Kullanici alani
	 * 
	 */
	private UserDTO user;
	
	/**
	 * Detay tablo iliski alani
	 * 
	 */
	private List<GivenMovieDetailDTO> details;
	
	public GivenMovieDTO() {
		this.transDate = new Date();
		this.user = (UserDTO) Registry.get("user");
		this.details = new ArrayList<GivenMovieDetailDTO>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<GivenMovieDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<GivenMovieDetailDTO> details) {
		this.details = details;
	}

	public Date getTransDate() {
		return transDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof GivenMovieDTO) {
			GivenMovieDTO model = (GivenMovieDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}