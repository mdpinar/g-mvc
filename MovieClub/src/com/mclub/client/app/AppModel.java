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

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.gmvc.client.app.BaseAppModel;
import com.gmvc.client.base.Popupable;
import com.gmvc.client.meta.IUserController;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.InstantComponent;
import com.gmvc.client.util.UserController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.mclub.client.forms.changeStatus.ChangeStatusController;
import com.mclub.client.forms.given.GivenMovieController;
import com.mclub.client.forms.incoming.IncomingMovieController;
import com.mclub.client.forms.info.CustomerController;
import com.mclub.client.forms.info.JobController;
import com.mclub.client.forms.info.LocationController;
import com.mclub.client.forms.info.MovieGroupController;
import com.mclub.client.forms.info.MovieInfoController;
import com.mclub.client.i18n.Tags;
import com.mclub.client.report.CustomerReportController;
import com.mclub.client.report.CustomerTransReportController;
import com.mclub.client.report.MovieReportController;
import com.mclub.client.report.MovieStatusReportController;
import com.mclub.client.report.MovieTransReportController;
import com.mclub.client.service.SystemService;
import com.mclub.client.service.SystemServiceAsync;
import com.mclub.client.util.UserUtil;

/**
 * Uygulama bazindaki verileri sunan siniftir, 
 * GMVC modulu icin bu sinifin olmasi ve BaseAppModel sinifini uzatmis olmasi zorunludur.
 *
 * <p/>
 * TODO: sistem parametrelerini isleyecek bir yapi kurulmali
 * 
 * @author mdpinar
 * 
 */
public final class AppModel extends BaseAppModel {
	
	/**
	 * Coklu dil desteginde kullanilacak i18n handle i
	 * 
	 */
	private Tags tags = GWT.create(Tags.class);
	
	/**
	 * Genel rpc metodlarini barindiran sistem servisi
	 * 
	 */
	private static SystemServiceAsync systemService = GWT.create(SystemService.class);

	/**
	 * Kullanici isim ve sifre sinin girildigi acilis formu
	 *  
	 */
	private Dialog loginDialog;
	
	/**
	 * Kullanici hak denetimlerinin yapildigi sinif
	 * 
	 */
	private IUserController userController;
	
	public AppModel() {
		setTags(tags);
	}

	/**
	 * Sistem servisini doner
	 * 
	 */
	public static SystemServiceAsync getSystemService() {
		return systemService;
	}

	/**
	 * Acilis formu
	 */
	public Dialog getLoginDialog() {
		if (loginDialog == null) loginDialog = new LoginDialog();
		return loginDialog;
	}
	
	/**
	 * Kullanici isim ve sifre sinin girildigi acilis formunu doner
	 * 
	 */
	public IUserController getUserController() {
		if (userController == null) userController = new UserUtil();
		return userController;
	}
	
	/**
	 * Ana menu formunun sqg ust kisminda yer alacak diller combosunu doner, 
	 * null donulurse coklu dil destegi olmayacaktir
	 * 
	 */
	@Override
	public Component getLangListWidget() {
        ComboBox<ImageItem> combo = InstantComponent.getComboLang();
		combo.setForceSelection(true);
		combo.setEditable(false);
		combo.setTypeAhead(true);
		combo.setTriggerAction(TriggerAction.ALL);
        
        String currentLocale = LocaleInfo.getCurrentLocale().getLocaleName();
        if (currentLocale.equals("default")) currentLocale = "tr";
        
        for (ImageItem ii: combo.getStore().getModels()) {
        	if (ii.getStringVal().equals(currentLocale)) {
        		combo.setValue(ii);
        	}
        }
        
        combo.addSelectionChangedListener(new SelectionChangedListener<ImageItem>() {
            @Override
            public void selectionChanged(SelectionChangedEvent<ImageItem> se) {
            	
            	String url = Window
            	.Location.getHref();
            	int pos = url.indexOf("locale=");
            	
            	if (pos > 0) {
            		url = url.substring(0, pos + 7) + se.getSelectedItem().getStringVal();
            	} else {
            		url += "&locale=" + se.getSelectedItem().getStringVal();
            	}
            	Window.open(url, "_self","");
            }
        });
        
        return combo;
	}
	
	/**
	 * Ana menu sistemi
	 * 
	 */
	public List<BaseTreeModel> getMenuList() {
		List<BaseTreeModel> result = new ArrayList<BaseTreeModel>();
		
		result.add(getActionMenu());
		result.add(getInfoMenu());
		result.add(getReportMenu());
		result.add(getSystemMenu());
		
		return result;
	}

	/**
	 * WAR icindeki temel imaj dizinini verir 
	 * 
	 * @param addPath - donecek dizinin sonuna eklenecek parca
	 */
	public static String getBaseImagePath(String addPath) {
		return (GXT.isIE ? "" : "../../") + "images/" + addPath;
	}
	
	/**
	 * Aksiyonlar menusu olusturulur
	 * 
	 * @return menu
	 */
	private BaseTreeModel getActionMenu() {
		List<Popupable> popupList = new ArrayList<Popupable>();

		if (UserController.hasRight(Rights.GivenMovie))
			popupList.add(new GivenMovieController());

		if (UserController.hasRight(Rights.IncomingMovie))
			popupList.add(new IncomingMovieController());

		if (UserController.hasRight(Rights.ChangeStatus))
			popupList.add(new ChangeStatusController());
		
		return buildTreeMenu(popupList, "action", tags.actions());
	}

	/**
	 * Tanıtım menusunu olusturulur
	 * 
	 * @return menu sistemi
	 */
	private BaseTreeModel getInfoMenu() {
		List<Popupable> popupList = new ArrayList<Popupable>();

		if (UserController.hasRight(Rights.Movie))
			popupList.add(new MovieInfoController());
		
		if (UserController.hasRight(Rights.Customer))
			popupList.add(new CustomerController());
		
		if (UserController.hasRight(Rights.Job))
			popupList.add(new JobController());
		
		if (UserController.hasRight(Rights.Location))
			popupList.add(new LocationController());
		
		if (UserController.hasRight(Rights.MovieGroup))
			popupList.add(new MovieGroupController());

		return buildTreeMenu(popupList, "publicInfo", tags.publicInfo());
	}

	/**
	 * Raporlar menusu olusturulur
	 * 
	 * @return menu
	 */
	private BaseTreeModel getReportMenu() {
		List<Popupable> popupList = new ArrayList<Popupable>();

		if (UserController.hasRight(Rights.CustomerReport))
			popupList.add(new CustomerReportController());

		if (UserController.hasRight(Rights.MovieStatusReport))
			popupList.add(new MovieStatusReportController());

		if (UserController.hasRight(Rights.MovieReport))
			popupList.add(new MovieReportController());

		if (UserController.hasRight(Rights.CustomerTransReport))
			popupList.add(new CustomerTransReportController());

		if (UserController.hasRight(Rights.MovieTransReport))
			popupList.add(new MovieTransReportController());
		
		return buildTreeMenu(popupList, "reports", tags.reports());
	}

	/**
	 * Sistem menusu olusturulur
	 * 
	 * @return menu
	 */
	private BaseTreeModel getSystemMenu() {
		if (! UserController.hasAllRight()) return null;
		
		List<Popupable> popupList = new ArrayList<Popupable>();

		if (UserController.hasRight(Rights.Users))
			popupList.add(new com.mclub.client.forms.user.UserController());
		
		return buildTreeMenu(popupList, "system", "System");
	}
	
	/**
	 * Tree menu sistemini bina eder
	 * 
	 * @param popupList
	 * @param id
	 * @param title
	 */
	private BaseTreeModel buildTreeMenu(List<Popupable> popupList, String id, String title) {
		if (popupList.size() > 0) {
			BaseTreeModel root = new BaseTreeModel();
			root.set("id", id);
			root.set("name", title);

			for (Popupable pop : popupList) {
				BaseTreeModel child = new BaseTreeModel();
				child.set("id", pop.getRight());
				child.set("name", pop.getTitle());
				child.set("popup", pop);
				child.set("leaf", true);

				root.add(child);
			}

			return root;
		} else {
			return null;
		}
	}
	
	@Override
	public ContentPanel getWellcomePanel() {
		ContentPanel cp = new ContentPanel();
		cp.setBorders(true);
		cp.setHeaderVisible(false);
		cp.setFrame(false);
		cp.setUrl("wellcomepage.html");
		
		return cp;
	}

}
