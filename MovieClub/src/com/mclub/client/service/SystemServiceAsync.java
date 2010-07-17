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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mclub.client.enums.FilmStatus;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.JobDTO;
import com.mclub.client.model.LocationDTO;
import com.mclub.client.model.MovieDTO;
import com.mclub.client.model.MovieGroupDTO;
import com.mclub.client.model.UserDTO;

/**
 * Herhangi bir bolume ait olmayan, sistemin genelinde kullanilan servis
 * 
 * @author mdpinar
 * 
 */
public interface SystemServiceAsync {

	/**
	 * Musteriler Listesi
	 * 
	 * @param startWith ile baslayan Musteriler getirilir
	 * 
	 * @return List<Customer>
	 */
	void getCustomerList(String startWith, AsyncCallback<List<CustomerDTO>> callback);
	
	/**
	 * Filmler Listesi
	 * 
	 * @param FilmStatus[] istenen statuler
	 * @param startWith ile baslayan Filmler getirilir
	 * 
	 * @return List<Movie>
	 */
	void getMovieList(String startWith, FilmStatus[] wantedStatus, AsyncCallback<List<MovieDTO>> callback);
	
	/**
	 * Meslekler Listesi
	 * 
	 * @return List<Job>
	 */
	void getJobList(AsyncCallback<List<JobDTO>> callback);

	/**
	 * Lokasyon/Bolgeler Listesi
	 * 
	 * @return List<Location>
	 */
	void getLocationList(AsyncCallback<List<LocationDTO>> callback);

	/**
	 * Film Guruplari Listesi
	 * 
	 * @return List<MovieGroup>
	 */
	void getMovieGroupList(AsyncCallback<List<MovieGroupDTO>> callback);

	/**
	 * Kullanici giris denetimi
	 * 
	 * @return User - basarili ise kullanicinin kendisi degilse null doner
	 */
	void login(String username, String password, AsyncCallback<UserDTO> callback);
	
}
