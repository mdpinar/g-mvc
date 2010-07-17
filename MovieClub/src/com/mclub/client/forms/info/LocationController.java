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
import com.mclub.client.model.LocationDTO;
import com.mclub.client.service.LocationService;
import com.mclub.client.service.LocationServiceAsync;

/**
 * Lokasyon/Bolge Tanitimi, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class LocationController extends AbstractBaseController<LocationDTO> {

	/**
	 * Server tarafi iletisim servisi
	 * 
	 */
	private LocationServiceAsync service = GWT.create(LocationService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocationBrowser createBrowser() {
		return new LocationBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LocationEditor createEditor() {
		return new LocationEditor(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICRUDServiceAsync<LocationDTO> getService() {
		return this.service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return Rights.Location;
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
		return Images.Instance.plugin();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("locationTitle");
	}

}
