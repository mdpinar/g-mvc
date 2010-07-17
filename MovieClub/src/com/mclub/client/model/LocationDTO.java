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
 * Lokasyon/Bolge tanitim modeli
 * 
 * @author mdpinar
 * 
 */
public class LocationDTO implements IModel {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
	 * Isim alani
	 */
	private String name;

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

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof LocationDTO) {
			LocationDTO model = (LocationDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
