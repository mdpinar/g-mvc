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

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.gmvc.client.base.AbstractBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.enums.SearchType;
import com.gmvc.client.util.ColumnFactory;
import com.gmvc.client.util.Tags;
import com.mclub.client.model.GivenMovieDTO;

/**
 * Verilen Filmler, Browser sinifi
 * 
 * @see AbstractBrowser
 * 
 * @author mdpinar
 * 
 */
public class GivenMovieBrowser extends AbstractBrowser<GivenMovieDTO> {

	public GivenMovieBrowser(IController<GivenMovieDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		addWidgetToPanel(getGrid(), new RowData(1, 1, new Margins(4)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ColumnFactory getColumnFactory() {
		return 
			new ColumnFactory()
				.addColumn("id", "ID#", 50, HorizontalAlignment.RIGHT, true, SearchType.EQUAL)
				.addColumn(ColumnFactory.getDateColumn("transDate", Tags.get("date"), 80))
				.addColumn("customer.name", Tags.get("customer"), 200, true, SearchType.LIKE, false)
				.addColumn("description", Tags.get("description"), 200, true, SearchType.LIKE, false)
				.addColumn("user.name", Tags.get("user"), 80);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDeleteQueryPart() {
		return getSelectedModel().getCustomer().getName() + Tags.get("processOwner");
	}

}
