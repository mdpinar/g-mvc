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
package com.mclub.client.forms.user;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.gmvc.client.base.AbstractBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.util.ColumnFactory;
import com.gmvc.client.util.Tags;
import com.mclub.client.model.UserDTO;

/**
 * Kullanici Tanitimi, Browser sinifi
 * 
 * @see AbstractBrowser
 * 
 * @author mdpinar
 * 
 */
public class UserBrowser extends AbstractBrowser<UserDTO> {

	public UserBrowser(IController<UserDTO> controller) {
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
				.addColumn("id", "ID#", 50, HorizontalAlignment.RIGHT)
				.addColumn("name", Tags.get("name"), 250, true)
				.addColumn("isAdmin", "Admin ?", 80);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDeleteQueryPart() {
		return getSelectedModel().getName();
	}

}
