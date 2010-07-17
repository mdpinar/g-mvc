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
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.mclub.client.app.Rights;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.image.Images;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.service.CustomerService;
import com.mclub.client.service.CustomerServiceAsync;
import com.mclub.client.util.Loaders;

/**
 * Musteri Tanitimi, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class CustomerController extends AbstractBaseController<CustomerDTO> {

	/**
	 * Editor kismi
	 */
	private CustomerEditor editor;
	
	/**
	 * Server tarafi iletisim servisi
	 */
	private CustomerServiceAsync service = GWT.create(CustomerService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CustomerBrowser createBrowser() {
		return new CustomerBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CustomerEditor createEditor() {
		editor = new CustomerEditor(this);
		return editor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICRUDServiceAsync<CustomerDTO> getService() {
		return this.service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void holdSpecialEvent(Event event) {
		if (event != null) {
			CustomerDTO model = (CustomerDTO) event.getModel();
			switch (event.getType()) {

				/*
				 * Meslek secim combosuna tanimli meslekleri yukler
				 */
				case SpecialEvent.LoadJob: {
					Loaders.loadJobs(editor.job, model.getJob());
					break;
				}

				/*
				 * Ev ve Is lokasyon combosuna tanimli lokasyonlari yukler
				 */
				case SpecialEvent.LoadLocation: {
					Loaders.loadLocations(editor.homeLocation, 
							(model.getHomeAddress() != null ? model.getHomeAddress().getLocation() : null));
					Loaders.loadLocations(editor.workLocation, 
							(model.getWorkAddress() != null ? model.getWorkAddress().getLocation() : null));
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
		return Rights.Customer;
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
		return Images.Instance.group();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("customerBrowserTitle");
	}

}
