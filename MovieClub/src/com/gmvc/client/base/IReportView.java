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
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.gmvc.client.enums.ReportUnit;

/**
 * Rapor kisitlarinin girilebildigi formlarin uretildigi ortak arayuzdur
 * 
 * @author mdpinar
 * 
 */
public interface IReportView {

	/**
	 * Formun gosterimini saglar.
	 * 
	 * @return ContentPanel - ana panel
	 */
	ContentPanel popup();

	/**
	 * Kisitlarin satirlar halinde yer aldigi form
	 * 
	 * @return FormPanel - form paneli
	 */
	FormPanel getPanel();

	/**
	 * Form basligi
	 * 
	 * @return title
	 */
	String getReportName();

	/**
	 * Rapor cikis unitesi
	 * 
	 * @see ReportUnit
	 * @return ReportUnit - html, xls, csv, pdf...
	 */
	ReportUnit getReportUnit();

	/**
	 * Rapora eklenecek Having parcasi icin kullanilir
	 * 
	 */
	String getHavingString();

	/**
	 * Rapordaki query' e ek olarak eklenecek sorgu parcasi icin kullanilir
	 * 
	 * @return String - raporu olusturacak olan ek sql kisitlari
	 */
	String getQueryString();

}
