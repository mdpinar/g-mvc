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
package com.mclub.client.service;

import com.gmvc.client.service.ICRUDService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mclub.client.model.ChangeStatusDTO;

/**
 * @see ChangeStatusServiceAsync
 * 
 * @author mdpinar
 */
@RemoteServiceRelativePath("changeStatusService")
public interface ChangeStatusService extends ICRUDService<ChangeStatusDTO> {
	
}
