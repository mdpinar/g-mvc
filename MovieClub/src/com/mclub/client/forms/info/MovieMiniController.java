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

import com.extjs.gxt.ui.client.widget.Info;
import com.gmvc.client.base.AbstractBaseController;
import com.gmvc.client.base.AbstractMiniController;
import com.gmvc.client.base.IBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.base.IEditor;
import com.gmvc.client.meta.Event;
import com.gmvc.client.util.Tags;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.model.MovieDTO;

/**
 * Film Tanitimi altindaki Kopya Filmler kismi, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class MovieMiniController extends AbstractMiniController<MovieDTO> {

	public MovieMiniController(IController<?> parent) {
		super(parent, "Movie");
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
	public int getHeight() {
		return 200;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBrowser<MovieDTO> createBrowser() {
		return new MovieMiniBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IEditor<MovieDTO> createEditor() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeEvent(Event event) {
		if (event.getType() == SpecialEvent.Delete) {
			MovieDTO movie = (MovieDTO) event.getModel();
			if (movie != null && movie.getLastTransId() != null) {
				event.setCancel(true);
				Info.display(Tags.get("attention"), Tags.get("dontDeletedMessage"));
			}
		}
	}

}
