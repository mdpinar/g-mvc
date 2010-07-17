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

import java.util.List;

import org.hibernate.Session;

import com.gmvc.client.meta.EffectedDetail;
import com.gmvc.client.meta.RetVal;
import com.gmvc.server.impl.CRUDImpl;
import com.mclub.client.model.MovieInfoDTO;
import com.mclub.client.service.MovieInfoService;
import com.mclub.server.model.Movie;
import com.mclub.server.model.MovieInfo;

/**
 * Film Tanitimi servisi
 * 
 * @see CRUDImpl
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings("serial")
public class MovieInfoServiceImpl
	extends CRUDImpl<MovieInfo, MovieInfoDTO> implements MovieInfoService {

	public MovieInfoServiceImpl() {
		super(MovieInfo.class, MovieInfoDTO.class);
	}

	/**
	 * Film basligi kaydedilmeden hemen once, kendisine bagli filmlerin
	 * isim alanlari, basliktaki isim alanina esitlenir
	 * 
	 */
	@Override
	public RetVal beforeSave(Session session, MovieInfo serverModel, List<EffectedDetail> effectedDetailList) {
		List<Movie> movieList = serverModel.getMovies();
		for (Movie movie: movieList) {
			movie.setName(serverModel.getName());
		}
		return null;
	}
	
}
