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
package com.mclub.client.forms.changeStatus;

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
import com.mclub.client.model.ChangeStatusDTO;
import com.mclub.client.model.ChangeStatusDetailDTO;
import com.mclub.client.service.ChangeStatusService;
import com.mclub.client.service.ChangeStatusServiceAsync;

/**
 * Durumu degisen filmler, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class ChangeStatusController extends AbstractBaseController<ChangeStatusDTO> {

	/**
	 * Server tarafi iletisim servisi
	 * 
	 */
	private ChangeStatusServiceAsync service = GWT.create(ChangeStatusService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChangeStatusBrowser createBrowser() {
		return new ChangeStatusBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChangeStatusEditor createEditor() {
		return new ChangeStatusEditor(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICRUDServiceAsync<ChangeStatusDTO> getService() {
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
				ChangeStatusDTO changeStatus = (ChangeStatusDTO) event.getModel();
				if (changeStatus.getDetails().size() < 1) {
					event.setCancel(true);
					Utils.showAlert(Tags.get("haveToAddMinDetail"));
					break;
				}
				break;
			}
		
			case SpecialEvent.Delete: {
				ChangeStatusDTO changeStatus = (ChangeStatusDTO) event.getModel();
				for (ChangeStatusDetailDTO detail: changeStatus.getDetails()) {
					
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
		return Rights.ChangeStatus;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 500;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageResource getIcon() {
		return Images.Instance.comment_edit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("changeStatus");
	}

}
