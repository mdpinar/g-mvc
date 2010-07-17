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

import java.io.Serializable;

/**
 * Her turlu adres bilgisi icin embedded ozellikte adres yapisi.
 * 
 * @see CustomerDTO#getHomeAddress()
 * @see CustomerDTO#getWorkAddress()
 * 
 * @author mdpinar
 * 
 */
public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Telefon alani
	 * 
	 */
	private String phone;

	/**
	 * Adres alani
	 * 
	 */
	private String address;

	/**
	 * Bolge alani
	 * 
	 */
	private LocationDTO location;

	public AddressDTO() {
		this.phone = "";
		this.address = "";
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
