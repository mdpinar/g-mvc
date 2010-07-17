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
import java.util.List;

import com.gmvc.client.base.IModel;

/**
 * Kullanici tanitim modeli
 * 
 * @author mdpinar
 * 
 */
public class UserDTO implements IModel {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
	 * Isim alani
	 * 
	 */
	private String name;
	
	/**
	 * Sifre alani
	 * 
	 */
	private String password;
	
	/**
	 * Admin mi
	 * 
	 */
	private Boolean isAdmin;
	
	/**
	 * Sahip oldugu roller ve rollere ait haklari
	 * 
	 */
	private List<RoleDTO> roles;

	public UserDTO() {
		this.isAdmin = Boolean.FALSE;
		this.roles = new ArrayList<RoleDTO>();
	}
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof UserDTO) {
			UserDTO model = (UserDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
