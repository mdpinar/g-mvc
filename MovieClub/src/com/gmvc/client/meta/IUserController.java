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

import com.gmvc.client.enums.RightType;

/**
 * Kullanici icin login ve hak denetimlerinin yapildigi arabirimdir
 * 
 * @author mdpinar
 * 
 */
public interface IUserController {

	/**
	 * Login islemi
	 * 
	 * @param username
	 * @param password
	 */
	void login(String username, String password);

	/**
	 * Admin turunde bir kullanici mi
	 */
	boolean hasAllRight();
	
	/**
	 * Sorulan hakki var mi, burda execute tipinde yetki denetimi yapilir
	 * 
	 * @param right
	 */
	boolean hasRight(Right right);
	
	/**
	 * Sorulan hakki icin verilen tipte yetkisi var mi
	 * 
	 * @param right
	 * @param type
	 * @return
	 */
	boolean hasRight(Right right, RightType type);
	
}
