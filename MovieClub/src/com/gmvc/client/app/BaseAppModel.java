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
package com.gmvc.client.app;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.gmvc.client.meta.IUserController;
import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * Uygulama bazindaki verileri sunan siniftir
 * 
 * </p>
 * Uygulamada mutlaka bu sinifi gercekleyen bir sinif olmalidir
 * 
 * </p>
 * GMVC modulu icin proje tarafindaki gerekli olan bilgiler bu sinifi uzatan sinif 
 * araciligi ile elde edilecek
 * 
 * @author mdpinar
 */
public class BaseAppModel {

	/**
	 * Ilk acilis esnasinda kullanici onayi icin acilacak olan login formu
	 */
	private Dialog loginDialog;
	
	/**
	 * Coklu dil destegi icin kullanilan mesaj etiketleri arabirimi
	 */
	private ConstantsWithLookup tags;
	
	/**
	 * Kullanici hak denetimlerini yapacak olan arabirim
	 */
	private IUserController userController;
	
	/**
	 * Coklu dil destegi icin combo sunar
	 */
	private Component langListWidget;
	
	/**
	 * AppView de gosterilecek olan menu maddeleri listesi
	 */
	private List<BaseTreeModel> menuList;
	
	/**
	 * Her zaman ilk tab olacak, menu acilislarinda kullaniciyi karsilayacak olan panel
	 * 
	 */
	private ContentPanel wellcomePanel;
	
	public Dialog getLoginDialog() {
		return loginDialog;
	}

	public void setLoginDialog(Dialog loginDialog) {
		this.loginDialog = loginDialog;
	}

	public ConstantsWithLookup getTags() {
		return tags;
	}

	public void setTags(ConstantsWithLookup tags) {
		this.tags = tags;
	}

	public IUserController getUserController() {
		return userController;
	}

	public void setUserController(IUserController userController) {
		this.userController = userController;
	}

	public List<BaseTreeModel> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<BaseTreeModel> menuList) {
		this.menuList = menuList;
	}

	public Component getLangListWidget() {
		return langListWidget;
	}

	public void setLangListWidget(Component langListWidget) {
		this.langListWidget = langListWidget;
	}

	public ContentPanel getWellcomePanel() {
		return wellcomePanel;
	}

	public void setWellcomePanel(ContentPanel wellcomePanel) {
		this.wellcomePanel = wellcomePanel;
	}

}
