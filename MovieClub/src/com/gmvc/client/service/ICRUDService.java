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
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * @see ICRUDServiceAsync
 * 
 * @author mdpinar
 * 
 */
public interface ICRUDService<M extends IModel> extends RemoteService {

	RetVal save(M model, List<EffectedDetail> effectedDetails);

	RetVal remove(M model);

	List<M> getModelList(FilterItem masterFilter, FilterItem filter);

}
