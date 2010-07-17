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
package com.mclub.client.forms.given;

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
import com.mclub.client.model.GivenMovieDTO;
import com.mclub.client.model.GivenMovieDetailDTO;
import com.mclub.client.service.GivenMovieService;
import com.mclub.client.service.GivenMovieServiceAsync;

/**
 * Verilen filmler, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class GivenMovieController extends AbstractBaseController<GivenMovieDTO> {

	/**
	 * Server tarafi iletisim servisi
	 * 
	 */
	private GivenMovieServiceAsync service = GWT.create(GivenMovieService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GivenMovieBrowser createBrowser() {
		return new GivenMovieBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GivenMovieEditor createEditor() {
		return new GivenMovieEditor(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICRUDServiceAsync<GivenMovieDTO> getService() {
		return this.service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeEvent(Event event) {
		switch (event.getType()) {

			case SpecialEvent.SaveAndClose:
			case SpecialEvent.SaveAndContinue: {
				GivenMovieDTO givenMovie = (GivenMovieDTO) event.getModel();
				if (givenMovie.getDetails().size() < 1) {
					event.setCancel(true);
					Utils.showAlert(Tags.get("haveToAddMinDetail"));
					break;
				}
				break;
			}
		
			case SpecialEvent.Delete: {
				GivenMovieDTO givenMovie = (GivenMovieDTO) event.getModel();
				for (GivenMovieDetailDTO detail: givenMovie.getDetails()) {
					
					if (! detail.getTransId().equals(detail.getMovie().getLastTransId())) {
						event.setCancel(true);
						Utils.showAlert(Tags.get("cantDeleteThisAction"));
						break;
					}
					
				}
				break;
			}

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return Rights.GivenMovie;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 670;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageResource getIcon() {
		return Images.Instance.run();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("givenMovies");
	}

}
