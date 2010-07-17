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
package com.mclub.client.forms.incoming;

import com.gmvc.client.base.AbstractBaseController;
import com.gmvc.client.base.AbstractMiniController;
import com.gmvc.client.base.IBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.base.IEditor;
import com.gmvc.client.meta.Event;
import com.gmvc.client.util.Tags;
import com.gmvc.client.util.Utils;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.model.IncomingMovieDetailDTO;

/**
 * Gelen filmler altindaki ekli filmler kismi, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class IncomingMovieMiniController extends AbstractMiniController<IncomingMovieDetailDTO> {

	public IncomingMovieMiniController(IController<?> parent) {
		super(parent, "IncomingMovieDetail");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 150;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBrowser<IncomingMovieDetailDTO> createBrowser() {
		return new IncomingMovieMiniBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IEditor<IncomingMovieDetailDTO> createEditor() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeEvent(Event event) {
		switch (event.getType()) {
			case SpecialEvent.Delete: {
				IncomingMovieDetailDTO detail = (IncomingMovieDetailDTO) event.getModel();
				if (detail.getId() != null 
				&& ! detail.getTransId().equals(detail.getMovie().getLastTransId())) {
					event.setCancel(true);
					Utils.showAlert(Tags.get("movieHadBeenProcess"));
					break;
				} else { //tekrardan secim listesine eklensin diye haber veriliyor
					getParent().fireEvent(new Event(SpecialEvent.Overall, detail));
				}
			}
		}
	}
	
}
