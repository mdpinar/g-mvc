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

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.gmvc.client.base.AbstractBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.util.ColumnFactory;
import com.gmvc.client.util.Tags;
import com.mclub.client.model.IncomingMovieDetailDTO;

/**
 * Gelen filmler altindaki ekli filmler kismi, Browser sinifi
 * 
 * @see AbstractBrowser
 * 
 * @author mdpinar
 * 
 */
public class IncomingMovieMiniBrowser extends AbstractBrowser<IncomingMovieDetailDTO> {
	
	public IncomingMovieMiniBrowser(IController<IncomingMovieDetailDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ToolBar getToolBar() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ColumnFactory getColumnFactory() {
		return
			new ColumnFactory()
				.addColumn("movie.name", Tags.get("movie"), 200)
				.addColumn("movie.copyNo", Tags.get("copy"), 50, HorizontalAlignment.RIGHT)
				.addColumn("movie.lastStatus", Tags.get("lastStatus"), 80)
				.addColumn("deleteButton", Tags.get("delete"), 35, 
							ColumnFactory.getRemoveButtonCellRenderer(getController()));
	}
	
}
