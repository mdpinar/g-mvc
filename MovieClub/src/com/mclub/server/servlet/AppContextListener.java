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
package com.mclub.server.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gmvc.server.util.DBUtils;
import com.mclub.server.model.ChangeStatus;
import com.mclub.server.model.ChangeStatusDetail;
import com.mclub.server.model.Customer;
import com.mclub.server.model.GivenMovie;
import com.mclub.server.model.GivenMovieDetail;
import com.mclub.server.model.IncomingMovie;
import com.mclub.server.model.IncomingMovieDetail;
import com.mclub.server.model.Job;
import com.mclub.server.model.Location;
import com.mclub.server.model.Movie;
import com.mclub.server.model.MovieGroup;
import com.mclub.server.model.MovieInfo;
import com.mclub.server.model.Role;
import com.mclub.server.model.Trans;
import com.mclub.server.model.User;

/**
 * Uygulama sunucusunun bu proje ile ilgili baglam olaylarini dinler
 * 
 * <p/>
 * Uygulama, sunucuda ayaga kalkinca ilk etapta yapilacak islemler ile
 * durdurulunca yapilacak islemler burada yapilacaktir.
 * 
 * @author mdpinar
 * 
 */
public class AppContextListener implements ServletContextListener {

	/**
	 * Veritabani baglantisi ve ORM islemleri icin cagri yapilir
	 * 
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Class<?>[] classes = {
				Trans.class,
				GivenMovie.class,
				GivenMovieDetail.class,
				IncomingMovie.class,
				IncomingMovieDetail.class,
				ChangeStatus.class,
				ChangeStatusDetail.class,
				Customer.class,
				MovieInfo.class,
				Movie.class,
				MovieGroup.class,
				Location.class,
				Job.class,
				User.class,
				Role.class
		};
		DBUtils.init(classes);
	}
	
	/**
	 * Veritabani baglantisi kapatilir
	 * 
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		DBUtils.closeSessionFactory();
	}

}
