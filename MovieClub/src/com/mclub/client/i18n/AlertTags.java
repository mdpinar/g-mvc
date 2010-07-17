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
 * Hata mesajlarini icerir
 * 
 * @author mdpinar
 * 
 */
public interface AlertTags extends Constants {

	String cantDeleteThisCustomer();
	String cantDeleteThisMovie();
	String cantDeleteThisAction();
	String movieHadBeenProcess();
	String haveToAddMinCopy();
	String haveToAddMinDetail();
	String nameCanNotBeEmpty();
	String customerCanNotBeEmpty();
	String movieCanNotBeEmpty();
	String newAndReTypePasswordAreNotEquals();
	String oldPasswordCanNotBeEmpty();
	String newPasswordCanNotBeEmpty();
	String oldPasswordInvalid();
	String badEmailMessage();
	String generalErrorMessage();
	String dontDeletedMessage();
	String notAllowed();
	String invalidLogin();
	String cannotDeleteAdminUser();
	String addNewNotAllowed();
	String readNotAllowed();
	String updateNotAllowed();
	String deleteNotAllowed();
	String executeNotAllowed();

}
