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
import com.mclub.client.model.CustomerDTO;

/**
 * @see CustomerServiceAsync
 * 
 * @author mdpinar
 */
@RemoteServiceRelativePath("customerService")
public interface CustomerService extends ICRUDService<CustomerDTO> {

}
