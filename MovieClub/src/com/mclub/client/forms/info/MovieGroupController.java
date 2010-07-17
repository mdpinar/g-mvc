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
import com.gmvc.client.meta.Right;
import com.gmvc.client.service.ICRUDServiceAsync;
import com.gmvc.client.util.Tags;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.mclub.client.app.Rights;
import com.mclub.client.image.Images;
import com.mclub.client.model.MovieGroupDTO;
import com.mclub.client.service.MovieGroupService;
import com.mclub.client.service.MovieGroupServiceAsync;

/**
 * Film Gurubu Tanitimi, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class MovieGroupController extends AbstractBaseController<MovieGroupDTO> {

	/**
	 * Server tarafi iletisim servisi
	 * 
	 */
	private MovieGroupServiceAsync service = GWT.create(MovieGroupService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MovieGroupBrowser createBrowser() {
		return new MovieGroupBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MovieGroupEditor createEditor() {
		return new MovieGroupEditor(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICRUDServiceAsync<MovieGroupDTO> getService() {
		return this.service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return Rights.MovieGroup;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 450;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageResource getIcon() {
		return Images.Instance.all_in();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("movieGroupTitle");
	}

}
