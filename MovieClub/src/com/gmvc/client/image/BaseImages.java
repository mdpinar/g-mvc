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
package com.gmvc.client.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * GMVC de kullanilabilecek tum simgeleri icerir
 * 
 * @author mdpinar
 * 
 */
public interface BaseImages extends ClientBundle {

	BaseImages Instance = GWT.create(BaseImages.class);

	ImageResource add();
	ImageResource broom();
	ImageResource delete();
	ImageResource door_out();
	ImageResource edit();
	ImageResource exec();
	ImageResource exit();
	ImageResource update();
	ImageResource load();
	ImageResource print();
	ImageResource refresh();
	ImageResource report();
	ImageResource saveAndClose();
	ImageResource saveAndContinue();
	ImageResource ok();
	ImageResource cancel();
	ImageResource search();
	ImageResource minus();
	ImageResource html();
	ImageResource excel();
	ImageResource pdf();
	ImageResource csv();
	ImageResource tr();
	ImageResource us();
	
}
