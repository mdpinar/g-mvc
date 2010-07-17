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

import java.util.HashMap;
import java.util.Map;

import com.gmvc.client.base.IModel;

/**
 * Model-View-Controller arasindaki haberlesmede kullanilan model siniftir
 * 
 * @author mdpinar
 * 
 */
public class Event {

	/**
	 * Olayin turu
	 * 
	 * @see EventType
	 */
	private int type;

	/**
	 * Kayit islemlerindeki aktif model
	 * 
	 * @see IModel
	 */
	private IModel model;
	
	/**
	 * Herhangi bir neden dolayi, olayin devam etmemesi 
	 * gerekiyorsa bu deger true yapilir
	 * 
	 */
	private boolean cancel = false;
	
	/**
	 * Lazim olabilecek diger (model ve cancel degiskeni disindaki) butun parametreleri tasiyan map
	 * 
	 */
	private Map<String, Object> paramMap = new HashMap<String, Object>();

	public Event(int type) {
		super();
		this.type = type;
	}

	public Event(int type, IModel model) {
		super();
		this.type = type;
		this.model = model;
	}

	public Event(int type, Map<String, Object> paramMap) {
		super();
		this.type = type;
		this.paramMap = paramMap;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public IModel getModel() {
		return model;
	}

	public void setModel(IModel model) {
		this.model = model;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	public <X> Event addParam(String key, X value) {
		paramMap.put(key, value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <X> X getParam(String key) {
		return (X) paramMap.get(key);
	}

}
