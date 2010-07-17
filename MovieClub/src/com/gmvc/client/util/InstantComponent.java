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

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.gmvc.client.base.IModel;
import com.gmvc.client.image.BaseImages;
import com.gmvc.client.meta.ImageItem;
import com.google.gwt.user.client.ui.Widget;


/**
 * Hazir widget lar sunar
 * 
 * @author mdpinar
 * 
 */
public final class InstantComponent {

	/**
	 * Ornegi alinamaz
	 * 
	 */
	private InstantComponent() {
		;
	}

	/**
	 * Verilen bilesenleri tek satir halinde panele koyar ve bu paneli doner
	 * panel yuksekligi sabit 45 dur
	 *
	 * @param widgets
	 * @return ContentPanel
	 */
	public static ContentPanel getSmartWidgetRow(Widget... widgets) {
		return getSmartWidgetRow(45, 5, widgets);
	}

	/**
	 * Verilen bilesenleri tek satir halinde panele koyar ve bu paneli doner
	 * panel yukseligi parametriktir
	 *
	 * @param widgets
	 * @return ContentPanel
	 */
	public static ContentPanel getSmartWidgetRow(int height, int padding, Widget... widgets) {
		ContentPanel cPanel = new ContentPanel();
		cPanel.setBorders(true);
		cPanel.setHeaderVisible(false);
		cPanel.setFrame(true);
		cPanel.setHeight(height);
		
		LayoutContainer container = new LayoutContainer();  
		HBoxLayout layout = new HBoxLayout();  
		layout.setPadding(new Padding(padding));  
		layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		layout.setPack(BoxLayoutPack.CENTER);
		container.setLayout(layout);  
		
		HBoxLayoutData layoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

		for (Widget w : widgets) {
			container.add(w, layoutData);
		}
		cPanel.add(container);
		
		return cPanel;
	}

	/**
	 * Bir panelin ortasina bir bilesen konacaksa bu metod kullanilabilir
	 * 
	 * <p/>
	 * Rapor on ekranlarindaki Rapor butonuna bakilabilir
	 * 
	 * @param widget
	 * @param height
	 * @return ContentPanel
	 */
	public static ContentPanel buildCenterPanel(Widget widget, int height) {
		LayoutContainer lc = new LayoutContainer();
		VBoxLayout layout = new VBoxLayout();
		layout.setPadding(new Padding(5));
		layout.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
		lc.setLayout(layout);
		lc.setHeight(height);
		lc.add(widget);

		ContentPanel panel = new ContentPanel();
		panel.setFrame(false);
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());
		panel.add(lc);

		return panel;
	}

	/**
	 * Email girisleri icin hazir bilesen doner
	 * 
	 */
	public static TextField<String> getTextEmail() {
		TextField<String> email = new TextField<String>();
		email.setFieldLabel("Email");
		email.setName("email");
		 email.setRegex(".+@.+\\.[a-z]+");
		 email.getMessages().setRegexText(Tags.get("badEmailMessage"));
		// email.setAutoValidate(true);

		return email;
	}

	/** 
	 * Standard combobox doner
	 * 
	 * @param title
	 */
	public static <M extends IModel> SimpleComboBox<M> getStandartCombo(String title) {
		SimpleComboBox<M> combo = new SimpleComboBox<M>();
		combo.setFieldLabel(title);
		combo.setForceSelection(true);
		combo.setTypeAhead(true);
		combo.setTriggerAction(TriggerAction.ALL);

		return combo;
	}

	/**
	 * Rapor cikis uniteleri comboboxu doner
	 * 
	 */
	public static ComboBox<ImageItem> getComboReportUnit() {
		ComboBox<ImageItem> reportUnitCombo = 
			getBaseImageCombo(Tags.get("reportUnit"), Utils.getListReportUnit());
		reportUnitCombo.setEditable(false);
		return reportUnitCombo;
	}

	/**
	 * Dil secenekleri comboboxunu doner
	 * 
	 */
	public static ComboBox<ImageItem> getComboLang() {
		ListStore<ImageItem> result = new ListStore<ImageItem>();
		
		ImageItem ii = null;

		ii = new ImageItem();
		ii.setTitle("Türkçe");
		ii.setIconPath(BaseImages.Instance.tr().getURL());
		ii.setStringVal("tr");
		result.add(ii);
		
		ii = new ImageItem();
		ii.setTitle("English");
		ii.setIconPath(BaseImages.Instance.us().getURL());
		ii.setStringVal("en");
		result.add(ii);
		
		ComboBox<ImageItem> langCombo = getBaseImageCombo("Dil", result);
		langCombo.setEditable(false);
		return langCombo;
	}

	/**
	 * Genel kullanim icin temel tipte imajli combobox doner
	 * 
	 * @param label
	 * @param modelList
	 */
	public static ComboBox<ImageItem> getBaseImageCombo(String label, ListStore<ImageItem> modelList) {
		ComboBox<ImageItem> combo = new ComboBox<ImageItem>();
		combo.setStore(modelList);
		combo.setFieldLabel(label);
		combo.setDisplayField("title");
		combo.setForceSelection(true);
		combo.setTypeAhead(true);
		combo.setTemplate(getBaseImageComboTemplate());
		combo.setTriggerAction(TriggerAction.ALL);

		return combo;
	}

	/**
	 * Imajli temel tipteki combolar icin template doner
	 */
	private static native String getBaseImageComboTemplate() /*-{ 
		return  [ 
			'<tpl for=".">', 
			'<div class="x-combo-list-item"><img width="14px" height="14px" src="{[values.iconPath]}"> {[values.title]}</img>', 
			'</div>', 
			'</tpl>' 
		].join(""); 
	}-*/;

}
