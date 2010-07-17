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

import com.gmvc.client.meta.Event;

/**
 * Rapor formlarinda View ve Controller siniflari mevcuttur (model yoktur). View
 * kismi sadece gorsel islemler ile ilgili isleri yaparken, Bu siniflarda
 * mantiksal islemler yapilir.
 * 
 * <p/>
 * Ana menude gosterilebilmek ve kullanici hak denetimleri icin Popupable arayuzunu 
 * gercekler. popup() metodu, View daki popup() metoduna dogrudan cagrida bulunur.
 * 
 * <p/>
 * Yapilar arasi iletisim icin olay temelli programlama tercih edildi!
 * 
 * @see IModel
 * @see IReportView
 * @see Popupable
 * @see Event
 * 
 * @author mdpinar
 * 
 */
public interface IReportController extends Popupable {

	/**
	 * Kisitlarinin girilip raporlarin alinabildigi formu doner
	 * 
	 * @see IReportView
	 * @return ReportView
	 */
	IReportView getView();

	/**
	 * View' deki olaylarin Controller' a bildirimleri icin bu metod kullanilir.
	 * 
	 */
	void fireEvent(Event event);

}
