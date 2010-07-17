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
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.gmvc.client.base.AbstractBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.enums.SearchType;
import com.gmvc.client.util.ColumnFactory;
import com.gmvc.client.util.Tags;
import com.mclub.client.model.AddressDTO;
import com.mclub.client.model.CustomerDTO;

/**
 * Musteri Tanitimi, Browser sinifi
 * 
 * @see AbstractBrowser
 * 
 * @author mdpinar
 * 
 */
public class CustomerBrowser extends AbstractBrowser<CustomerDTO> {

	public CustomerBrowser(IController<CustomerDTO> controller) {
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
				.addColumn("name", Tags.get("name"), 250, true, SearchType.LIKE, true)
				.addColumn("mobilePhone", Tags.get("mobilePhone"), 80)
				.addColumn("phone", Tags.get("homePhone"), 80, getHomePhoneCellRenderer())
				.addColumn("location", Tags.get("homeLocation"), 100, getHomeLocationCellRenderer())
				.addColumn("job", Tags.get("job"), 120);
	}
	
	/**
	 * Ev Adresi embedded yapida, Ev telefonu bilgisini 
	 * kolonda gosterebilmek icin lazim olan cellrenderer i doner 
	 * 
	 * @return GridCellRenderer
	 */
	private GridCellRenderer<BeanModel> getHomePhoneCellRenderer() {
		return new GridCellRenderer<BeanModel>() {
			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				
				AddressDTO address = model.get("homeAddress");
				if (address != null) {
					return address.getPhone();
				}
				return "";
				
			}
		};
	}
	
	/**
	 * Ev Adresi embedded yapida, Ev bolgesi bilgisini 
	 * kolonda gosterebilmek icin lazim olan cellrenderer i doner
	 * 
	 * @return GridCellRenderer
	 */
	private GridCellRenderer<BeanModel> getHomeLocationCellRenderer() {
		return new GridCellRenderer<BeanModel>() {
			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				
				AddressDTO address = model.get("homeAddress");
				if (address != null) {
					return address.getLocation();
				}
				return "";
				
			}
		};
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDeleteQueryPart() {
		return getSelectedModel().getName();
	}

}
