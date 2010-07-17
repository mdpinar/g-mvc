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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mclub.client.model.RoleDTO;

/**
 * Aciklamalar icin client tarafinda ayni isimli 
 * sinifin javadoclarina bakiniz
 * 
 * @see RoleDTO
 * 
 * @author mdpinar
 * 
 */
@Entity
@Table
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;
	
	@Column(name="userRight", length=30, nullable=false)
	private String right;

	@Column
	private Boolean hasExecute;

	@Column
	private Boolean hasAddNew;

	@Column
	private Boolean hasRead;

	@Column
	private Boolean hasUpdate;

	@Column
	private Boolean hasDelete;
	
	public Role() {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
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
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Role) {
			Role model = (Role) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
