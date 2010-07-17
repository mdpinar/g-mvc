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
package com.mclub.client.i18n;

import com.google.gwt.i18n.client.Constants;

/**
 * Musteri tanitimi mesajlarini icerir
 * 
 * @author mdpinar
 * 
 */
public interface CustomerTags extends Constants {

	String customer();
	String customerBrowserTitle();
	String mobilePhone();
	String homePhone();
	String homeAddress();
	String homeLocation();
	String workPhone();
	String workAddress();
	String workLocation();
	String customerGroup();
	String ageGroup();
	String sex();
	String work();
	String privte();

}
