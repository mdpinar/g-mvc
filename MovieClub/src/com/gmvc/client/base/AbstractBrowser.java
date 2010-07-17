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
package com.gmvc.client.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.gmvc.client.image.BaseImages;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.EventType;
import com.gmvc.client.meta.FilterItem;
import com.gmvc.client.util.ColumnFactory;
import com.gmvc.client.util.InstantComponent;
import com.gmvc.client.util.Tags;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;

/**
 * Model-View-Controller uclusundeki Browser siniflari icin hazir yapi sunan
 * soyut siniftir
 * 
 * <p/>
 * View katmanindaki iki yapidan (Browser-Editor) birisidir. Kayit islemleri
 * icin, Listeleme, Arama ve Silme islemlerini yapar. Kendisini miras alacak
 * olan siniflar adina ortak olan metodlari gerceklestirir.
 * 
 * @see IModel
 * @see IBrowser
 * @see IController
 * 
 * @author mdpinar
 * 
 */
public abstract class AbstractBrowser<M extends IModel> implements IBrowser<M> {

	/**
	 * Kendisini kullanan Controller
	 * 
	 * @see #getController()
	 */
	private IController<M> controller;

	/**
	 * Verilerin listelendigi grid
	 * 
	 * @see #getGrid()
	 */
	private Grid<BeanModel> grid;

	/**
	 * Formun ana panelidir, butun bilesenler bu form uzerine yerlestirilir.
	 * 
	 * @see ContentPanel
	 */
	protected ContentPanel mainPanel;

	/**
	 * Grid' in yerlestirilecegi paneldir.
	 * 
	 * @see #getPanel()
	 */
	private ContentPanel panel;

	/**
	 * Filtre alanlarinin yerlestirilecegi paneldir.
	 * 
	 * @see ContentPanel
	 */
	private ContentPanel filterPanel;
	
	/**
	 * Filtre basliklari ile Filtre degerlerini elde etmek icin kullanilacak 
	 */
	private Map<String, FilterItem> filterMap;
	
	/**
	 * CRUD (Create-Read(refresh/show)-Update-Delete) butonlarinin 
	 * bulundugu ToolBar
	 * 
	 * @see #getToolBar()
	 */
	private ToolBar toolBar;

	/**
	 * Yeni Kayit butonu
	 * 
	 * @see #getAddNewButton()
	 */
	protected Button addNewButton;

	/**
	 * Guncelleme butonu
	 * 
	 * @see #getUpdateButton()
	 */
	protected Button updateButton;

	/**
	 * Kayit Silme butonu
	 * 
	 * @see #getDeleteButton()
	 */
	protected Button deleteButton;

	/**
	 * Grid uzerindeki kayitlari Tazeleme butonu
	 * 
	 * @see #getRefreshButton()
	 */
	protected Button refreshButton;

	/**
	 * Formun ilk yukleme islemlerinin yalnizca bir kere yapilmasini saglamak
	 * icin kullanilan degisken
	 * 
	 */
	private boolean initialize;
	
	/**
	 * View kismin tek bir formdan ibaretse bu deger true olacak
	 * 
	 * Normalde View kismi Browser-Editor ikilisinden olusuyor
	 */
	private boolean isSingleForm;
	
	/**
	 * Kolon tanimlarinin tutuldugu degisken
	 */
	private ColumnFactory colFactory;
	
	/**
	 * Kolon tanimlarinin bulundugu yapiyi donen metod.
	 */
	public abstract ColumnFactory getColumnFactory();

	public AbstractBrowser(IController<M> controller) {
		this(controller, false);
	}
	
	/**
	 * Bagli bulundugu Controller' i alarak ayaga kalkar. 
	 * 
	 * <p/>
	 * mainPanel, grid, toolbar ve filtre paneli gibi bilesenleri olusturur.
	 * 
	 */
	public AbstractBrowser(IController<M> controller, boolean isSingleForm) {
		this.controller = controller;
		this.isSingleForm = isSingleForm;

		mainPanel = new ContentPanel(new RowLayout(Orientation.VERTICAL));
		mainPanel.setHeaderVisible(false);
		mainPanel.setHeight(this.controller.getHeight());
		mainPanel.setWidth(this.controller.getWidth());

		colFactory = getColumnFactory();
		grid = new Grid<BeanModel>(new ListStore<BeanModel>(), getColumnModel());
		grid.setBorders(true);
		grid.setLoadMask(true);
		grid.setAutoExpandColumn(colFactory.getAutoExpandColumnId());

		if (getToolBar() != null)
			mainPanel.add(getToolBar(), new RowData(1, -1, new Margins(4)));

		buildFilterPanel();
		ContentPanel masterFilterPanel = getMasterFilterPanel();
		
		if (filterPanel != null || masterFilterPanel != null) {
			
			ContentPanel fPanel = new ContentPanel();
			fPanel.setHeaderVisible(false);
			fPanel.setFrame(true);
		
			if (getMasterFilterPanel() != null) {
				masterFilterPanel.setBorders(false);
				masterFilterPanel.setFrame(false);
				fPanel.add(masterFilterPanel);
			}
			if (filterPanel != null) {
				filterPanel.setBorders(false);
				filterPanel.setFrame(false);
				fPanel.add(filterPanel);
			}
			
			mainPanel.add(fPanel, new RowData(1, -1, new Margins(4)));
		}

		/*
		 * Grid uzerindeki tiklamalarda, secili modelin gosterilebilmesi ve
		 * guncellenebilmesi icin update butonuna olay bildirimi yapilir.
		 */
		this.grid.addListener((isSingleForm ? Events.CellClick : Events.CellDoubleClick),
			new Listener<BaseEvent>() {
				public void handleEvent(BaseEvent be) {
					if (getSelectedModel() != null) {
						getController().fireEvent(new Event(EventType.Show));
					}
				}
			}
		);
		controller.fireEvent(new Event(EventType.InitBrowser));
	}
	
	public boolean isSingleForm() {
		return this.isSingleForm;
	}

	/**
	 * Browser in gosterimini saglayacak olan ana paneli doner
	 * 
	 * @see #init()
	 */
	public ContentPanel popup() {
		if (!isInitialize()) {
			init();
			
			addWidgetToPanel(getGrid(), new RowData(1, 1));
			if (isSingleForm) addWidgetToPanel(getController().getEditor().getMainPanel(), new RowData(1, -1));
			
			this.mainPanel.add(getPanel(), new RowData(1, 1, new Margins(4)));
		}
		getController().fireEvent(new Event(EventType.Refresh));

		return this.mainPanel;
	}
	
	/**
	 * Ilk etapta yapilmasi gereken islemler metodu.
	 * 
	 */
	public void init() {
		;
	}

	/**
	 * Asil panele bilesen ekler, grid gibi.
	 * 
	 */
	protected void addWidgetToPanel(Widget widget, RowData rowData) {
		getPanel().add(widget, rowData);
	}

	/**
	 * Grid uzerinde o an secili olan modeli doner, secili model yoksa griddeki
	 * ilk modeli doner
	 * 
	 */
	@SuppressWarnings("unchecked")
	public M getSelectedModel() {
		BeanModel beanModel = getGrid().getSelectionModel().getSelectedItem();

		if (beanModel == null && getGrid().getStore() != null
				&& getGrid().getStore().getCount() > 0) {
			beanModel = getGrid().getStore().getAt(0);
		}

		if (beanModel != null) {
			return (M) beanModel.getBean();
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<M> getSelectedModels() {
		List<M> modelList = new ArrayList<M>();
		
		for (BeanModel bm: getGrid().getSelectionModel().getSelectedItems()) {
			modelList.add((M) bm.getBean());
		}
		
		return modelList;
	}
	
	/**
	 * Bagli oldugu Controller' i doner
	 * 
	 * @see IController
	 */
	protected IController<M> getController() {
		return this.controller;
	}

	public Grid<BeanModel> getGrid() {
		return this.grid;
	}

	/**
	 * Grid gibi bilesenlerin eklendigi merkezdeki paneli doner.
	 * 
	 */
	protected ContentPanel getPanel() {
		if (panel == null) {
			panel = new ContentPanel();
			panel.setHeaderVisible(false);
			panel.setLayout(new RowLayout(Orientation.VERTICAL));
		}
		return panel;
	}

	/**
	 * Ilk yukleme kontrolu bu metoda bakilarak yapilir.
	 * 
	 * @see #popup()
	 */
	protected boolean isInitialize() {
		if (!initialize) {
			initialize = true;
			return false;
		}
		return initialize;
	}

	/**
	 * Standart butonlarin bulundugu toolbari doner
	 * 
	 * @see #buildToolBar()
	 * @return ToolBar
	 */
	public ToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new ToolBar();
			buildToolBar();
		}
		return toolBar;
	}

	public void refresh(ListStore<BeanModel> store) {
		getGrid().reconfigure(store, getColumnModel());
	}
	
	/**
	 * Grid uzerindeki bir kayit silinirken, uyari mesaji vermek istenirse bu alan 
	 * uyari mesajinin asil kismini donmelidir
	 * 
	 * <p/>
	 * Bu metodun dondugu string e sabit olarak "silinecek, emini misini?" ibaresi eklenecektir
	 * 
	 * Uyari mesaji: 23/04/2010 tarihli makbuz silinecek, emini misiniz?
	 * seklinde olacaksa sadece "23/04/2010 tarihli makbuz" donmelidir
	 * 
	 * ya da
	 * 
	 * Uyari mesaji: Hasan Ali'nin aldigi filmler silinecek, emini misiniz?
	 * seklinde olacaksa sadece "Hasan Ali'nin aldigi filmler" donmelidir
	 * 
	 */
	public String getDeleteQueryPart() {
		return null;
	}

	/**
	 * Grid uzerinde yer alacak olan kolon listesinin modelini doner.
	 * Buradaki modelin proje genelindeki model ile ilgisi yoktur.
	 * 
	 */
	protected ColumnModel getColumnModel() {
		return new ColumnModel(colFactory.getColumnList());
	}

	/**
	 * Toolbar a eklenecek ekstra butonlar
	 * 
	 */
	public Button[] getExtraButtons() {
		return null;
	}

	/**
	 * ToolBar uzerine standart butonlar eklenir
	 * 
	 * @see #getToolBar()
	 */
	private void buildToolBar() {
		Button[] buttons = getExtraButtons();
		if (buttons != null) {
			for (Button btn: buttons) {
				toolBar.add(btn);
			}
		}
		
		if (! isSingleForm) {
			if (getAddNewButton() != null) toolBar.add(getAddNewButton());
			if (getUpdateButton() != null) toolBar.add(getUpdateButton());
			toolBar.add(new SeparatorToolItem());
			if (getRefreshButton() != null) toolBar.add(getRefreshButton());
		}
		
		toolBar.add(new SeparatorToolItem());
		if (getDeleteButton() != null) toolBar.add(getDeleteButton());
	}

	/**
	 * Yeni kayit ekleme butonu
	 * 
	 * @see #getUpdateButton()
	 * @see #getDeleteButton()
	 * @see #getRefreshButton()
	 * 
	 * @see buildToolBar()
	 */
	protected Button getAddNewButton() {
		if (addNewButton == null) {
			addNewButton = new Button(Tags.get("addNew"),
				AbstractImagePrototype.create(BaseImages.Instance.add()),
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						getController().fireEvent(new Event(EventType.AddNew));
					}

				});
		}

		return addNewButton;
	}

	/**
	 * Kayit guncelleme butonu
	 * 
	 * @see #getAddNewButton()
	 * @see #getDeleteButton()
	 * @see #getRefreshButton()
	 * 
	 * @see buildToolBar()
	 */
	protected Button getUpdateButton() {
		if (updateButton == null) {
			updateButton = new Button(Tags.get("update"),
				AbstractImagePrototype.create(BaseImages.Instance.update()),
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						if (getSelectedModel() != null) {
							getController().fireEvent(new Event(EventType.Show));
						}
					}

				});
		}

		return updateButton;
	}

	/**
	 * Kayit silme butonu
	 * 
	 * @see #getAddNewButton()
	 * @see #getUpdateButton()
	 * @see #getRefreshButton()
	 * @see buildToolBar()
	 */
	protected Button getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new Button(Tags.get("delete"),
				AbstractImagePrototype.create(BaseImages.Instance.delete()),
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						if (getSelectedModel() != null) {
							getController().fireEvent(new Event(EventType.Delete, getSelectedModel()));
						}
					}

				});
		}

		return deleteButton;
	}

	/**
	 * Grid uzerindeki model listesini tazeleme butonu
	 * 
	 * @see #getAddNewButton()
	 * @see #getUpdateButton()
	 * @see #getDeleteButton()
	 * 
	 * @see buildToolBar()
	 */
	protected Button getRefreshButton() {
		if (refreshButton == null) {
			refreshButton = new Button(Tags.get("refresh"),
				AbstractImagePrototype.create(BaseImages.Instance.refresh()),
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						getController().fireEvent(new Event(EventType.Refresh));
					}

				});
		}

		return refreshButton;
	}
	
	/**
	 * Master filtre alani icin filtre doner
	 */
	@Override
	public FilterItem getMasterFilterItem() {
		return null;
	}
	
	/**
	 * Her halukarda olmasi gereken master filtre alani icin panel doner
	 */
	public ContentPanel getMasterFilterPanel() {
		return null;
	}

	/**
	 * Kolonlarin eklenmesi esnasinda filter olarak belirlenmis kolonlar icin,
	 * grid in hemen uzerinde filtre paneli olusturur. Bu alanlar uzerinden
	 * arama islemleri yapilabilir.
	 * 
	 * <p/>
	 * Hic filtre alani verilmezse, panel olusturulmaz.
	 * 
	 */
	private ContentPanel buildFilterPanel() {
		
		if (colFactory.getFilterItemList() != null && colFactory.getFilterItemList().size() > 0) {
			
			final SimpleComboBox<String> filterCombo = new SimpleComboBox<String>();
			final TextField<String> searchText = new TextField<String>();
			
			filterCombo.setTriggerAction(TriggerAction.ALL);
			filterCombo.setAllowBlank(false);
			filterCombo.setForceSelection(true);
			filterCombo.setEditable(false);
			filterCombo.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {
				@Override
				public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
					searchText.focus();
				}
			});
			
			final Button searchButton = new Button(Tags.get("search"), 
				AbstractImagePrototype.create(BaseImages.Instance.search()),
				new SelectionListener<ButtonEvent>() {
			
					@Override
					public void componentSelected(ButtonEvent ce) {
						FilterItem fi = filterMap.get(filterCombo.getSimpleValue());
						fi.setValue(searchText.getValue());
						getController().fireEvent(
							new Event(EventType.Refresh)
								.addParam("filter", fi)
						);
					}

			});
			
			final Button clearButton = new Button(null, 
				AbstractImagePrototype.create(BaseImages.Instance.broom()),
				new SelectionListener<ButtonEvent>() {
			
					@Override
					public void componentSelected(ButtonEvent ce) {
						searchText.setValue("");
						searchButton.fireEvent(Events.Select);
						searchText.focus();
					}

			});
			
			searchText.addListener(Events.OnKeyPress,
					new Listener<FieldEvent>() {
						@Override
						public void handleEvent(FieldEvent be) {
							if (be.getKeyCode() == KeyCodes.KEY_ENTER) {
								searchButton.fireEvent(Events.Select);
							}
						}
					});

			filterMap = new HashMap<String, FilterItem>();
			for (FilterItem fi: colFactory.getFilterItemList()) {
				filterCombo.add(fi.getLabel());
				filterMap.put(fi.getLabel(), fi);
			}
			filterCombo.setSimpleValue(colFactory.getFilterItemList().get(0).getLabel());
			
			filterPanel = InstantComponent.getSmartWidgetRow(30, 2,
											new Label(Tags.get("filter")),
											filterCombo,
											clearButton,
											searchText,
											searchButton);
			return filterPanel;

		} else {

			return null;

		}
	}
	
}
