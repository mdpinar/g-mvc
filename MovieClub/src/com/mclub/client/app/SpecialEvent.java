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
package com.mclub.client.app;

import com.gmvc.client.meta.EventType;

/**
 * Uygulama icinde kullanilacak ozel olaylar bu sinifta tanimlanir
 * 
 * @see EventType
 * 
 * @author mdpinar
 * 
 */
public interface SpecialEvent extends EventType {

	public static final int LoadMovie = 100;
	public static final int LoadMovieGroup = 101;
	public static final int LoadJob = 102;
	public static final int LoadCustomer = 103;
	public static final int LoadLocation = 104;
	
}
