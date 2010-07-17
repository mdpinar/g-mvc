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
package com.mclub.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.gmvc.server.util.Hash;
import com.gmvc.server.util.DBUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mclub.client.enums.FilmStatus;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.JobDTO;
import com.mclub.client.model.LocationDTO;
import com.mclub.client.model.MovieDTO;
import com.mclub.client.model.MovieGroupDTO;
import com.mclub.client.model.UserDTO;
import com.mclub.client.service.SystemService;
import com.mclub.server.model.Customer;
import com.mclub.server.model.Job;
import com.mclub.server.model.Location;
import com.mclub.server.model.Movie;
import com.mclub.server.model.MovieGroup;
import com.mclub.server.model.User;

/**
 * Herhangi bir bolume ait olmayan, sistemin genelinde kullanilan servis
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
public class SystemServiceImpl extends RemoteServiceServlet implements SystemService {

	/**
	 * Musteri Listesi
	 * 
	 * @return List<Customer>
	 */
	@Override
	public List<CustomerDTO> getCustomerList(String startWith) {
		Session session = DBUtils.getSession();
		session.beginTransaction();
		
		List<Customer> customerList = session.createQuery("from Customer c " +
														"where c.name like :startWith " +
														"order by c.name")
													.setParameter("startWith", "%" + startWith + "%")
												.setFetchSize(2)
											.list();

		List<CustomerDTO> result = new ArrayList<CustomerDTO>(customerList.size());
		for (Customer model: customerList) {
			result.add(DBUtils.getMapper().map(model, CustomerDTO.class));
		}

		session.getTransaction().commit();
		
		return result;
	}

	/**
	 * Film Listesi
	 * 
	 * @return List<Movie>
	 */
	@Override
	public List<MovieDTO> getMovieList(String startWith, FilmStatus[] wantedStatus) {
		Session session = DBUtils.getSession();
		session.beginTransaction();

		if (wantedStatus == null) wantedStatus = FilmStatus.values();

		List<Movie> movieList = 
			session.createQuery("from Movie m " +
								"where m.lastStatus IN (:lastStatus) " +
								"  and m.name like :startWith " +
								"order by m.name")
							.setParameterList("lastStatus", wantedStatus)
							.setParameter("startWith", "%" + startWith + "%")
						.setMaxResults(5)
					.list();
			
		List<MovieDTO> result = new ArrayList<MovieDTO>(movieList.size());
		for (Movie model: movieList) {
			result.add(DBUtils.getMapper().map(model, MovieDTO.class));
		}

		session.getTransaction().commit();
		
		return result;
	}

	/**
	 * Meslekler Listesi
	 * 
	 * @return List<Job>
	 */
	@Override
	public List<JobDTO> getJobList() {
		Session session = DBUtils.getSession();
		session.beginTransaction();

		List<Job> jobList = session.createQuery("from Job j").list();

		List<JobDTO> result = new ArrayList<JobDTO>(jobList.size());
		for (Job model: jobList) {
			result.add(DBUtils.getMapper().map(model, JobDTO.class));
		}

		session.getTransaction().commit();
		return result;
	}

	/**
	 * Lokasyon/Bolgeler Listesi
	 * 
	 * @return List<Location>
	 */
	@Override
	public List<LocationDTO> getLocationList() {
		Session session = DBUtils.getSession();
		session.beginTransaction();
		
		List<Location> locationList = session.createQuery("from Location l").list();

		List<LocationDTO> result = new ArrayList<LocationDTO>(locationList.size());
		for (Location model: locationList) {
			result.add(DBUtils.getMapper().map(model, LocationDTO.class));
		}

		session.getTransaction().commit();
		return result;
	}

	/**
	 * Film Guruplari Listesi
	 * 
	 * @return List<MovieGroup>
	 */
	@Override
	public List<MovieGroupDTO> getMovieGroupList() {
		Session session = DBUtils.getSession();
		session.beginTransaction();

		List<MovieGroup> movieGroupList = session.createQuery("from MovieGroup mg").list();

		List<MovieGroupDTO> result = new ArrayList<MovieGroupDTO>(movieGroupList.size());
		for (MovieGroup model : movieGroupList) {
			result.add(DBUtils.getMapper().map(model, MovieGroupDTO.class));
		}

		session.getTransaction().commit();
		return result;
	}

	/**
	 * Kullanici giris denetimi
	 * 
	 * @return User - basarili ise kullanicinin kendisi degilse null doner
	 */
	@Override
	public UserDTO login(String username, String password) {
		UserDTO result = null;

		if (username != null) {
			Session session = DBUtils.getSession();
			session.beginTransaction();

			User user = (User) session.createQuery("from User u " 
											+ "where u.name = :name "
											+ "  and u.password = :password")
									.setParameter("name", username)
									.setParameter("password", Hash.md5(password))
								.uniqueResult();

			if (user != null) {
				result = DBUtils.getMapper().map(user, UserDTO.class);
			}

			session.getTransaction().commit();
		}

		return result;
	}

}
