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

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * Imajli combolarin olusturulmasinda kullanilan siniftir.
 * 
 * @see BaseModelData
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
public class ImageItem extends BaseModelData {

	/**
	 * Serilestirilecegi icin hazir kurucu metod olmak zorunda
	 */
	public ImageItem() {
		;
	}

	public ImageItem(Enum enumVal) {
		setTitle(enumVal.toString());
		setEnumVal(enumVal);
	}

	public String getTitle() {
		return get("title");
	}

	public void setTitle(String title) {
		set("title", title);
	}

	public String getIconPath() {
		return get("iconPath");
	}

	public void setIconPath(String iconPath) {
		set("iconPath", iconPath);
	}

	public Enum getEnumVal() {
		return get("enumVal");
	}

	public void setEnumVal(Enum enumVal) {
		set("enumVal", enumVal);
	}

	public String getStringVal() {
		return get("stringVal");
	}

	public void setStringVal(String stringVal) {
		set("stringVal", stringVal);
	}

}
