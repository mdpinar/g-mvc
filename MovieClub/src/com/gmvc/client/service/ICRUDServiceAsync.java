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
package com.gmvc.client.service;

import java.util.List;

import com.gmvc.client.base.IModel;
import com.gmvc.client.meta.EffectedDetail;
import com.gmvc.client.meta.FilterItem;
import com.gmvc.client.meta.RetVal;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Client tarafi icin;
 * 
 * <p/>
 * Tum tanitim formlari icin CRUD (Create-Read-Update-Delete) islemlerini yapan ortak servis.
 * 
 * <p/>
 * Tanitim formlarinin basvurduklari servisler bu sinifi extends edecekler
 * 
 * </p>
 * TODO: Metod donus degerleri String den RetVal e cekilecek
 * 
 * @author mdpinar
 * 
 */
public interface ICRUDServiceAsync<M extends IModel> {

	/**
	 * Kayit islemi (insert ve update ortak)
	 * 
	 * @param model
	 * 
	 * @return RetVal sonuc nesnesi
	 */
	void save(M model, List<EffectedDetail> effectedDetails, AsyncCallback<RetVal> callback);

	/**
	 * Silme islemi
	 * 
	 * @param model
	 * 
	 * @return RetVal sonuc nesnesi
	 */
	void remove(M model, AsyncCallback<RetVal> callback);

	/**
	 * Refresh islemlerinde kullanilan liste yenileme islemi
	 * 
	 * @param filter
	 * @param callback
	 * 
	 */
	void getModelList(FilterItem masterFilter, FilterItem filter, AsyncCallback<List<M>> callback);

}
