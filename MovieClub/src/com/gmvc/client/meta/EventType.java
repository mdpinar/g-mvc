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

/**
 * MVC haberlesmelerindeki olaylarin tipleri
 * 
 * @author mdpinar
 * 
 */
public interface EventType {

	//Genel amacli
	public static final int Overall = 0;
	public static final int Refresh = 1;
	public static final int GetReport = 2;
	
	//Uygulama acilis, ilk yukleme ve kullanici giris/cikislari
	public static final int InitApp = 10;
	public static final int ShowLoginForm = 11;
	public static final int Login = 12;
	public static final int Logout = 13;
	
	//Form hareketleri
	public static final int InitController = 20;
	public static final int InitEditor = 21;
	public static final int InitBrowser = 22;
	
	public static final int ShowContent = 23;
	public static final int ResizeContent = 24;

	//Ana panel islemleri
	public static final int BuildMainPanel = 30;
	public static final int DestroyMainPanel = 31;
	
	//Standart MVC icin
	public static final int Show = 40;
	public static final int AddNew = 41;
	public static final int Delete = 42;
	public static final int Close = 43;
	public static final int SaveAndClose = 44;
	public static final int SaveAndContinue = 45;
	public static final int Cancel = 46;

	//MiniMVC icin
	public static final int AddNewModel = 50;
	public static final int UpdateModel = 51;
	public static final int MiniDoUpdates = 52;
	public static final int MiniSetModels = 53;
	public static final int GridEvent = 54;

}
