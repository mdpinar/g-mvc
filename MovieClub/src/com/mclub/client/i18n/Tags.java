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
package com.mclub.client.i18n;

import com.google.gwt.i18n.client.ConstantsWithLookup;


/**
 * Tum mesajlari icerir
 * 
 * @author mdpinar
 * 
 */
public interface Tags extends ConstantsWithLookup, CustomerTags, 
		MovieTags, UserTags, AlertTags {

	String projectTitle();
	String error();
	String confirm();
	String info();
	String attention();
	String ok();
	String date();
	String firstDate();
	String lastDate();
	String cancel();
	String search();
	String apply();
	String loadPicture();
	String clearPicture();
	String addNew();
	String update();
	String refresh();
	String delete();
	String save();
	String saveAndClose();
	String saveAndContinue();
	String add();
	String remove();
	String select();
	String deselect();
	String pleaseWait();
	String deleteMessage();
	String successSaveMessage();
	String successRemoveMessage();
	String photo();
	String name();
	String discount();
	String group();
	String type();
	String male();
	String female();
	String child();
	String young();
	String mature();
	String old();
	String job();
	String jobTitle();
	String location();
	String locationTitle();
	String movieGroup();
	String movieGroupTitle();
	String movieInfoTitle();
	String user();
	String userTitle();
	String report();
	String customerReport();
	String customerTransReport();
	String movieReport();
	String movieStatusReport();
	String movieTransReport();
	String menuSystem();
	String publicInfo();
	String roles();
	String actions();
	String forms();
	String reports();
	String reportUnit();
	String description();
	String filter();
	String processOwner();
	String customersSelectableMovies();
	String addSelectedMoviesToGrid();
	String wellcome();
	
}
