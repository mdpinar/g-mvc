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
 * Film Tanitim mesajlarini icerir
 * 
 * @author mdpinar
 * 
 */
public interface MovieTags extends Constants {

	String movie();
	String changeStatus();
	String givenMovies();
	String incomingMovies();
	String director();
	String actors();
	String prodYear();
	String lastStatus();
	String copy();
	String copyCount();
	String copyCountMessage();
	String status();
	String inStore();
	String rent();
	String sell();
	String present();
	String junk();
	String lost();
	
}
