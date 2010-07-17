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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.extjs.gxt.ui.client.store.ListStore;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.Tags;
import com.mclub.client.app.AppModel;
import com.mclub.client.enums.AgeGroup;
import com.mclub.client.enums.CustomerGroup;
import com.mclub.client.enums.FilmStatus;
import com.mclub.client.enums.MediaType;
import com.mclub.client.enums.Sex;

/**
 * Client tarafinda enum lara dayali olarak kullanilacak olan Imajli Combobox lar icin yardimci sinif
 * 
 * <p/>
 * Comboboxlarda doldurulan imajli enumlar icin hazir islemler sunar
 * 
 * @author mdpinar
 * 
 */
public final class EnumHelper {

	/**
	 * Cinsiyet secim combosu icin kullanilacak olan enum lar
	 * 
	 */
	private static Map<Sex, ImageItem> sexMap;

	/**
	 * Yas Gurubu secim combosu icin kullanilacak olan enum lar
	 * 
	 */
	private static Map<AgeGroup, ImageItem> ageGroupMap;

	/**
	 * Fil Statusu secim combosu icin kullanilacak olan enum lar
	 * 
	 */
	private static Map<FilmStatus, ImageItem> filmStatusMap;

	/**
	 * Musteri Gurubu secim combosu icin kullanilacak olan enum lar
	 * 
	 */
	private static Map<CustomerGroup, ImageItem> customerGroupMap;

	/**
	 * Ilk erisimde yapilacak seyler
	 * 
	 */
	static {
		buildSexMap();
		buildAgeGroupMap();
		buildFilmStatusMap();
		buildCustomerGroupMap();
	}

	/**
	 * Ornegi alinamaz
	 * 
	 */
	private EnumHelper() {
		;
	}

	/**
	 * Cinsiyet secenekleri listesi
	 * 
	 */
	public static ListStore<ImageItem> getListSex() {
		ListStore<ImageItem> result = new ListStore<ImageItem>();
		result.add(new ArrayList<ImageItem>(sexMap.values()));
		return result;
	}

	/**
	 * Yas Gurubu secenekleri listesi
	 * 
	 */
	public static ListStore<ImageItem> getListAgeGroup() {
		ListStore<ImageItem> result = new ListStore<ImageItem>();
		result.add(new ArrayList<ImageItem>(ageGroupMap.values()));
		return result;
	}

	/**
	 * Film Statuleri secenekleri listesi
	 * 
	 * @param unwantedStatus istenmeyen statuler
	 * 
	 */
	public static ListStore<ImageItem> getListFilmStatus(List<FilmStatus> unwantedStatus) {
		ListStore<ImageItem> result = new ListStore<ImageItem>();
		
		for (FilmStatus fs: filmStatusMap.keySet()) {
			if (unwantedStatus == null || ! unwantedStatus.contains(fs)) {
				result.add(filmStatusMap.get(fs));
			}
		}
		
		return result;
	}

	/**
	 * Musteri Gurubu secenekleri listesi
	 * 
	 */
	public static ListStore<ImageItem> getListCustomerGroup() {
		ListStore<ImageItem> result = new ListStore<ImageItem>();
		result.add(new ArrayList<ImageItem>(customerGroupMap.values()));
		return result;
	}

	/**
	 * Urun tipi (dvd, vcd, diger) secenekleri listesi
	 * 
	 */
	public static ListStore<ImageItem> getListMediaType() {
		ListStore<ImageItem> result = new ListStore<ImageItem>();
		result.add(new ImageItem(MediaType.OTHER));
		result.add(new ImageItem(MediaType.DVD));
		result.add(new ImageItem(MediaType.VCD));
		return result;
	}

	/**
	 * Cinsiyet seceneklerini barindiran map i bina eder
	 * 
	 */
	private static void buildSexMap() {
		sexMap = new TreeMap<Sex, ImageItem>();

		ImageItem ii = null;

		ii = new ImageItem();
		ii.setTitle(Tags.get("male"));
		ii.setIconPath(AppModel.getBaseImagePath("icons/male.png"));
		ii.setEnumVal(Sex.MALE);
		sexMap.put(Sex.MALE, ii);

		ii = new ImageItem();
		ii.setTitle(Tags.get("female"));
		ii.setIconPath(AppModel.getBaseImagePath("icons/female.png"));
		ii.setEnumVal(Sex.MALE);
		sexMap.put(Sex.FEMALE, ii);
	}


	/**
	 * Yas Gurubu seceneklerini barindiran map i bina eder
	 * 
	 */
	private static void buildAgeGroupMap() {
		ageGroupMap = new TreeMap<AgeGroup, ImageItem>();

		ImageItem ii = null;

		ii = new ImageItem();
		ii.setTitle(Tags.get("child"));
		ii.setIconPath(AppModel.getBaseImagePath("icons/child.png"));
		ii.setEnumVal(AgeGroup.CHILD);
		ageGroupMap.put(AgeGroup.CHILD, ii);

		ii = new ImageItem();
		ii.setTitle(Tags.get("young"));
		ii.setIconPath(AppModel.getBaseImagePath("icons/young.png"));
		ii.setEnumVal(AgeGroup.YOUNG);
		ageGroupMap.put(AgeGroup.YOUNG, ii);

		ii = new ImageItem();
		ii.setTitle(Tags.get("mature"));
		ii.setIconPath(AppModel.getBaseImagePath("icons/mature.png"));
		ii.setEnumVal(AgeGroup.MATURE);
		ageGroupMap.put(AgeGroup.MATURE, ii);

		ii = new ImageItem();
		ii.setTitle(Tags.get("old"));
		ii.setIconPath(AppModel.getBaseImagePath("icons/old.png"));
		ii.setEnumVal(AgeGroup.OLD);
		ageGroupMap.put(AgeGroup.OLD, ii);
	}


	/**
	 * Film Statusu seceneklerini barindiran map i bina eder
	 * 
	 */
	private static void buildFilmStatusMap() {
		filmStatusMap = new TreeMap<FilmStatus, ImageItem>();

		ImageItem ii = null;

		ii = new ImageItem();
		ii.setTitle(Tags.get("inStore"));
		ii.setEnumVal(FilmStatus.IN_STORE);
		filmStatusMap.put(FilmStatus.IN_STORE, ii);

		ii = new ImageItem();
		ii.setTitle(Tags.get("rent"));
		ii.setEnumVal(FilmStatus.RENT);
		filmStatusMap.put(FilmStatus.RENT, ii);

		ii = new ImageItem();
		ii.setTitle(Tags.get("sell"));
		ii.setEnumVal(FilmStatus.SELL);
		filmStatusMap.put(FilmStatus.SELL, ii);

		ii = new ImageItem();
		ii.setTitle(Tags.get("present"));
		ii.setEnumVal(FilmStatus.PRESENT);
		filmStatusMap.put(FilmStatus.PRESENT, ii);

		ii = new ImageItem();
		ii.setTitle(Tags.get("junk"));
		ii.setEnumVal(FilmStatus.JUNK);
		filmStatusMap.put(FilmStatus.JUNK, ii);

		ii = new ImageItem();
		ii.setTitle(Tags.get("lost"));
		ii.setEnumVal(FilmStatus.LOST);
		filmStatusMap.put(FilmStatus.LOST, ii);
	}

	private static void buildCustomerGroupMap() {
		customerGroupMap = new TreeMap<CustomerGroup, ImageItem>();

		ImageItem ii = null;

		ii = new ImageItem();
		ii.setTitle("A");
		ii.setIconPath(AppModel.getBaseImagePath("icons/yellow_flag.png"));
		ii.setEnumVal(CustomerGroup.A);
		customerGroupMap.put(CustomerGroup.A, ii);

		ii = new ImageItem();
		ii.setTitle("B");
		ii.setIconPath(AppModel.getBaseImagePath("icons/orange_flag.png"));
		ii.setEnumVal(CustomerGroup.B);
		customerGroupMap.put(CustomerGroup.B, ii);

		ii = new ImageItem();
		ii.setTitle("C");
		ii.setIconPath(AppModel.getBaseImagePath("icons/red_flag.png"));
		ii.setEnumVal(CustomerGroup.C);
		customerGroupMap.put(CustomerGroup.C, ii);

		ii = new ImageItem();
		ii.setTitle("D");
		ii.setIconPath(AppModel.getBaseImagePath("icons/green_flag.png"));
		ii.setEnumVal(CustomerGroup.D);
		customerGroupMap.put(CustomerGroup.D, ii);

		ii = new ImageItem();
		ii.setTitle("E");
		ii.setIconPath(AppModel.getBaseImagePath("icons/blue_flag.png"));
		ii.setEnumVal(CustomerGroup.E);
		customerGroupMap.put(CustomerGroup.E, ii);
	}

}
