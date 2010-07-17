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
package com.gmvc.client.meta;

import java.io.Serializable;

/**
 * Server tarafinda tetiklenen islemlerin sonuclarini donen ortak bir siniftir
 * 
 * @author mdpinar
 * 
 */
public class RetVal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Etkilenen kayit sayisi
	 * 
	 */
	private int effectedRows;
	
	/**
	 * Donulen mesaj (hata ya da baska birsey olabilir)
	 */
	private String message;

	/**
	 * Serilestirilecegi icin hazir kurucu metod olmak zorunda
	 */
	public RetVal() {
		;
	}

	public RetVal(int effectedRows) {
		this.effectedRows = effectedRows;
	}

	public RetVal(String message) {
		this.message = message;
	}
	
	public int getEffectedRows() {
		return effectedRows;
	}
	
	public void setEffectedRows(int effectedRows) {
		this.effectedRows = effectedRows;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
