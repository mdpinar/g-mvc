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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmvc.client.enums.RightGroup;

/**
 * Kullanici haklari icin tanimli haklar ile ilgili islemlerde yardimci siniftir
 * 
 * @author mdpinar
 */
public final class RightHelper {

	/**
	 * Hak ismi ile Hak bilgisini tutan map
	 */
	private static Map<String, Right> rightMap;
	
	/**
	 * Hak Guruplarina gore Hak Listesini tutan map
	 */
	private static Map<RightGroup, List<Right>> groupMap;
	
	/**
	 * Ornegi alinamaz
	 * 
	 */
	private RightHelper() {
		;
	}
	
	/**
	 * Ilk erisimde map ler hazirlanacak
	 * 
	 */
	static {
		rightMap = new HashMap<String, Right>();
		groupMap = new HashMap<RightGroup, List<Right>>();
	}
	
	/**
	 * Tanimlanan her hak, Hak listesine ve Hak Gurubuna gore map lere eklenir
	 * 
	 */
	static void addRight(Right right) {
		if (right == null) return;
		
		rightMap.put(right.getName(), right);
		
		List<Right> rightList = groupMap.get(right.getGroup());
		if (rightList == null) rightList = new ArrayList<Right>();
		
		rightList.add(right);
		groupMap.put(right.getGroup(), rightList);
	}
	
	/**
	 * Hak adindan Hak bulur, doner
	 * 
	 */
	public static Right findRight(String name) {
		return rightMap.get(name);
	}
	
	/**
	 * Hak Gurubundan Hak Listesini bulur, doner
	 * 
	 * @param group
	 * 
	 */
	public static List<Right> getRightList(RightGroup group) {
		return groupMap.get(group);
	}
	
}
