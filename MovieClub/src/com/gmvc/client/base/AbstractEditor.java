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
import java.util.List;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.gmvc.client.image.BaseImages;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.EventType;
import com.gmvc.client.util.Tags;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;

/**
 * Temel Editor metodlarini gercekler, Editor siniflari icin hazir yapi sunan
 * soyut siniftir
 * 
 * <p/>
 * Gorsel veri giris/duzenlemeler bu siniflarda yapilir.
 * 
 * @see IModel
 * @see IEditor
 * @see IController
 * 
 * @author mdpinar
 * 
 */
public abstract class AbstractEditor<M extends IModel> implements IEditor<M> {

	/**
	 * Islem yapilacak modeli tutar, somut siniflarin dogrudan kullanabilmesi
	 * icin protected erisim hakki verildi
	 * 
	 * @see IModel
	 */
	protected M model;

	/**
	 * Kendisini kullanan Controller
	 * 
	 * @see IController
	 */
	private IController<M> controller;

	/**
	 * Editor bilesenlerinin gosterilecegi modal yapidaki pencere
	 * 
	 */
	private Window mainWindow;

	/**
	 * Tamam ve Vazgec butonlarinin bulundugu ToolBar
	 * 
	 */
	private ToolBar toolBar;

	/**
	 * Gosterim penceresine yerlestirilecek ana panel
	 * 
	 */
	private ContentPanel mainPanel;

	/**
	 * Ana panel icerisinde bulunacak Form yapisindaki panel. Bu paneldeki her
	 * bir satirda sahanin kendisi ve etiketi bulunacaktir, eklenen her satir
	 * otomatik olarak alt alta yerlesecektir
	 * 
	 */
	private FormPanel form;

	/**
	 * Modeli kaydederek formu kapatan buton
	 * 
	 * @see #getSaveAndCloseButton()
	 * @see #getSaveAndContinueButton()
	 */
	private Button saveAndCloseButton;

	/**
	 * Modeli kaydedererek yeni bir model ile kayit islemine devam eden buton
	 * 
	 * @see #getSaveAndCloseButton()
	 * @see #getSaveAndContinueButton()
	 */
	private Button saveAndContinueButton;

	/**
	 * Modeli kayden buton,
	 * 
	 * Tek form halindeki View larda kullaniliyor
	 * 
	 * @see #getSaveAndCloseButton()
	 * @see #getSaveAndContinueButton()
	 */
	private Button saveButton;

	/**
	 * Yeni model olusturan buton
	 * 
	 * Tek form halindeki View larda kullaniliyor
	 */
	private Button addNewButton;
	
	/**
	 * Formu onaylamadan kapatan buton
	 * 
	 * @see #getCancelButton()
	 */
	private Button cancelButton;

	/**
	 * Formun ilk yukleme islemlerinin yalnizca bir kere yapilmasini saglamak
	 * icin bakilan degisken
	 * 
	 * @see #isInitialize()
	 * @see #init()
	 */
	private boolean initialize;

	/**
	 * Form paneline eklenecek widget larin yerlesim araligi
	 * 
	 * @see #getFormPanel()
	 */
	private FormData formData = new FormData("-10");

	/**
	 * View kismin tek bir formdan ibaretse bu deger true olacak
	 * 
	 * Normalde View kismi Browser-Editor ikilisinden olusuyor
	 */
	private boolean isSingleForm;

	/**
	 * Yeni bir model ornegini doner
	 * 
	 * @see IModel
	 */
	public abstract M getNewModel();

	/**
	 * Ilk yukleme esnasinda yapilacak olan islemler icin kullanilir Ana pencere
	 * ilk yuklenme esnasinda otomatik olarak cagiracaktir.
	 * 
	 * @see #isInitialize();
	 */
	public abstract void init();

	/**
	 * Pencerenin ebadlarini doner (genislik, yukseklik)
	 * 
	 */
	public abstract Size getSize();

	/**
	 * Model deki verileri formdaki alanlara setler
	 * 
	 * @see #pushFields()
	 */
	public abstract void popFields();

	/**
	 * Form uzerinde bulunan alanlardaki verileri Model e setler
	 * 
	 * @see #popFields()
	 */
	public abstract void pushFields();

	/**
	 * Model deki verileri formdaki alanlara setler
	 * 
	 */
	public abstract Field<?> getFirstField();
	
	/**
	 * Formdaki alanlari tutar
	 */
	public List<Field<?>> fieldList = new ArrayList<Field<?>>();

	/**
	 * Bagli bulundugu Controller' i alarak ayaga kalkar
	 * 
	 * @see IController
	 */
	public AbstractEditor(IController<M> controller) {
		this.controller = controller;

		isSingleForm = controller.getBrowser().isSingleForm();
		
		if (! isSingleForm) {
			if (mainWindow == null) {
				init();
				Size size = getSize();
	
				mainWindow = new Window();
				mainWindow.setPlain(true);
				mainWindow.setModal(true);
				mainWindow.setResizable(false);
				mainWindow.setBlinkModal(true);
				mainWindow.setHeading(getTitle());
				mainWindow.setLayout(new FitLayout());
				mainWindow.setTopComponent(getToolBar());
				mainWindow.add(getMainPanel());
				
				if (size != null) {
					if (size.width < 350) size.width = 350;
					if (size.height < 140) size.height = 140;
					mainWindow.setSize(size.width, size.height);
				}
			}
		}
		
		controller.fireEvent(new Event(EventType.InitEditor));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void popup(M model) {
		this.model = model;
		if (this.model == null) this.model = getNewModel();
		if (this.model == null) clearFields();

		if (! isSingleForm) mainWindow.show();
		
		popFields();
		if (getFirstField() != null) getFirstField().focus();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cancel() {
		if (mainWindow == null) {
			popup(model);
		} else {
			mainWindow.hide();
		}
	}

	/**
	 * Form paneline yeni bir veri giris alani ekler
	 * 
	 * @param widget
	 */
	protected void addFieldToForm(Field<?> field) {
		field.addKeyListener(new KeyListener() {
			
			@Override
			public void componentKeyPress(ComponentEvent event) {
				if (event.isControlKey() && event.getKeyCode() == 119) { //F8
					if (saveAndContinueButton != null && saveAndContinueButton.isEnabled()) 
						saveAndContinueButton.fireEvent(Events.Select);
				}
				if (event.isControlKey() && event.getKeyCode() == 120) { //F9
					if (saveAndCloseButton != null && saveAndCloseButton.isEnabled()) 
						saveAndCloseButton.fireEvent(Events.Select);
				}
			}
		});
		
		fieldList.add(field);
		
		getFormPanel().add(field, formData);
	}
	
	/**
	 * Formdaki alanlari temizler
	 */
	private void clearFields() {
		for (Field<?> field: fieldList) {
			field.clear();
		}
	}

	/**
	 * Form paneline yeni bir veri widget ekler
	 * 
	 * @param widget
	 */
	protected void addWidgetToForm(Widget widget) {
		getFormPanel().add(widget, formData);
	}

	/**
	 * Ana panele yeni bir widget ekler
	 * 
	 * @param widget
	 * @param rowData - layout bilgisi (yerlesim sekli)
	 */
	protected void addWidgetToPanel(Widget widget, RowData rowData) {
		getMainPanel().add(widget, rowData);
	}

	/**
	 * Form bilesenlerinin yerlestigi paneli doner
	 * 
	 * @return FormPanel - veri girsilerinin yapildigi panel
	 */
	public FormPanel getFormPanel() {
		if (this.form == null) {
			form = new FormPanel();
			form.setHeaderVisible(false);
			form.setLabelWidth(100);
			form.setFrame(true);
		}
		return this.form;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Butun bilesenlerin yerlestirildigi ana panel
	 * 
	 * @return ContentPanel - ana panel
	 */
	@Override
	public ContentPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new ContentPanel();
			mainPanel.setLayout(new RowLayout(Orientation.VERTICAL));
			mainPanel.setHeaderVisible(false);
			mainPanel.setFrame(true);
			
			if (isSingleForm) {
				init();
				Size size = getSize();
				mainPanel.setSize(size.width, size.height);
				
				if (getAddNewButton() != null) {
					getAddNewButton().fireEvent(Events.Select);
					form.addButton(getAddNewButton());
				}
				
				if (getCancelButton() != null) form.addButton(getCancelButton());
				if (getSaveButton() != null) form.addButton(getSaveButton());
			}
		}
		return mainPanel;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Tamam ve Vazgec butonlarinin bulundugu toolbar donulur
	 * 
	 * @return ToolBar - butonlarin yerlestirildigi bar
	 */
	public ToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new ToolBar();
			buildToolBar();
		}
		return toolBar;
	}

	/**
	 * Ilk yukleme kontrolu bu metoda bakilarak yapilir.
	 * 
	 * @see #isInitialize()
	 * @see #init()
	 */
	protected boolean isInitialize() {
		if (!initialize) {
			initialize = true;
			return false;
		}
		return initialize;
	}

	/**
	 * Bagli oldugu Controller' i doner
	 * 
	 * @see IController
	 */
	protected IController<M> getController() {
		return this.controller;
	}

	/**
	 * Kaydet ve Yenisi ile devam et butonu, farklı gerceklestirimler icin 
	 * public erisim verildi, Yetkiye dayali olarak Controller siniflarindan 
	 * kaldirilabilir
	 * 
	 * @see #getSaveAndCloseButton()
	 * @see #getCancelButton()
	 */
	protected Button getSaveAndContinueButton() {
		if (saveAndContinueButton == null) {
			saveAndContinueButton = new Button(Tags.get("saveAndContinue"),
				AbstractImagePrototype.create(BaseImages.Instance.saveAndContinue()),
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						String validationMsg = isValid();
						if (validationMsg != null && !validationMsg.isEmpty()) {
							Info.display(Tags.get("error"), validationMsg);
							return;
						}

						pushFields();
						getController().fireEvent(new Event(EventType.SaveAndContinue, model));
					}

				});
			saveAndContinueButton.setToolTip("Ctrl + F8");
		}

		return saveAndContinueButton;
	}

	/**
	 * Kaydet ve Kapat butonu, farkli gerceklestirimler icin public erisim
	 * verildi, Yetkiye dayali olarak Controller siniflarindan kaldirilabilir
	 * 
	 * @see #getSaveAndContinueButton()
	 * @see #getCancelButton()
	 */
	protected Button getSaveAndCloseButton() {
		if (saveAndCloseButton == null) {
			saveAndCloseButton = new Button(Tags.get("saveAndClose"),
				AbstractImagePrototype.create(BaseImages.Instance.saveAndClose()),
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						String validationMsg = isValid();
						if (validationMsg != null && !validationMsg.isEmpty()) {
							Info.display(Tags.get("error"), validationMsg);
							return;
						}

						pushFields();
						getController().fireEvent(new Event(EventType.SaveAndClose, model));
					}

				});
			saveAndCloseButton.setToolTip("Ctrl + F9");
		}

		return saveAndCloseButton;
	}

	/**
	 * Yeni kayit ekleme butonu
	 * 
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
	 * Kaydet butonu, farkli gerceklestirimler icin public erisim
	 * verildi, Yetkiye dayali olarak Controller siniflarindan kaldirilabilir
	 * 
	 * @see #getSaveAndContinueButton()
	 * @see #getSaveAndCloseButton()
	 * @see #getCancelButton()
	 */
	protected Button getSaveButton() {
		if (saveButton == null) {
			saveButton = new Button(Tags.get("save"),
				AbstractImagePrototype.create(BaseImages.Instance.saveAndContinue()),
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						String validationMsg = isValid();
						if (validationMsg != null && !validationMsg.isEmpty()) {
							Info.display(Tags.get("error"), validationMsg);
							return;
						}

						if (model != null) {
							pushFields();
							if (model.getId() == null) {
								getController().fireEvent(new Event(EventType.SaveAndContinue, model));
							} else {
								getController().fireEvent(new Event(EventType.SaveAndClose, model));
							}
						} else {
							Info.display(Tags.get("error"), Tags.get("addNewNotAllowed"));
						}
					}

				});
		}

		return saveButton;
	}

	/**
	 * Formu kapatma butonu
	 * 
	 * @see #getOKButton()
	 */
	protected Button getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new Button(Tags.get("cancel"),
				AbstractImagePrototype.create(BaseImages.Instance.cancel()),
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						getController().fireEvent(new Event(EventType.Cancel));
					}

				});
			cancelButton.setToolTip("Esc");
		}

		return cancelButton;
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
	 * @see #getOKButton()
	 * @see #getCancelButton()
	 */
	private void buildToolBar() {
		
		Button[] buttons = getExtraButtons();
		if (buttons != null) {
			for (Button btn: buttons) {
				toolBar.add(btn);
			}
		}
		
		if (getSaveAndContinueButton() != null) toolBar.add(getSaveAndContinueButton());
		toolBar.add(new SeparatorToolItem());
		if (getSaveAndCloseButton() != null) toolBar.add(getSaveAndCloseButton());
		toolBar.add(new SeparatorToolItem());
		if (getCancelButton() != null) toolBar.add(getCancelButton());
	}

}
