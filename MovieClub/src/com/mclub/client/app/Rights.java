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
package com.mclub.client.app;

import com.gmvc.client.enums.RightGroup;
import com.gmvc.client.meta.Right;

/**
 * Kullanici denetimlerinde kullanilacak olan haklarin tanimlandigi siniftir.
 * 
 * Olusturulan her Right (hak) icin RightHelper sinifina bildirimde bulunulur,
 * bu sayede kullanici tanitim kismina otomatik olarak haklar eklenir
 * 
 * @see Right
 *
 * @author mdpinar
 * 
 */
public interface Rights {

	//Aksiyonlar
	public static final Right GivenMovie = new Right("Given Movies", RightGroup.Action);
	public static final Right IncomingMovie = new Right("Incoming Movies", RightGroup.Action);
	public static final Right ChangeStatus = new Right("Change Status", RightGroup.Action);
	
	//Tanitimlar
	public static final Right Customer = new Right("Customer", RightGroup.Form);
	public static final Right Movie = new Right("Movie", RightGroup.Form);
	public static final Right Job = new Right("Job", RightGroup.Form);
	public static final Right Location = new Right("Location", RightGroup.Form);
	public static final Right MovieGroup = new Right("MovieGroup", RightGroup.Form);

	//Raporlar
	public static final Right CustomerReport = new Right("Customer Report", RightGroup.Report);
	public static final Right CustomerTransReport = new Right("Customer Trans. Report", RightGroup.Report);
	public static final Right MovieReport = new Right("Movie Report", RightGroup.Report);
	public static final Right MovieStatusReport = new Right("Movie Status Report", RightGroup.Report);
	public static final Right MovieTransReport = new Right("Movie Trans. Report", RightGroup.Report);

	//Sistem
	public static final Right Users = new Right("Users", RightGroup.System);
	public static final Right Void = new Right("Void", RightGroup.System);
	
}
