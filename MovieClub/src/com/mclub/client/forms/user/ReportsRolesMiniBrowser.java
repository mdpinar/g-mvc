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

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.gmvc.client.base.AbstractBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.util.ColumnFactory;
import com.gmvc.client.util.Tags;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.model.RoleDTO;

/**
 * Kullanici Tanitimi altindaki Rapor Rolleri kismi, Browser sinifi
 * 
 * @see AbstractBrowser
 * 
 * @author mdpinar
 * 
 */
public class ReportsRolesMiniBrowser extends AbstractBrowser<RoleDTO> {

	public ReportsRolesMiniBrowser(IController<RoleDTO> controller) {
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
	public void init() {
		/*
		 * Ikinci kolon basligina tiklanirsa secim tersine cevrilir
		 */
		getGrid().addListener(Events.HeaderClick,
				new Listener<GridEvent<ModelData>>() {
					@Override
					public void handleEvent(GridEvent<ModelData> be) {
						getController().fireEvent(
							new Event(SpecialEvent.GridEvent)
								.addParam("rowIndex", -1)
								.addParam("colIndex", be.getColIndex())
						);
					}
				});

		/*
		 * Ikinci hucre tiklanirsa secim tersine cevrilir,
		 */
		getGrid().addListener(Events.CellClick,
				new Listener<GridEvent<ModelData>>() {
					@Override
					public void handleEvent(GridEvent<ModelData> be) {
						getController().fireEvent(
							new Event(SpecialEvent.GridEvent)
								.addParam("rowIndex", be.getRowIndex())
								.addParam("colIndex", be.getColIndex())
						);
					}
				});

		addWidgetToPanel(getGrid(), new RowData(1, 1, new Margins(4)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ColumnFactory getColumnFactory() {
		return
			new ColumnFactory()
				.addColumn("right", Tags.get("rights"), 390)
				.addColumn(ColumnFactory.getCheckBoxColumn("hasExecute", Tags.get("hasExecute"), 60));
	}
	
}
