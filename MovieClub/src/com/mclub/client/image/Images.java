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
package com.mclub.client.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Tum simgeleri icerir
 * 
 * @author mdpinar
 * 
 */
public interface Images extends ClientBundle {

	Images Instance = GWT.create(Images.class);

	ImageResource all_in();
	ImageResource add();
	ImageResource arrow_switch();
	ImageResource button_ok();
	ImageResource button_cancel();
	ImageResource broom();
	ImageResource chart_line();
	ImageResource chart_pie();
	ImageResource checked();
	ImageResource clock();
	ImageResource coins();
	ImageResource coins_add();
	ImageResource coins_delete();
	ImageResource comment();
	ImageResource connect();
	ImageResource cross();
	ImageResource date();
	ImageResource delete();
	ImageResource delete1();
	ImageResource door_out();
	ImageResource down();
	ImageResource edit();
	ImageResource edit1();
	ImageResource exclamation();
	ImageResource exec();
	ImageResource exit();
	ImageResource film();
	ImageResource folder();
	ImageResource update();
	ImageResource group();
	ImageResource help();
	ImageResource hint();
	ImageResource house();
	ImageResource info();
	ImageResource information();
	ImageResource load();
	ImageResource lorry();
	ImageResource minus();
	ImageResource money_dollar();
	ImageResource money_euro();
	ImageResource pause();
	ImageResource packge();
	ImageResource package_add();
	ImageResource page();
	ImageResource plugin();
	ImageResource plus();
	ImageResource ppreview();
	ImageResource print();
	ImageResource refresh();
	ImageResource report();
	ImageResource script_add();
	ImageResource script_delete();
	ImageResource selectBox();
	ImageResource shield();
	ImageResource saveAndClose();
	ImageResource saveAndContinue();
	ImageResource square_arrow();
	ImageResource table();
	ImageResource telephone();
	ImageResource text();
	ImageResource unchecked();
	ImageResource user_add();
	ImageResource user_delete();
	ImageResource user();
	ImageResource validator();
	ImageResource vcard_add();
	ImageResource vcard();
	ImageResource warning();
	ImageResource world_add();
	ImageResource world_delete();
	ImageResource world_edit();
	ImageResource world();
	ImageResource wrench_orange();
	ImageResource xls();
	ImageResource building();
	ImageResource home();
	ImageResource ok();
	ImageResource cancel();
	ImageResource search();
	ImageResource cd_eject();
	ImageResource cd_go();
	ImageResource comment_edit();
	ImageResource repeat();
	ImageResource run();

}
