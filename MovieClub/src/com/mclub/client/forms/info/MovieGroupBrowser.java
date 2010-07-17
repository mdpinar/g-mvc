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

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.gmvc.client.base.AbstractBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.enums.SearchType;
import com.gmvc.client.util.ColumnFactory;
import com.gmvc.client.util.Tags;
import com.mclub.client.model.MovieGroupDTO;

/**
 * Film Gurubu Tanitimi, Browser sinifi
 * 
 * @see AbstractBrowser
 * 
 * @author mdpinar
 * 
 */
public class MovieGroupBrowser extends AbstractBrowser<MovieGroupDTO> {

	public MovieGroupBrowser(IController<MovieGroupDTO> controller) {
		super(controller, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ColumnFactory getColumnFactory() {
		return
			new ColumnFactory()
				.addColumn("id", "ID#", 70, HorizontalAlignment.RIGHT)
				.addColumn("name", Tags.get("name"), 250, true, SearchType.LIKE, true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDeleteQueryPart() {
		return getSelectedModel().getName();
	}

}
