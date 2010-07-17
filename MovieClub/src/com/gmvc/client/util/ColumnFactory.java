/*
 * Copyleft 2010 Mustafa DUMLUPINAR
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 * http://www.gnu.org/licenses/lgpl.html
 *
 * dumlupinar01@gmail.com
 * http://mdpinar.blogspot.com/
 *
 */
package com.gmvc.client.util;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.gmvc.client.base.IController;
import com.gmvc.client.base.IModel;
import com.gmvc.client.enums.SearchType;
import com.gmvc.client.image.BaseImages;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.EventType;
import com.gmvc.client.meta.FilterItem;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * Browserlar uzerinde bulunan gridlere kolonlar uretir
 * 
 * @author mdpinar
 * 
 */
public final class ColumnFactory {

	/**
	 * Kolonlari barindiran mapdir
	 * 
	 * @see #getColumnModel()
	 * @see ColumnConfig
	 */
	private List<ColumnConfig> columnList;

	/**
	 * Filtre alanlarini barindiran listedir
	 * 
	 * @see FilterItem
	 */
	private List<FilterItem> filterItemList;

	/**
	 * Genisligi otomatik olarak ayarlanacak olan kolonun id si
	 * 
	 */
	private String autoExpandColumnId;
	
	/**
	 * Kolon ekler
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * 
	 * @see #addColumn(String, String, int)
	 */
	public ColumnFactory addColumn(String id, String header, int width) {
		return addColumn(getBaseColumn(id, header, width));
	}

	/**
	 * Kolon ekler
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * @param isAutoExpand
	 * 
	 * @see #addColumn(String, String, int)
	 */
	public ColumnFactory addColumn(String id, String header, int width, boolean isAutoExpand) {
		if (isAutoExpand) autoExpandColumnId = id;
		return addColumn(getBaseColumn(id, header, width));
	}

	/**
	 * Kolon ekler
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * @param isFilter
	 * @param searchType
	 * 
	 * @see #addColumn(String, String, int)
	 */
	public ColumnFactory addColumn(String id, String header, int width, boolean isFilter, SearchType searchType) {
		if (isFilter) addFilterItem(id, searchType, header);
		return addColumn(getBaseColumn(id, header, width));
	}

	/**
	 * Kolon ekler
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * @param isFilter
	 * @param searchType
	 * @param isAutoExpand
	 * 
	 * @see #addColumn(String, String, int, boolean, SearchType)
	 */
	public ColumnFactory addColumn(String id, String header, int width,
			boolean isFilter, SearchType searchType, boolean isAutoExpand) {
		if (isAutoExpand) autoExpandColumnId = id;
		return addColumn(id, header, width, isFilter, searchType);
	}

	/**
	 * Kolon ekler
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * @param alignment
	 * 
	 * @see #addColumn(String, String, int)
	 */
	public ColumnFactory addColumn(String id, String header, int width, HorizontalAlignment alignment) {
		ColumnConfig column = getBaseColumn(id, header, width);
		column.setAlignment(alignment);
		return addColumn(column);
	}

	/**
	 * Kolon ekler
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * @param alignment
	 * @param cellRenderer
	 * 
	 * @see #addColumn(String, String, int)
	 */
	public ColumnFactory addColumn(String id, String header, int width, GridCellRenderer<BeanModel> cellRenderer) {
		ColumnConfig column = getBaseColumn(id, header, width);
		column.setRenderer(cellRenderer);
		return addColumn(column);
	}

	/**
	 * Kolon ekler
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * @param alignment
	 * @param isFilter
	 * @param searchType
	 * 
	 * @see #addColumn(String, String, int)
	 * @see SearchType
	 */
	public ColumnFactory addColumn(String id, String header, int width,
			HorizontalAlignment alignment, boolean isFilter, SearchType searchType) {
		ColumnConfig column = getBaseColumn(id, header, width);
		column.setAlignment(alignment);
		if (isFilter) addFilterItem(id, searchType, header);
		return addColumn(column);
	}

	/**
	 * Temel kolon tipinde yeni bir kolon verir
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * @return ColumnConfig
	 */
	public static ColumnConfig getBaseColumn(String id, String header, int width) {
		ColumnConfig column = new ColumnConfig(id, header, width);
		column.setSortable(false);
		column.setMenuDisabled(false);
		return column;
	}
	
	/**
	 * Tarih tipinde Kolon doner
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * 
	 * @see #addColumn(String, String, int)
	 */
	public static ColumnConfig getDateColumn(String id, String header, int width) {
		ColumnConfig column = getBaseColumn(id, header, width);
		
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setDateTimeFormat(DateTimeFormat.getFormat("dd/MM/yyyy"));
		
		return column;
	}

	/**
	 * CheckBox kolon tipinde yeni bir kolon verir
	 * 
	 * @param id
	 * @param header
	 * @param width
	 * @return CheckColumnConfig
	 */
	public static CheckColumnConfig getCheckBoxColumn(String id, String header, int width) {
		CheckColumnConfig column = new CheckColumnConfig(id, header, width);
		column.setSortable(false);
		column.setMenuDisabled(false);
		column.setEditor(new CellEditor(new CheckBox()));
		return column;
	}

	/**
	 * Gridlere sil butonu kolonu eklemek icin hazir yapi sunar
	 * 
	 * @param <M extends IModel>
	 * @param controller
	 * @return GridCellRenderer<ModelData>
	 */
	public static <M extends IModel> GridCellRenderer<BeanModel> getRemoveButtonCellRenderer(final IController<M> controller) {
		
		return new GridCellRenderer<BeanModel>() {
	
			private boolean init;  
	
			public Object render(final BeanModel model, String property, ColumnData config, final int rowIndex,
					final int colIndex, ListStore<BeanModel> store, Grid<BeanModel> grid) {
				
				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize, new Listener<GridEvent<BeanModel>>() {
					    public void handleEvent(GridEvent<BeanModel> be) {
				    		for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
								if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
									&& be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
								   ((BoxComponent) be.getGrid().getView().getWidget(i, be.getColIndex())).setWidth(be.getWidth() - 10);
				        		}
				      		}  
				    	}  
					});  
				}  
	
				Button b = 
					new Button(null, 
						AbstractImagePrototype.create(BaseImages.Instance.delete()), 
						new SelectionListener<ButtonEvent>() {
						
						@Override
						@SuppressWarnings("unchecked")
						public void componentSelected(ButtonEvent ce) {
							controller.fireEvent(new Event(EventType.Delete, (M) model.getBean()));
						}
						
					}
				);
				b.setHeight(15);
				b.setBorders(false);
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
	
				return b;
			}
		};
		
	}

	/**
	 * Kolon listesine yeni bir kolon ekler
	 * 
	 * @param column
	 */
	public ColumnFactory addColumn(ColumnConfig column) {
		if (columnList == null) columnList = new ArrayList<ColumnConfig>();
		columnList.add(column);
		return this;
	}

	/**
	 * Kolon listesini doner
	 * 
	 * @return List<ColumnConfig>
	 */
	public List<ColumnConfig> getColumnList() {
		return columnList;
	}

	/**
	 * Filtre listesini doner
	 * 
	 * @return
	 */
	public List<FilterItem> getFilterItemList() {
		return filterItemList;
	}

	/**
	 * Filtre listesini setler
	 * 
	 * @param filterItemList
	 */
	public void setFilterItemList(List<FilterItem> filterItemList) {
		this.filterItemList = filterItemList;
	}

	/**
	 * Otomatik genislige sahip kolonu doner
	 * 
	 * @return
	 */
	public String getAutoExpandColumnId() {
		return autoExpandColumnId;
	}

	/**
	 * Otomatik genislige sahip kolonu setler
	 * 
	 * @param autoExpandColumnId
	 */
	public void setAutoExpandColumnId(String autoExpandColumnId) {
		this.autoExpandColumnId = autoExpandColumnId;
	}

	/**
	 * Filtre paneline eklenecek sahlari ekler
	 * 
	 * @param id
	 * @param searchType
	 * @param header
	 * 
	 * @see SearchType
	 */
	private void addFilterItem(String id, SearchType searchType, String header) {
		if (filterItemList == null) filterItemList = new ArrayList<FilterItem>();
		filterItemList.add(new FilterItem(id, searchType, header));
	}
	
}
