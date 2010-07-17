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
package com.mclub.client.util;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoadConfig;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.InstantComponent;
import com.gmvc.client.util.Tags;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mclub.client.app.AppModel;
import com.mclub.client.enums.CustomerGroup;
import com.mclub.client.enums.FilmStatus;
import com.mclub.client.enums.MediaType;
import com.mclub.client.enums.Sex;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.MovieDTO;

/**
 * UI ler icin hazir bilesenler sunar
 * 
 * @author mdpinar
 * 
 */
public class GUIHelper {

	/**
	 * Media Turu comboboxu doner
	 * 
	 * @see MediaType
	 * 
	 */
	public static ComboBox<ImageItem> getComboMediaType() {
		return getBaseCombo(Tags.get("type"), EnumHelper.getListMediaType());
	}

	/**
	 * Cinsiyet icin combobox doner
	 * 
	 * @see Sex
	 * 
	 */
	public static ComboBox<ImageItem> getComboSex() {
		return InstantComponent.getBaseImageCombo(Tags.get("sex"), EnumHelper.getListSex());
	}

	/**
	 * Musteri Gurubu icin combobox doner
	 * 
	 * @see CustomerGroup
	 * 
	 */
	public static ComboBox<ImageItem> getComboCustomerGroup() {
		return InstantComponent.getBaseImageCombo(Tags.get("customerGroup"), EnumHelper.getListCustomerGroup());
	}

	/**
	 * Yas Gurubu icin combobox doner
	 * 
	 */
	public static ComboBox<ImageItem> getComboAgeGroup() {
		return InstantComponent.getBaseImageCombo(Tags.get("ageGroup"), EnumHelper.getListAgeGroup());
	}

	/**
	 * Film Durumu icin combobox doner
	 * 
	 */
	public static ComboBox<ImageItem> getComboFilmStatus(List<FilmStatus> unwantedStatus) {
		return getBaseCombo(Tags.get("status"), EnumHelper.getListFilmStatus(unwantedStatus));
	}

	/**
	 * Musteri tanitimlari icin suggestbox doner
	 * 
	 * @param wantedStatus istenen statuler
	 * 
	 */
	public static ComboBox<BeanModel> getCustomerSuggestCombo() {
		ComboBox<BeanModel> customerCombo = new ComboBox<BeanModel>();
		customerCombo.setFieldLabel(Tags.get("customer"));
		customerCombo.setItemSelector("div.search-item");
		customerCombo.setDisplayField("suggestText");
		customerCombo.setTemplate(getCustomerSuggestComboTemplate());
		customerCombo.setStore(getCustomerSuggestComboStore());
		customerCombo.setHideTrigger(true);
		customerCombo.setMinChars(3);
		
		return customerCombo;
	}

	/**
	 * Film tanitimlari icin suggestbox doner
	 * 
	 * @param wantedStatus istenen statuler
	 * 
	 */
	public static ComboBox<BeanModel> getMovieSuggestCombo(final FilmStatus[] wantedStatus) {
		ComboBox<BeanModel> movieCombo = new ComboBox<BeanModel>();
		movieCombo.setFieldLabel(Tags.get("movie"));
		movieCombo.setItemSelector("div.search-item");
		movieCombo.setDisplayField("suggestText");
		movieCombo.setTemplate(getMovieSuggestComboTemplate());
		movieCombo.setStore(getMovieSuggestComboStore(wantedStatus));
		movieCombo.setHideTrigger(true);
		movieCombo.setMinChars(3);
		
		return movieCombo;
	}
	
	/**
	 * Genel kullanim icin temel tipte combobox doner
	 * 
	 * @param label
	 * @param modelList
	 * 
	 */
	private static ComboBox<ImageItem> getBaseCombo(String label, ListStore<ImageItem> modelList) {
		ComboBox<ImageItem> combo = new ComboBox<ImageItem>();
		combo.setStore(modelList);
		combo.setFieldLabel(label);
		combo.setDisplayField("title");
		combo.setForceSelection(true);
		combo.setTypeAhead(true);
		combo.setTemplate(getBaseComboTemplate());
		combo.setTriggerAction(TriggerAction.ALL);
		combo.setValue(modelList.getAt(0));

		return combo;
	}
	
	/**
	 * RPC destekli Musteri secim Suggestion Combobox icin data doner
	 * 
	 * @return ListStore<CustomerDTO>
	 * 
	 */
	private static ListStore<BeanModel> getCustomerSuggestComboStore() {
		RpcProxy<List<CustomerDTO>> proxy = new RpcProxy<List<CustomerDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<List<CustomerDTO>> callback) {
				BaseListLoadConfig conf = (BaseListLoadConfig) loadConfig;
				String startWith = conf.get("query");
				AppModel.getSystemService().getCustomerList(startWith, callback);
			}
		};
		ListLoader<ListLoadResult<CustomerDTO>> loader = new BaseListLoader<ListLoadResult<CustomerDTO>>(proxy, new BeanModelReader());
		ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
		loader.load();
		
		return store;
	}
	
	/**
	 * RPC destekli Film secim Suggestion Combobox icin data doner
	 * 
	 * @return ListStore<MovieDTO>
	 * 
	 */
	private static ListStore<BeanModel> getMovieSuggestComboStore(final FilmStatus[] wantedStatus) {
		RpcProxy<List<MovieDTO>> proxy = new RpcProxy<List<MovieDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<List<MovieDTO>> callback) {
				BaseListLoadConfig conf = (BaseListLoadConfig) loadConfig;
				String startWith = conf.get("query");
				AppModel.getSystemService().getMovieList(startWith, wantedStatus, callback);
			}
		};
		ListLoader<ListLoadResult<MovieDTO>> loader = new BaseListLoader<ListLoadResult<MovieDTO>>(proxy, new BeanModelReader());
		ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
		loader.load();
		
		return store;
	}
	
	/**
	 * Musteri secim Suggestion Combobox u icin template doner
	 * 
	 * @return template
	 * 
	 */
	private static native String getCustomerSuggestComboTemplate() /*-{ 
		return [ 
			'<tpl for="."><div class="search-item">', 
			'{name} - {mobilePhone}', 
			'</div></tpl>' 
		].join(""); 
	}-*/;  
	
	
	/**
	 * Film secim Suggestion Combobox u icin template doner
	 * 
	 * @return template
	 * 
	 */
	private static native String getMovieSuggestComboTemplate() /*-{ 
		return [ 
			'<tpl for="."><div class="search-item">', 
			'{name} - {copyNo}', 
			'</div></tpl>' 
		].join(""); 
	}-*/;  

	/**
	 * Temel tipteki combolar icin template doner
	 */
	private static native String getBaseComboTemplate() /*-{ 
		return  [ 
			'<tpl for=".">', 
			'<div class="x-combo-list-item">{[values.title]}', 
			'</div>', 
			'</tpl>' 
		].join(""); 
	}-*/;
	
}
