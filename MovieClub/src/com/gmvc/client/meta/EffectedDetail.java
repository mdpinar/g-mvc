/*
 * Copyleft 2010 Mustafa DUMLUPINAR
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 * http://www.gnu.org/licenses/lgpl.html
 *
 * dumlupinar01@gmail.com
 * http://mdpinar.blogspot.com/
 *
 */
package com.gmvc.client.meta;

import java.io.Serializable;
import java.util.List;

import com.gmvc.client.base.IModel;

/**
 * Master - Detail yapisindaki formlarda;
 * Master in altindaki Detail turleri icin "Degisiklikler Liste" sini tutar
 * 
 * <p/>
 * CRUD servislerindeki save metodu ile server tarafina bildirilir
 * 
 * @author mdpinar
 * 
 */
public class EffectedDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Master uzerinde bulunabilecek diger detay modellerinden ayrim icin kullanilacak
	 */
	private String modelName;
	
	/**
	 * Yeni eklenen detaylarin listesi
	 */
	private List<IModel> addedList;
	
	/**
	 * Duzenlenen detaylarin listesi
	 */
	private List<IModel> updatedList;
	
	/**
	 * Silinen detaylarin listesi
	 */
	private List<IModel> deletedList;

	public EffectedDetail() {
		this("");
	}
	
	public EffectedDetail(String modelName) {
		this.modelName = modelName;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List<IModel> getAddedList() {
		return addedList;
	}

	public void setAddedList(List<IModel> addedList) {
		this.addedList = addedList;
	}

	public List<IModel> getUpdatedList() {
		return updatedList;
	}

	public void setUpdatedList(List<IModel> updatedList) {
		this.updatedList = updatedList;
	}

	public List<IModel> getDeletedList() {
		return deletedList;
	}

	public void setDeletedList(List<IModel> deletedList) {
		this.deletedList = deletedList;
	}

}
