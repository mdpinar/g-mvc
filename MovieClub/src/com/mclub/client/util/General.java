/*
 * Copyleft 2010 Mustafa DUMLUPINAR
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 * http://www.gnu.org/licenses/gpl.html
 *
 * dumlupinar01@gmail.com
 * http://mdpinar.blogspot.com/
 *
 */
package com.mclub.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Genel yardimci metodlari sunan siniftir
 * 
 * @author mdpinar
 * 
 */
public class General {

	/**
	 * Verilen tarihi yyyy-MM-dd formatinda String olarak doner
	 * 
	 * @param date
	 * 
	 * @return String farmatli tarih
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}
	
	/**
	 * Verilen tarihi verilen formatda String olarak doner
	 * 
	 * @param date
	 * @param format
	 * 
	 * @return String formatli tarih
	 */
	public static String formatDate(Date date, String format) {
		return DateTimeFormat.getFormat(format).format(date);
	}

	/**
	 * Verilen sayinin son iki hanesini yuvarlar
	 * 
	 * @param value
	 * 
	 * @return double yuvarlanmis sayi
	 */
	public static double roundTwoDecimals(double value) {
		int ix = (int) (value * 100.0);
		return ((double) ix) / 100.0;
	}
	
}
