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
package com.gmvc.client.meta;

import java.io.Serializable;

import com.gmvc.client.enums.SearchType;

/**
 * Browser formlarinda arama kriterlerinin olusturulmasinda kullanilan siniflardir.
 * 
 * </p>
 * TODO: Daha gelismis filtreleme sistemi hazirlanmali
 * 
 * @author mdpinar
 * 
 */
public class FilterItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Saha adi
	 */
	private String fieldName;
	
	/**
	 * Arama turu
	 * 
	 * @see SearchType
	 */
	private SearchType searchType;

	/**
	 * Filtrenin basligi
	 */
	private String label;

	/**
	 * Filtrenin degeri
	 */
	private String value;

	/**
	 * Serilestirilecegi icin hazir kurucu metod olmak zorunda
	 */
	public FilterItem() {
		;
	}

	public FilterItem(String fieldName, SearchType searchType, String label) {
		super();
		this.fieldName = fieldName;
		this.searchType = searchType;
		this.label = label;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
