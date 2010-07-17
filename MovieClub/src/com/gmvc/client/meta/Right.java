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

import com.gmvc.client.enums.RightGroup;

/**
 * Kullanici hak denetimlerinde kullanilacak haklari tutan siniftir
 * 
 * @author mdpinar
 * 
 */
public final class Right {

	/**
	 * Hak adi
	 * 
	 */
	private String name;
	
	/**
	 * Hangi gurupta yer aldigi
	 */
	private RightGroup group;
	
	public Right(String name, RightGroup group) {
		super();
		this.name = name;
		this.group = group;

		/*
		 * Tanimlanan her hak, diger denetimlerde kolaylik olsun diye
		 * RightHelper a bildirilir, bu ayrica kullanici tanitim formunda
		 * haklarin otomatikman forma doldurulmasini da saglayacaktir
		 * 
		 */
		RightHelper.addRight(this);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public RightGroup getGroup() {
		return group;
	}
	
	public void setGroup(RightGroup group) {
		this.group = group;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Right) {
			Right right = (Right) obj;
			if (right.getName() != null && right.getName().equals(this.name)) {
				return (right.getGroup() != null && right.getGroup().equals(this.group));
			}
		}
		return super.equals(obj);
	}
	
}
