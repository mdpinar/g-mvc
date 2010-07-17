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

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

/**
 * Model duzenlemelerinin yapildigi siniflardir.
 * 
 * <p/>
 * View kismi iki yapidan meydana gelir; Browser ve Editor. Editor, View
 * tarafinda yer alan ve verileri isledigimiz kisimdir. Create-Read-Update
 * islemleri bu formlarda yapilir.
 * 
 * @see IModel
 * @see IBrowser - view' i olusturan diger yapi
 * @see IController
 * 
 * @author mdpinar
 * 
 */
public interface IEditor<M extends IModel> {

	/**
	 * Form basligini doner
	 * 
	 * @return title
	 */
	String getTitle();

	/**
	 * Formun gosterimini saglar.
	 * 
	 * <p/>
	 * Yeni eklenecek kayitlar icin parametre degeri olarak null verilir.
	 * Gosterim/duzenleme yapilacak kayitlar icin ilgili model verilir.
	 * 
	 * @param IModel - islenecek kayit
	 */
	void popup(M model);
	
	/**
	 * Forma girilen bilgilerin dogrulukları kontrol edilir, sorun varsa false,
	 * yoksa true donulur.
	 * 
	 * <p/>
	 * True donulmesi durumunda kayit islemi onaylanir aksi durumda hata mesaji
	 * verilir.
	 * 
	 * <p/>
	 * Bu metoda dogrudan cagri yapilmayacak, kayit isleminden hemen once
	 * otomatik cagrilacaktir.
	 * 
	 * @see AbstractController#fireEvent(Event)
	 * @return error mesaji
	 */
	String isValid();

	/**
	 * Yerlesim duzenin degisimi / degisik bilesenlerin eklenebilmesi icin
	 * 
	 * Editor uzerindeki ana panele erisim verir
	 */
	ContentPanel getMainPanel();
	
	/**
	 * En ustte cikacak olan form yonetim toolbari
	 * 
	 */
	ToolBar getToolBar();
	
	/**
	 * Yapilan degisiklikler <strong>onaylanmadan</strong> form kapatilir.
	 * 
	 */
	void cancel();

}
