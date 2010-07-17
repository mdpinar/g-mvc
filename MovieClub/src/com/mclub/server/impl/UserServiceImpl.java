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
package com.mclub.server.impl;

import com.gmvc.server.impl.CRUDImpl;
import com.mclub.client.model.UserDTO;
import com.mclub.client.service.UserService;
import com.mclub.server.model.User;

/**
 * Kullanici Tanitimi servisi
 * 
 * @see CRUDImpl
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings("serial")
public class UserServiceImpl 
	extends CRUDImpl<User, UserDTO> implements UserService {

	public UserServiceImpl() {
		super(User.class, UserDTO.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxResultSize() {
		return 0; //tum kullanicilar donulecek
	}

}
