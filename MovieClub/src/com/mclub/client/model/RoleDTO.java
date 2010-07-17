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
import com.gmvc.client.meta.Right;

/**
 * Kullanici Hak tanitiminda kullanilan Roller
 *  
 * @author mdpinar
 * 
 */
public class RoleDTO implements IModel {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
	 * Kullanici iliskisi alani
	 * 
	 */
	private UserDTO user;
	
	/**
	 * Hak adi alani
	 * 
	 */
	private String right;
	
	/**
	 * Calistirabilir mi?
	 * 
	 */
	private Boolean hasExecute;

	/**
	 * Ekleyebilir mi?
	 * 
	 */
	private Boolean hasAddNew;
	
	/**
	 * Okuyabilir mi?
	 * 
	 */
	private Boolean hasRead;
	
	/**
	 * Guncelleyebilir mi?
	 * 
	 */
	private Boolean hasUpdate;
	
	/**
	 * Silebilir mi?
	 * 
	 */
	private Boolean hasDelete;

	public RoleDTO() {
		this(null);
	}

	public RoleDTO(Right right) {
		this.right = (right != null ? right.getName() : null);
		this.hasExecute = false;
		this.hasAddNew = false;
		this.hasRead = false;
		this.hasUpdate = false;
		this.hasDelete = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public Boolean getHasExecute() {
		return hasExecute;
	}

	public void setHasExecute(Boolean hasExecute) {
		this.hasExecute = hasExecute;
	}

	public Boolean getHasAddNew() {
		return hasAddNew;
	}

	public void setHasAddNew(Boolean hasAddNew) {
		this.hasAddNew = hasAddNew;
	}

	public Boolean getHasRead() {
		return hasRead;
	}

	public void setHasRead(Boolean hasRead) {
		this.hasRead = hasRead;
	}

	public Boolean getHasUpdate() {
		return hasUpdate;
	}

	public void setHasUpdate(Boolean hasUpdate) {
		this.hasUpdate = hasUpdate;
	}

	public Boolean getHasDelete() {
		return hasDelete;
	}

	public void setHasDelete(Boolean hasDelete) {
		this.hasDelete = hasDelete;
	}

	@Override
	public String toString() {
		return this.right.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof RoleDTO) {
			RoleDTO model = (RoleDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
