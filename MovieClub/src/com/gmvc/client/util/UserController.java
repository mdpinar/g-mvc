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
import com.gmvc.client.enums.RightType;
import com.gmvc.client.meta.IUserController;
import com.gmvc.client.meta.Right;

/**
 * Kullanici login ve hak denetimlerini yapan araci siniftir
 * 
 * @author mdpinar
 * 
 */
public class UserController {
	
	/**
	 * Delegsayon modeli ile kontrolleri yapacagimiz instance 
	 */
	private static IUserController instance;
	
	/**
	 * Uygulama tarafinda belirtilen kontrol sinifini alir
	 * 
	 */
	static {
		instance = AppController.getAppModel().getUserController();
	}
	
	/**
	 * @see IUserController#login(String, String)
	 */
	public static void login(String username, String password) {
		instance.login(username, password);
	}
	
	/**
	 * @see IUserController#hasAllRight()
	 */
	public static boolean hasAllRight() {
		return instance.hasAllRight();
	}
	
	/**
	 * @see IUserController#hasRight(Right)
	 */
	public static boolean hasRight(Right right) {
		return instance.hasRight(right);
	}
	
	/**
	 * @see IUserController#hasRight(Right, RightType)
	 */
	public static boolean hasRight(Right right, RightType type) {
		return instance.hasRight(right, type);
	}
	
}
