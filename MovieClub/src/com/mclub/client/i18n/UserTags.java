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
 * Kullanici Tanitim ve Hak Denetimi mesajlarini icerir
 * 
 * @author mdpinar
 * 
 */
public interface UserTags extends Constants {

	String logout();
	String username();
	String password();
	String oldPassword();
	String newPassword();
	String rePassword();
	String userType();
	String rights();
	String hasExecute();
	String hasRead();
	String hasAddNew();
	String hasUpdate();
	String hasDelete();
	String actionRights();
	String formRights();
	String reportRights();
	String allReads();
	String allAddNews();
	String allUpdates();
	String allDeletes();
	String allExecutes();

}
