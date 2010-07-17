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

import com.gmvc.client.app.AppController;
import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * Coklu dil destegi icin, uygulama tarafindaki "Etiket Sabitleri Yapisi" ni tutar
 * 
 * @author mdpinar
 * 
 */
public class Tags {

	/**
	 * GWT de genel kullanim, arabirimi direkt olarak kullanmaktir
	 * 
	 * </p>
	 * Arabirimi dogrudan kullanamayacagimiz icin Lookup olarak erisilir
	 *  
	 */
	private static ConstantsWithLookup instance;
	
	static {
		instance = AppController.getAppModel().getTags();
	}
	
	/**
	 * Verilen etiketin degerini doner
	 */
	public static String get(String key) {
		return instance.getString(key);
	}
	
}
