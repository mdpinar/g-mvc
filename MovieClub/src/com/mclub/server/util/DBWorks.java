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
package com.mclub.server.util;

import java.util.List;

import org.hibernate.Session;

import com.mclub.client.enums.FilmStatus;
import com.mclub.server.model.Movie;
import com.mclub.server.model.Trans;

/**
 * Server tarafi icin, uygulamanin geneli icin veritabani metodlari sunar
 * 
 * @author mdpinar
 * 
 */
public final class DBWorks {

	/**
	 * Ornegi alinamaz
	 * 
	 */
	private DBWorks() {
		;
	}
	
	/**
	 * Durumu degisen filmin, hareketlerini bir geri alir
	 * 
	 * @param session
	 * @param movie
	 * @param unwantedTransId istenmeyen yani silinecek olan trans id
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void undoMovieStatus(Session session, Movie movie, Long unwantedTransId) {
		List<Trans> transList = session.createQuery("from Trans t " +
										 "where t.movie = :movie " +
										 "  and t.id != :unwantedTransId " +
										 "order by t.actionId desc")
									.setParameter("movie", movie)
									.setParameter("unwantedTransId", unwantedTransId)
								.setFetchSize(1)
							.list();
		
		Trans prevTrans = (transList.size() > 0 ? transList.get(0) : null);
		if (prevTrans != null) {
			movie.setLastTransId(prevTrans.getId());
			movie.setLastTransDate(prevTrans.getTransDate());
			movie.setLastCustomer(prevTrans.getCustomer());
			movie.setLastStatus(prevTrans.getStatus());
		} else {
			movie.setLastTransId(null);
			movie.setLastTransDate(null);
			movie.setLastCustomer(null);
			movie.setLastStatus(FilmStatus.IN_STORE);
		}
		session.merge(movie);
		
		Trans trans = (Trans) session.get(Trans.class, unwantedTransId);
		if (trans != null) {
			session.delete(trans);
		}
	}
	
}
