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

import java.util.List;

import com.gmvc.client.meta.EffectedDetail;
import com.gmvc.client.meta.Event;

/**
 * Model ile View arasindaki mantiksal islemlerinin yapildigi kisimdir. View
 * kismi ile olay temelli haberlesir.
 * 
 * <p/>
 * Ana menude Browser' i gosterilebilmek ve hak denetimleri yapabilmek icin
 * Popupable arayuzunu gercekler. popup() metodu Browser daki popup() metoduna
 * dogrudan cagrida bulunur.
 * 
 * @see IModel
 * @see IBrowser
 * @see IEditor
 * @see Popupable
 * @see Event
 * 
 * @author mdpinar
 * 
 */
public interface IController<M extends IModel> extends Popupable {
	
	/**
	 * Kullandigi Browser' i doner
	 * 
	 * @see IBrowser
	 * @return Browser
	 */
	IBrowser<M> getBrowser();

	/**
	 * View' in diger parcasi olan Editor' u doner
	 * 
	 * @see IEditor
	 * @return Editor
	 */
	IEditor<M> getEditor();

	/**
	 * Browser ve Editor, Controller ile haberlesmek icin bu metodu kullanilir.
	 * 
	 * @see Event
	 */
	void fireEvent(Event event);
	
	/**
	 * Master - Detail yapisindaki formlarda;
	 * 
	 * Master in altindaki her bir Detail turu icin "Degisiklikler Liste" sini ekler
	 */
	void addEffectedDetail(EffectedDetail effectedDetail);

	/**
	 * Master - Detail yapisindaki formlarda;
	 * 
	 * Master in altindaki Detail turleri icin "Degisiklikler Liste" lerini doner
	 */
	List<EffectedDetail> getEffectedDetailList();
	
}
