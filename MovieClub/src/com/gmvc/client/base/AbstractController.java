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

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.gmvc.client.enums.RightType;
import com.gmvc.client.meta.EffectedDetail;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.EventType;
import com.gmvc.client.meta.FilterItem;
import com.gmvc.client.util.Tags;
import com.gmvc.client.util.UserController;

/**
 * Temel Controller metodlarini gercekler, bir alt duzeydeki soyut Controller
 * siniflari icin hazir yapi sunan soyut siniftir
 * 
 * @see IModel
 * @see IController
 * 
 * @author mdpinar
 * 
 */
public abstract class AbstractController<M extends IModel> implements IController<M> {

	/**
	 * Kontrol ettigi Browser
	 * 
	 * @see IBrowser
	 */
	private IBrowser<M> browser;

	/**
	 * Kontrol ettigi Editor
	 * 
	 * @see IEditor
	 */
	private IEditor<M> editor;

	/**
	 * Master-Detail yapisindaki modellerde;
	 * 
	 * <p/>
	 * Detay satirlarinda degisiklik olursa (add, delete, update)
	 * bu listeye degisikligin tipine gore model siniflar eklenerek
	 * server tarafindaki save metoduna bildirilir
	 * 
	 */
	private List<EffectedDetail> effectedDetailList;
	
	/**
	 * Browser'un create edildigi metod (birkez cagrilacak)
	 * 
	 * @see #getBrowser()
	 * @see IBrowser
	 */
	public abstract IBrowser<M> createBrowser();

	/**
	 * Editor'un create edildigi metod (birkez cagrilacak)
	 * 
	 * @see #getEditor()
	 * @see IEditor
	 */
	public abstract IEditor<M> createEditor();
	
	/**
	 * MiniMVC icin Model eklenir (insert)
	 * 
	 * @see IModel
	 */
	abstract void add(M model);

	/**
	 * MiniMVC icin Model guncellenir (update)
	 * 
	 * @see IModel
	 */
	abstract void update(M model);
	
	/**
	 * Model kaydedilir (insert/update)
	 * 
	 * @see IModel
	 */
	abstract void save(M model, boolean refresh, List<EffectedDetail> effectedDetailList);


	/**
	 * Uzerinde calisilan Model silinir
	 * 
	 * @see IModel
	 */
	abstract void remove(M model);

	/**
	 * Modellerin listelendigi Grid, tazelenir
	 * 
	 * @see IModel
	 * @see IBrowser
	 * @see FilterItem
	 */
	abstract void refresh(final FilterItem filter);

	/**
	 * Sadece MiniMVC yapisinda kullaniliyor!
	 * 
	 * <p/>
	 * Kayit isleminde gecici siniflarda yapilan duzenlemeler model uzerine
	 * yansitilir Insert-Update-Delete
	 * 
	 */
	abstract void doUpdates();

	/**
	 * Sadece MiniMVC yapisinda kullaniliyor!
	 * 
	 * <p/>
	 * Orijinal modelden gecici modellere aktarim yapan metoddur
	 * 
	 */
	abstract void setModelList(List<M> modelList);

	/**
	 * Kaydet ve Yeni butonuna hic basilmis mi?
	 * Basilmissa, form kapandiktan sonra, browserdaki grid refreshlenecek
	 * 
	 */
	private boolean anySaveAndContinueButtonPressed;
	
	@Override
	public IBrowser<M> getBrowser() {
		if (browser == null) {
			browser = createBrowser();
		}
		return browser;
	}

	@Override
	public IEditor<M> getEditor() {
		if (editor == null) {
			editor = createEditor();
		}
		return editor;
	}

	@Override
	public ContentPanel popup() {
		return getBrowser().popup();
	}

	/**
	 * fireEvent metodu ilk once bu metodu cagirir. Herhangi bir yerde bir
	 * olayin tetiklenmesinden <strong>ONCE</strong> birseyler yapilacaksa bu
	 * metod ezilebilir
	 * 
	 * @param Event
	 * 
	 * @see #fireEvent(Event) 
	 * @see #afterEvent(Event)
	 */
	public void beforeEvent(Event event) {

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void fireEvent(Event event) {

		if (event != null) {

			beforeEvent(event);

			if (!event.isCancel()) { // bir nedenden dolayi vazgecilmemisse

				final M model = (M) event.getModel();
				switch (event.getType()) {

					case EventType.AddNew: {
						if (UserController.hasRight(getRight(), RightType.AddNew)) {
							beforeShow();
							getEditor().popup(null); // yeni kayit
						} else {
							Info.display(Tags.get("error"), Tags.get("addNewNotAllowed"));
						}
						break;
					}
	
					case EventType.Show: {
						if (UserController.hasRight(getRight(), RightType.Read)) {
							beforeShow();
							getEditor().popup(getBrowser().getSelectedModel()); // update icin okuma islemi
						} else {
							Info.display(Tags.get("error"), Tags.get("readNotAllowed"));
						}
						break;
					}
	
					case EventType.Delete: {
						if (UserController.hasRight(getRight(), RightType.Delete)) {
							if (getBrowser().getDeleteQueryPart() != null) {
								MessageBox.confirm(
										Tags.get("confirm"), "<b>" + 
										getBrowser().getDeleteQueryPart() + " </b>" + Tags.get("deleteMessage"),
										new Listener<MessageBoxEvent>() {
		
											@Override
											public void handleEvent(MessageBoxEvent be) {
												if (be.getButtonClicked().getText().equals("Yes")) {
													remove(model);
													if (getBrowser().isSingleForm()) fireEvent(new Event(EventType.AddNew));
												}
											}
										});
							} else {
								remove(model);
							}
						} else {
							Info.display(Tags.get("error"), Tags.get("deleteNotAllowed"));
						}
						break;
					}
	
					case EventType.SaveAndClose:
					case EventType.SaveAndContinue:{
						boolean isNewModel = (model != null ? model.getId() == null : true);
						
						if (! isNewModel &&
							! UserController.hasRight(getRight(), RightType.Update)) {
								Info.display(Tags.get("error"), Tags.get("updateNotAllowed"));
								break;
						}
						if (isNewModel &&
							! UserController.hasRight(getRight(), RightType.AddNew)) {
								Info.display(Tags.get("error"), Tags.get("addNewNotAllowed"));
								break;
						}

						if (this instanceof AbstractMiniController<?>) {
							update(model);
						} else {		
							if (event.getType() == EventType.SaveAndClose) {
								save(model, true, getEffectedDetailList());
							} else {
								save(model, getBrowser().isSingleForm(), getEffectedDetailList());
								anySaveAndContinueButtonPressed = true;
							}
						}
						break;
					}
					
					case EventType.AddNewModel: { // mini browserlardan geliyor
						if (UserController.hasRight(getRight(), RightType.AddNew)) {
							add(model); // insert ve update ayni sekilde ele alinacak
							if (getEditor() != null) getEditor().cancel();
						} else {
							Info.display(Tags.get("error"), Tags.get("addNewNotAllowed"));
						}
						break;
					}
					
					case EventType.UpdateModel: { // mini browserlardan geliyor
						if (UserController.hasRight(getRight(), RightType.Update)) {
							update(model); // insert ve update ayni sekilde ele alinacak
							if (getEditor() != null) getEditor().cancel();
						} else {
							Info.display(Tags.get("error"), Tags.get("updateNotAllowed"));
						}
						break;
					}
	
					case EventType.Refresh: {
						refresh((FilterItem) event.getParam("filter"));
						break;
					}
	
					case EventType.Cancel: {
						if (getEditor() != null) getEditor().cancel();
						if (anySaveAndContinueButtonPressed) {
							refresh(null);
							anySaveAndContinueButtonPressed = false;
						}
						break;
					}
	
					case EventType.MiniSetModels: { // mini controllerlardan geliyor
						if (event.getParam("modelList") != null)
							setModelList((List<M>) event.getParam("modelList"));
						else
							setModelList(null);
						break;
					}
	
					case EventType.MiniDoUpdates: { // mini controllerlardan geliyor
						doUpdates();
						break;
					}
					
					default: { // ozel bir event ise
						holdSpecialEvent(event);
					}

				}

				afterEvent(event);
			}
		}

	}

	/**
	 * fireEvent metodu en son bu metodu cagirir. Herhangi bir yerde bir olayin
	 * tetiklenmesinden <strong>SONRA</strong> birseyler yapilacaksa bu metod
	 * ezilebilir
	 * 
	 * @param Event
	 * 
	 * @see #fireEvent(Event) 
	 * @see #beforeEvent(Event)
	 */
	public void afterEvent(Event event) {
		;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 300;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEffectedDetail(EffectedDetail effectedDetail) {
		effectedDetailList.add(effectedDetail);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EffectedDetail> getEffectedDetailList() {
		return effectedDetailList;
	}

	/**
	 * fireEvent metodundaki standart Event larin disinda kontrol edilecek ozel
	 * bir Event varsa, bu metod otomatik olarak cagrilir.
	 * 
	 * <p/>
	 * <strong>OZEL OLAYLAR</strong> denetlenecekse bu metod ezilebilir
	 * 
	 * @param Event
	 * 
	 * @see #fireEvent(Event)
	 */
	public void holdSpecialEvent(Event event) {
		System.out.println("AbstractController -> Special event trigged : " + event.getType());
	}

	/**
	 * Model sinif editor uzerinde gosterilmeden hemen once bu metod cagrilir
	 */
	private void beforeShow() {
		//detay satirlardaki degisiklikler icin etkilenmis detaylar listesi bosaltiliyor
		effectedDetailList = new ArrayList<EffectedDetail>();
	}
	
}
