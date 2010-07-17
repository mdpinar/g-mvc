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

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.gmvc.client.base.Popupable;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.EventType;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * Ana forma acilacak olan her tabpanelin kontrolunu yapar 
 *
 * @author mdpinar
 * 
 */
public final class ContentController {

	/**
	 * Ana formda gorunecek merkezi tabpanel
	 * 
	 */
	private static TabPanel mainTabPanel;
	
	/**
	 * Yeni bir ornegi alinamaz
	 * 
	 */
	private ContentController() {
		;
	}

	/**
	 * Acilabilir formlarin gorsel olaylarini denetler
	 * 
	 * @see AppEventType
	 */
	public static void fireEvent(Event event) {

		switch (event.getType()) {

			case EventType.ShowContent: {
				Popupable popupable = event.getParam("popupable");
				Boolean isWellcomeTab = event.getParam("wellcomeTab");
				
				if (popupable != null) showItem(popupable, isWellcomeTab);
				break;
			}
	
			case EventType.ResizeContent: {
				reSizeTabItem();
				break;
			}
	
			case EventType.BuildMainPanel: {
				buildMainPanel();
				break;
			}
	
			case EventType.DestroyMainPanel: {
				destroyMainPanel();
				break;
			}

		}

	}

	/**
	 * Ana tabpaneli doner
	 * 
	 * @return mainTabPanel
	 */
	static TabPanel getMainPanel() {
		return mainTabPanel;
	}

	/**
	 * Ana tabpanel olusturulur
	 * 
	 */
	private static void buildMainPanel() {
		mainTabPanel = new TabPanel();
		mainTabPanel.setMinTabWidth(100);
		mainTabPanel.setResizeTabs(true);
		mainTabPanel.setAnimScroll(true);
		mainTabPanel.setTabScroll(true);
		mainTabPanel.setCloseContextMenu(true);
		mainTabPanel.setLayoutData(new FitLayout());
	}

	/**
	 * Oturum Kapa/Ac durumlarinda, bilesen tekrarinin onune gecmek icin
	 * yuklenmis ana panel yikilir
	 * 
	 */
	private static void destroyMainPanel() {
		mainTabPanel = null;
	}

	/**
	 * Ana panele yeni bir popupable turunde form ekler, zaten ekli ise odaklanir
	 * 
	 */
	private static void showItem(Popupable popupable, Boolean isWellcomeTab) {

		TabItem item = mainTabPanel.getItemByItemId("" + popupable.getRight());

		if (item == null) {
			item = new TabItem();
			if (popupable.getRight() != null)
				item.setItemId(popupable.getRight().toString());
			else
				item.setItemId(popupable.getTitle());
			if (popupable.getIcon() != null)  item.setIcon(AbstractImagePrototype.create(popupable.getIcon()));
			item.setText(popupable.getTitle());
			item.setClosable(isWellcomeTab == null);
			item.addStyleName("pad-text");

			item.setData("height", popupable.getHeight());
			item.setData("width", popupable.getWidth());

			item.add(popupable.popup());

			item.addListener(Events.Select, new Listener<ComponentEvent>() {
				@Override
				public void handleEvent(ComponentEvent be) {
					fireEvent(new Event(EventType.ResizeContent));
				}
			});

			mainTabPanel.add(item);
		}

		mainTabPanel.setSelection(item);

		fireEvent(new Event(EventType.ResizeContent));
	}

	/**
	 * Genislik/yukseklik bilgisi 0 (sifir) verilmis formlari, panelin tamamina yayar
	 * 
	 */
	private static void reSizeTabItem() {
		TabItem item = mainTabPanel.getSelectedItem();

		if (item != null) {
			Integer width = (Integer) item.getData("width");
			Integer height = (Integer) item.getData("height");

			if (Integer.signum(width) == 0)
				item.getWidget(0).setWidth("" + (mainTabPanel.getWidth() - 10));

			if (Integer.signum(height) == 0)
				item.getWidget(0).setHeight("" + (mainTabPanel.getHeight() - 35));
		}
	}

}
