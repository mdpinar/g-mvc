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

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.gmvc.client.base.Popupable;
import com.gmvc.client.image.BaseImages;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.EventType;
import com.gmvc.client.util.Tags;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * Uygulama ana formunun iskeletini olusturur. 
 * Menu sistemi ve form yerlesimleri bu yapi uzerine bina edilir. 
 *
 * @author mdpinar
 * 
 */
public final class AppView {

	/**
	 * Uygulama bazindaki verileri tasiyan degisken
	 */
	BaseAppModel appModel;
	
	/**
	 * Menu sistemi
	 * 
	 * AppController dan expandAll() cagrimi icin protected olarak isaretlendi
	 */
	protected TreePanel<ModelData> treePanel;

	/**
	 * Tum bilesenleri tasiyan temel kap
	 * 
	 */
	private Viewport viewport;

	/**
	 * TabPanel i barindiran merkezi panel
	 * 
	 */
	private ContentPanel centerPanel;

	/**
	 * Form basligi ve Oturumu Kapat butonunu bulundugu En ust 
	 * kisimdaki panel
	 * 
	 */
	private HtmlContainer topPanel;

	/**
	 * Menu paneli
	 * 
	 */
	private ContentPanel menuPanel;
	
	public AppView(BaseAppModel appModel) {
		this.appModel = appModel;
	}

	/**
	 * Tum gorsel bilesenleri kapsayan kap
	 *
	 * @see #buildTopPanel()
	 * @see #buildMenuPanel()
	 * @see #buildCenterPanel()
	 * 
	 * @return Viewport
	 */
	Viewport getViewport() {
		if (this.viewport == null) {
			viewport = new Viewport();
			viewport.setLayout(new BorderLayout());

			buildTopPanel();
			buildMenuPanel();
			buildCenterPanel();

			menuPanel.add(buildTreePanel());
		}
		return viewport;
	}

	/**
	 * Oturum kapanip tekrar acildiginda, ayni bilesenler tekrar yuklenmesin
	 * diye bu metodla yikilirlar
	 * 
	 */
	void destroyViewport() {
		viewport = null;
		centerPanel = null;
		topPanel = null;
		menuPanel = null;
		ContentController.fireEvent(new Event(EventType.DestroyMainPanel));
	}

	/**
	 * Merkezi panel olusturulur, tabpaneller bunu üzerine yerlesecektir
	 * 
	 */
	private void buildCenterPanel() {
		ContentController.fireEvent(new Event(EventType.BuildMainPanel));

		centerPanel = new ContentPanel();
		centerPanel.setBorders(false);
		centerPanel.setHeaderVisible(false);
		centerPanel.setLayout(new FitLayout());
		centerPanel.add(ContentController.getMainPanel());

		centerPanel.addListener(Events.Resize, new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent be) {
				ContentController.fireEvent(new Event(EventType.ResizeContent));
			}
		});

		BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
		data.setMargins(new Margins(5, 5, 5, 0));
		viewport.add(centerPanel, data);
	}

	/**
	 * Menu paneli olusturulur
	 * 
	 */
	private void buildMenuPanel() {
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST, 220, 150, 320);
		data.setMargins(new Margins(5));
		data.setCollapsible(true);

		menuPanel = new ContentPanel(new FitLayout());
		menuPanel.setHeading("Menu");

		viewport.add(menuPanel, data);
	}

	/**
	 * En ustteki panel olusturulur, proje basligi ve Oturumu Kapat butonlari
	 * gibi bilesenler bu forma yerlesecektir
	 * 
	 */
	private void buildTopPanel() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<div id='demo-theme-btn'></div>");
		
		Component langListWidget = appModel.getLangListWidget();
		if (langListWidget != null) sb.append("<div id='demo-theme'></div>");
		
		sb.append("<div id='demo-title'>" + Tags.get("projectTitle") + "</div>");

		topPanel = new HtmlContainer(sb.toString());
		topPanel.setStateful(false);
		topPanel.setId("demo-header");
		topPanel.addStyleName("x-small-editor");
		
		Button logout = new Button(Tags.get("logout"),
				AbstractImagePrototype.create(BaseImages.Instance.door_out()));
		logout.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				AppController.fireEvent(new Event(EventType.Logout));
			}
		});

		if (langListWidget != null) topPanel.add(langListWidget, "#demo-theme");
		topPanel.add(logout, "#demo-theme-btn");

		BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 33);
		data.setMargins(new Margins());
		viewport.add(topPanel, data);
	}

	/**
	 * Menu panelini olusturur
	 * 
	 * @return menu sistemi
	 */
	private TreePanel<ModelData> buildTreePanel() {
		
		if (appModel.getMenuList() == null) return null;
		
		BaseTreeModel root = new BaseTreeModel();
		root.set("name", Tags.get("menuSystem"));
		
		for (BaseTreeModel menu: appModel.getMenuList()) {
			if (menu != null) root.add(menu);
		}

		TreeStore<ModelData> store = new TreeStore<ModelData>();
		store.add(root, true);

		treePanel = new TreePanel<ModelData>(store);
		treePanel.setWidth(300);
		treePanel.setDisplayProperty("name");
		treePanel.setBorders(true);
		treePanel.setLayoutData(new FitLayout());
		treePanel.getStyle().setLeafIcon(
				AbstractImagePrototype.create(BaseImages.Instance.minus()));

		treePanel.addListener(Events.OnClick,
				new Listener<TreePanelEvent<ModelData>>() {
					@Override
					public void handleEvent(TreePanelEvent<ModelData> be) {
						if (be != null && be.getItem() != null) {
							Boolean isLeaf = be.getItem().get("leaf");
							if (isLeaf != null && isLeaf.equals(Boolean.TRUE)) {
								ContentController.fireEvent(
									new Event(EventType.ShowContent)
										.addParam("popupable", be.getItem().get("popup"))
								);
							}
						}
					}
				});

		treePanel.setIconProvider(new ModelIconProvider<ModelData>() {
			public AbstractImagePrototype getIcon(ModelData model) {
				Popupable popup = (Popupable) model.get("popup");
				if (popup != null && popup.getIcon() != null) {
					return AbstractImagePrototype.create(popup.getIcon());
				} else {
					return null;
				}
			}
		});

		return treePanel;
	}

}
