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
package com.mclub.client.service;

import java.util.List;

import com.gmvc.client.service.ICRUDServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.IncomingMovieDTO;
import com.mclub.client.model.IncomingMovieDetailDTO;

/**
 * Gelen Filmler servisi
 * 
 * @see IncomingMovieDTO
 * @see ICRUDServiceAsync
 * 
 * @author mdpinar
 * 
 */
public interface IncomingMovieServiceAsync extends ICRUDServiceAsync<IncomingMovieDTO> {
	
	/**
	 * Musterinin daha onceden aldigi filmler listesi
	 *  
	 * @param customer
	 * @param callback
	 * 
	 */
	void getMovieList(CustomerDTO customer, AsyncCallback<List<IncomingMovieDetailDTO>> callback);

}
