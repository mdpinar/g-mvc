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

import com.gmvc.client.service.ICRUDService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.IncomingMovieDTO;
import com.mclub.client.model.IncomingMovieDetailDTO;

/**
 * @see IncomingMovieServiceAsync
 * 
 * @author mdpinar
 * 
 */
@RemoteServiceRelativePath("incomingMovieService")
public interface IncomingMovieService extends ICRUDService<IncomingMovieDTO> {
	
	List<IncomingMovieDetailDTO> getMovieList(CustomerDTO customer);

}
