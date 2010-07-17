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
package com.gmvc.client.app;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.widget.Info;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.EventType;
import com.gmvc.client.util.Tags;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Uygulama bazindaki ust olaylarin denetimini yapan Controller sinifidir.
 * 
 * Uygulamanin ilk yuklenisi, Login formun acilmasi, ... gibi
 * 
 * @author mdpinar
 * 
 */
public final class AppController {

	/**
	 * Uygulama ana formunu tasarlayan sinifin ornegi, menu sistemi bu form uzerinde bulunur
	 * 
	 */
	private static AppView appView;
	
	/**
	 * Yeni bir ornegi alinamaz, alinmamali
	 * 
	 */
	private AppController() {
		;
	}

	/**
	 * Uygulama bazindaki olaylari denetler
	 * 
	 * @see AppEventType
	 */
	public static void fireEvent(Event event) {

		switch (event.getType()) {

			case EventType.InitApp: {
				BaseAppModel appModel = event.getParam("appModel");
				appView = new AppView(appModel);
				break;
			}
	
			case EventType.ShowLoginForm: {
				getAppModel().getLoginDialog().show();
				break;
			}
	
			case EventType.Login: {
				if (Registry.get("user") != null) {
					getAppModel().getLoginDialog().hide();
					RootPanel.get().add(appView.getViewport());
					appView.treePanel.expandAll();
					
					ContentController.fireEvent(
						new Event(EventType.ShowContent)
							.addParam("wellcomeTab", true)
							.addParam("popupable", 
								new WellcomePage(appView.appModel.getWellcomePanel())));
					
				} else {
					Info.display(Tags.get("error"), Tags.get("invalidLogin"));
				}
				break;
			}
	
			case EventType.Logout: {
				Registry.unregister("user");
				RootPanel.get().remove(appView.getViewport());
				appView.destroyViewport();
				getAppModel().getLoginDialog().show();
				break;
			}
		}

	}

	/**
	 * Baska siniflar icin uygulama verilerine erisim izni verir 
	 */
	public static BaseAppModel getAppModel() {
		return appView.appModel;
	}
	
}
