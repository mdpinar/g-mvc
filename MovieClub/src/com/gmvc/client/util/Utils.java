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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;

import com.gmvc.client.base.IModel;
import com.gmvc.client.enums.ReportUnit;
import com.gmvc.client.image.BaseImages;
import com.gmvc.client.meta.ImageItem;

/**
 * Genel hizmetlerin sunuldugu siniftir
 * 
 * @author mdpinar
 */
public final class Utils {

	/**
	 * Rapor formlarinda kullanilan cikis unitesi icin;
	 * Cikis Unitelerinin ImageItem Map ini tutar
	 */
	private static Map<ReportUnit, ImageItem> reportUnitMap;
	
	/**
	 * IModel -> BaseModel cevrimlerinde caching yapan degisken
	 */
	private static Map<Class<?>, BeanModelFactory> beanFactoryMap = new HashMap<Class<?>, BeanModelFactory>();

	/**
	 * Ilk erisimde yapilan hazirliklar
	 * 
	 */
	static {
		buildReportUnitMap();
	}
	
	/**
	 * DRY ihlali nin onune gecmek amaci ile tum hata uyarilari bu metoda yonlendiriliyor
	 * 
	 * </p>
	 * TODO: Goze daha cok hitap edecek hale getirilecek
	 * 
	 * @param caught - hata sinif
	 * 
	 */
	public static void showError(Throwable caught) {
		if (caught.getCause() != null) {
			showAlert(caught.getCause().getMessage());
		} else {
			showAlert(caught.getMessage());
		}
		caught.printStackTrace();
	}

	/**
	 * Uyarilari gosteren metod
	 * 
	 * @param part - hata mesajinin String kismi
	 */
	public static void showAlert(String part) {
		StringBuilder sb = new StringBuilder(Tags.get("generalErrorMessage"));
		sb.append("<br/><br/><br/>");
		sb.append("<i>");
		sb.append(part);
		sb.append("</i>");
		MessageBox.alert(Tags.get("error"), sb.toString(), null);
	}
	
	/**
	 * IModel -> BaseModel cevrimlerinde kullaniliyor
	 * 
	 * @param model
	 * @return
	 */
	public static BeanModel convertToBeanModel(IModel model) {
		if (model != null)
			return getBeanFactory(model.getClass()).createModel(model);
		else
			return null;
	}

	/**
	 * Her model icin ayri olarak bulunmasi gereken BeanModelFactory
	 * degiskenini doner, yoksa olusuturup cache e ekler.
	 * 
	 */
	private static BeanModelFactory getBeanFactory(Class<?> clazz) {
		BeanModelFactory result = beanFactoryMap.get(clazz);
		if (result == null) {
			BeanModelFactory newFactory = BeanModelLookup.get().getFactory(clazz);
			beanFactoryMap.put(clazz, newFactory);
			result = newFactory;
		}
		
		return result;
	}

	/**
	 * Rapor Cikis Unitelerinin ImageItem listesini doner
	 */
	public static ListStore<ImageItem> getListReportUnit() {
		ListStore<ImageItem> result = new ListStore<ImageItem>();
		result.add(new ArrayList<ImageItem>(reportUnitMap.values()));
		return result;
	}

	/**
	 * Rapor Cikis Unitelerinin ImahgeItem Map ini doner
	 */
	private static void buildReportUnitMap() {
		reportUnitMap = new TreeMap<ReportUnit, ImageItem>();

		ImageItem ii = null;

		ii = new ImageItem();
		ii.setTitle("Html");
		ii.setIconPath(BaseImages.Instance.html().getURL());
		ii.setEnumVal(ReportUnit.Html);
		reportUnitMap.put(ReportUnit.Html, ii);

		ii = new ImageItem();
		ii.setTitle("Excel");
		ii.setIconPath(BaseImages.Instance.excel().getURL());
		ii.setEnumVal(ReportUnit.Excel);
		reportUnitMap.put(ReportUnit.Excel, ii);

		ii = new ImageItem();
		ii.setTitle("PDF");
		ii.setIconPath(BaseImages.Instance.pdf().getURL());
		ii.setEnumVal(ReportUnit.PDF);
		reportUnitMap.put(ReportUnit.PDF, ii);

		ii = new ImageItem();
		ii.setTitle("CSV");
		ii.setIconPath(BaseImages.Instance.csv().getURL());
		ii.setEnumVal(ReportUnit.CSV);
		reportUnitMap.put(ReportUnit.CSV, ii);
	}
	
}
