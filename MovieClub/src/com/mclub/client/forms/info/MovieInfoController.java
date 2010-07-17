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
package com.mclub.client.forms.info;

import com.gmvc.client.base.AbstractBaseController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.Right;
import com.gmvc.client.service.ICRUDServiceAsync;
import com.gmvc.client.util.Tags;
import com.gmvc.client.util.Utils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.mclub.client.app.Rights;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.image.Images;
import com.mclub.client.model.MovieDTO;
import com.mclub.client.model.MovieInfoDTO;
import com.mclub.client.service.MovieInfoService;
import com.mclub.client.service.MovieInfoServiceAsync;
import com.mclub.client.util.Loaders;

/**
 * Film Tanitimi, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class MovieInfoController extends AbstractBaseController<MovieInfoDTO> {

	/**
	 * Editor kismi
	 * 
	 */
	private MovieInfoEditor editor;
	
	/**
	 * Server tarafi iletisim servisi
	 * 
	 */
	private MovieInfoServiceAsync service = GWT.create(MovieInfoService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MovieInfoBrowser createBrowser() {
		return new MovieInfoBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MovieInfoEditor createEditor() {
		editor = new MovieInfoEditor(this);
		return editor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICRUDServiceAsync<MovieInfoDTO> getService() {
		return this.service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeEvent(Event event) {
		if (event.getType() == SpecialEvent.Delete) {
			MovieInfoDTO moveiInfo = (MovieInfoDTO) event.getModel();
			for (MovieDTO movie: moveiInfo.getMovies()) {
				if (movie.getLastTransId() != null) {
					event.setCancel(true);
					Utils.showAlert(Tags.get("movieHadBeenProcess"));
					break;
				}
			}

		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void holdSpecialEvent(Event event) {
		if (event != null) {
			MovieInfoDTO model = (MovieInfoDTO) event.getModel();
			switch (event.getType()) {

				/*
				 * Editor den cagrilir
				 * 
				 * Tanimli film gruplarini comboya yukler
				 */
				case SpecialEvent.LoadMovieGroup: {
					Loaders.loadMovieGroups(editor.movieGroup, model.getMovieGroup());
					break;
				}

			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return Rights.Movie;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageResource getIcon() {
		return Images.Instance.film();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("movieInfoTitle");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 700;
	}

}
