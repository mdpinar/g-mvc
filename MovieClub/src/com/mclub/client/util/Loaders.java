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

import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.gmvc.client.util.Utils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mclub.client.app.AppModel;
import com.mclub.client.model.JobDTO;
import com.mclub.client.model.LocationDTO;
import com.mclub.client.model.MovieGroupDTO;

/**
 * Proje genelinde kullanilan, Genel Tanitimlarin combolara doldurulmasi
 * islemlerini yapar.
 *  
 * @author mdpinar
 * 
 */
public final class Loaders {

	/**
	 * Tanimli Meslekleri verilen comboya doldurur
	 * 
	 * @param combo
	 * 
	 */
	public static void loadJobs(final SimpleComboBox<JobDTO> combo) {
		loadJobs(combo, null);
	}


	/**
	 * Tanimli Bolgeleri verilen comboya doldurur
	 * 
	 * @param combo
	 * 
	 */
	public static void loadLocations(final SimpleComboBox<LocationDTO> combo) {
		loadLocations(combo, null);
	}


	/**
	 * Tanimli Film Guruplarini verilen comboya doldurur
	 * 
	 * @param combo
	 * 
	 */
	public static void loadMovieGroups(final SimpleComboBox<MovieGroupDTO> combo) {
		loadMovieGroups(combo, null);
	}
	
	/**
	 * Tanimli Meslekleri verilen comboya doldurur, 
	 * value olarak verilen degeri secili duruma getirir
	 * 
	 * @param combo
	 * @param value
	 * 
	 */
	public static void loadJobs(final SimpleComboBox<JobDTO> combo, final JobDTO value) {
		if (combo.getStore() != null) combo.getStore().removeAll();
		AppModel.getSystemService().getJobList(new AsyncCallback<List<JobDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Utils.showError(caught);
			}

			@Override
			public void onSuccess(List<JobDTO> result) {
				combo.add(result);
				if (value != null) combo.setSimpleValue(value);
			}
		});
	}
	
	/**
	 * Tanimli Bolgeleri verilen comboya doldurur, 
	 * value olarak verilen degeri secili duruma getirir
	 * 
	 * @param combo
	 * @param value
	 * 
	 */
	public static void loadLocations(final SimpleComboBox<LocationDTO> combo, final LocationDTO value) {
		if (combo.getStore() != null) combo.getStore().removeAll();
		AppModel.getSystemService().getLocationList(
				new AsyncCallback<List<LocationDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Utils.showError(caught);
					}

					@Override
					public void onSuccess(List<LocationDTO> result) {
						combo.add(result);
						if (value != null) combo.setSimpleValue(value);
					}
				});
	}

	/**
	 * Tanimli Film Guruplarini verilen comboya doldurur, 
	 * value olarak verilen degeri secili duruma getirir
	 * 
	 * @param combo
	 * @param value
	 * 
	 */
	public static void loadMovieGroups(final SimpleComboBox<MovieGroupDTO> combo, final MovieGroupDTO value) {
		if (combo.getStore() != null) combo.getStore().removeAll();
		AppModel.getSystemService().getMovieGroupList(
				new AsyncCallback<List<MovieGroupDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Utils.showError(caught);
					}

					@Override
					public void onSuccess(List<MovieGroupDTO> result) {
						combo.add(result);
						if (value != null) combo.setSimpleValue(value);
					}
				});
	}
	
}
