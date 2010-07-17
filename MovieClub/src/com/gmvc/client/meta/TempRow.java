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

import com.gmvc.client.base.IModel;
import com.gmvc.client.enums.TempRowStatus;

/**
 * MiniMVC deki model listeleri uzerindeki her bir modelin,
 * durum bilgisini tutan siniflardir  
 * 
 * @see IModel
 * 
 * @author mdpinar
 * 
 */
public class TempRow<M extends IModel> {
	
	/**
	 * Model listelerinde modele erisebilmek icin kullanilacak
	 * 
	 */
	private int index;

	/**
	 * Duzenlenen model
	 */
	private M model;

	/**
	 * Modelin durumu
	 * 
	 * @see TempRowStatus
	 */
	private TempRowStatus status;

	public TempRow(int index, M model, TempRowStatus status) {
		super();
		this.index = index;
		this.model = model;
		this.status = status;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public M getModel() {
		return model;
	}

	public void setModel(M model) {
		this.model = model;
	}

	public TempRowStatus getStatus() {
		return status;
	}

	public void setStatus(TempRowStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof TempRow) {
			TempRow<?> tempRow = (TempRow<?>) obj;
			if (tempRow.getIndex() == this.index) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
