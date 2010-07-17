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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mclub.client.enums.FilmStatus;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.JobDTO;
import com.mclub.client.model.LocationDTO;
import com.mclub.client.model.MovieDTO;
import com.mclub.client.model.MovieGroupDTO;
import com.mclub.client.model.UserDTO;

/**
 * @see SystemServiceAsync
 * 
 * @author mdpinar
 * 
 */
@RemoteServiceRelativePath("systemService")
public interface SystemService extends RemoteService {

	List<CustomerDTO> getCustomerList(String startWith);
	
	List<MovieDTO> getMovieList(String startWith, FilmStatus[] wantedStatus);
	
	List<JobDTO> getJobList();
	
	List<LocationDTO> getLocationList();

	List<MovieGroupDTO> getMovieGroupList();

	UserDTO login(String username, String password);
	
}
