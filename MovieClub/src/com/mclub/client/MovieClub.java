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
package com.mclub.client;

import com.gmvc.client.app.AppController;
import com.gmvc.client.meta.Event;
import com.google.gwt.core.client.EntryPoint;
import com.mclub.client.app.AppModel;
import com.mclub.client.app.SpecialEvent;

/**
 * Projenin giris noktasi
 * 
 * @author mdpinar
 * 
 */
public class MovieClub implements EntryPoint {

	/**
	 * Init -> ilk yuklemeler icin
	 * 
	 * <p/>
	 * ShowLoginForm -> kullanici giris denetimi icin
	 * 
	 * @see AppController#fireEvent(AppEventType)
	 * 
	 */
	public void onModuleLoad() {
		AppController.fireEvent(
			new Event(SpecialEvent.InitApp)
				.addParam("appModel", new AppModel())
		);
		AppController.fireEvent(new Event(SpecialEvent.ShowLoginForm));
	}

}
