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
import com.gmvc.client.base.AbstractMiniController;
import com.gmvc.client.base.IBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.base.IEditor;
import com.gmvc.client.meta.Event;
import com.gmvc.client.util.Tags;
import com.gmvc.client.util.Utils;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.model.ChangeStatusDetailDTO;

/**
 * Durumu degisen filmler altindaki ekli filmler kismi, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class ChangeStatusMiniController extends AbstractMiniController<ChangeStatusDetailDTO> {

	public ChangeStatusMiniController(IController<?> parent) {
		super(parent, "ChangeStatusDetail");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 230;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBrowser<ChangeStatusDetailDTO> createBrowser() {
		return new ChangeStatusMiniBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IEditor<ChangeStatusDetailDTO> createEditor() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeEvent(Event event) {
		switch (event.getType()) {
			case SpecialEvent.Delete: {
				ChangeStatusDetailDTO detail = (ChangeStatusDetailDTO) event.getModel();
				if (detail.getId() != null 
				&& ! detail.getTransId().equals(detail.getMovie().getLastTransId())) {
					event.setCancel(true);
					Utils.showAlert(Tags.get("cantDeleteThisMovie"));
					break;
				}
			}
		}
	}
	
}
