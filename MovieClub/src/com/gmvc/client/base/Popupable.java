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
import com.gmvc.client.meta.Right;
import com.google.gwt.resources.client.ImageResource;

/**
 * Ana panelde (tabpanel) gosterilecek tum formlar icin ortak arayuzdur.
 * 
 * @author mdpinar
 * 
 */
public interface Popupable {

	/**
	 * Gosterilecek formu doner
	 * 
	 * @return form
	 */
	ContentPanel popup();

	/**
	 * Kullanici hak kontrollerinde kullanilmak uzere formun iznini doner. Form
	 * icin hak kontrolu yapilmiyacaksa Void donulmelidir
	 * 
	 * @see Right
	 * @return Right
	 */
	Right getRight();

	/**
	 * Ana panelde gorunecek basligi doner.
	 * 
	 * @return title
	 */
	String getTitle();

	/**
	 * Formun simgesini doner.
	 * 
	 * @return ImageResource
	 */
	ImageResource getIcon();

	/**
	 * Formun genislik bilgisini doner.
	 * 
	 * <p/>
	 * Bu degerin 0 (sifir) verilmesi durumunda, genislik ana panel genisligi
	 * kadar olacaktir. Ana form genisliginin degismesi durumunda, bu formun
	 * genisligi de otomatik olarak degisecektir
	 * 
	 */
	int getWidth();

	/**
	 * Formun yukseklik bilgisini doner.
	 * 
	 * <p/>
	 * Bu degerin 0 (sifir) verilmesi durumunda, yukseklik ana panel
	 * yukseklikligi kadar olacaktir. Ana form yuksekliginin degismesi durumunda,
	 * bu formun yuksekligi de otomatik olarak degisecektir
	 * 
	 */
	int getHeight();

}
