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
package com.gmvc.client.base;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

/**
 * Model siniflar icin ortak bir arayuz saglar, bu arayuz ile
 * client tarafindaki (view-controller) siniflar icin iletisim 
 * tutarliligi saglanir.
 * 
 * <p/>
 * <strong>Kullanilacak her model, bu arayuzu gerceklemek zorundadir!</strong>
 * 
 * <p/>
 * GXT bilesenleri data islemlerinde kendi <i>BeanModelTag</i> arayuzunu
 * kullanir, kendi modellerimizin GXT bilesenlerinde sorunsuz islem
 * gorebilmeleri icin her model BeanModelTag' i dolayli olarak extends eder.
 * 
 * @see BeanModelTag
 * 
 * @author mdpinar
 * 
 */
public interface IModel extends Serializable, BeanModelTag {

	/**
	 * Her modelde bulunan standart id alanini doner
	 * 
	 * @return id
	 */
	Long getId();

}
